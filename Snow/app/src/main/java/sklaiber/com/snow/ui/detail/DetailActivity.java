package sklaiber.com.snow.ui.detail;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import sklaiber.com.snow.R;
import sklaiber.com.snow.database.ResortProvider;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<Cursor> {

  private static final int URL_LOADER = 0;
  private LatLng latLng;

  @Bind(R.id.mapview) MapView mMapView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    ButterKnife.bind(this);

    String name = getIntent().getStringExtra("name");

    float lat = getIntent().getFloatExtra("lat", 0);
    float longt = getIntent().getFloatExtra("longt", 0);
    Timber.d("Lat " + String.valueOf(lat));
    latLng = new LatLng(lat, longt);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(name);
    setSupportActionBar(toolbar);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.container, DetailFragment.newInstance(name));
    ft.commit();

    mMapView.onCreate(savedInstanceState);
    mMapView.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(GoogleMap googleMap) {
          float zoomLevel = 13f;

          UiSettings settings = googleMap.getUiSettings();
          settings.setZoomControlsEnabled(true);
          settings.setAllGesturesEnabled(true);

          googleMap.moveCamera(
              CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
          googleMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
      }
    });

    getLoaderManager().initLoader(URL_LOADER, null, this);
  }

  @Override
  public void onResume() {
    super.onResume();
    if (mMapView != null) {
      mMapView.onResume();
    }
  }

  @Override
  public void onPause() {
    if (mMapView != null) {
      mMapView.onPause();
    }
    super.onPause();
  }

  @Override
  public void onDestroy() {
    if (mMapView != null) {
      try {
        mMapView.onDestroy();
      } catch (NullPointerException e) {
        Timber.e("%s Error while attempting MapView.onDestroy(), ignoring exception", e);
      }
    }
    super.onDestroy();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    if (mMapView != null) {
      mMapView.onLowMemory();
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (mMapView != null) {
      mMapView.onSaveInstanceState(outState);
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    String name = getIntent().getStringExtra("name");
    return new CursorLoader(getApplicationContext(), ResortProvider.Resorts.CONTENT_URI, null,
        "name=?", new String[] { name }, null);
  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    data.moveToFirst();
    Timber.d(String.valueOf(data.getCount()));
  }

  @Override public void onLoaderReset(Loader<Cursor> loader) {

  }
}

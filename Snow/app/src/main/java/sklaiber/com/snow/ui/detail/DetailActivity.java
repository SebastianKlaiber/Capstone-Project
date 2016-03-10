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
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import sklaiber.com.snow.R;
import sklaiber.com.snow.database.ResortColums;
import sklaiber.com.snow.database.ResortProvider;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity
    implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<Cursor> {

  private static final int URL_LOADER = 0;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    String name = getIntent().getStringExtra("name");

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(name);
    setSupportActionBar(toolbar);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.container, DetailFragment.newInstance(name));
    ft.commit();

    SupportMapFragment mapFragment =
        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

    getLoaderManager().initLoader(URL_LOADER, null, this);
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

  @Override public void onMapReady(GoogleMap map) {
    float zoomLevel = 12f;
    UiSettings settings = map.getUiSettings();
    settings.setZoomControlsEnabled(true);
    settings.setAllGesturesEnabled(true);
    map.moveCamera(
        CameraUpdateFactory.newLatLngZoom(new LatLng(47.2091904, 11.9637953), zoomLevel));
    map.addMarker(new MarkerOptions().position(new LatLng(47.2091904, 11.9637953)).title("Marker"));
  }

  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    String name = getIntent().getStringExtra("name");
    return new CursorLoader(getApplicationContext(), ResortProvider.Resorts.CONTENT_URI, null,
        "name=?", new String[] { name }, null);
  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    data.moveToFirst();
    Timber.d(String.valueOf(data.getCount()));
    Toast.makeText(DetailActivity.this,
        data.getString(data.getColumnIndex(ResortColums.NAME)) + data.getString(
            data.getColumnIndex(ResortColums.CONDITIONS)), Toast.LENGTH_SHORT).show();
  }

  @Override public void onLoaderReset(Loader<Cursor> loader) {

  }
}

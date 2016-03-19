package sklaiber.com.snow.ui.detail;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import sklaiber.com.snow.database.ResortColums;
import sklaiber.com.snow.database.ResortProvider;
import timber.log.Timber;

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

  @Bind(R.id.detail_snow_conditions_textview) TextView mConditonTV;
  @Bind(R.id.detail_new_snow_textview) TextView mNewSnowTV;
  @Bind(R.id.detail_snow_mountain_textview) TextView mSnowMountainTV;
  @Bind(R.id.detail_snow_valley_textview) TextView mSnowValleyTV;
  @Bind(R.id.mapview) MapView mMapView;

  private static final int URL_LOADER = 0;
  private LatLng latLng;
  private String mName;

  public DetailFragment() {
    // Required empty public constructor
  }

  public static DetailFragment newInstance(String name, float resortLat, float resortLong) {
    DetailFragment detailFragment = new DetailFragment();
    Bundle args = new Bundle();
    args.putString("name", name);
    args.putFloat("resortLat", resortLat);
    args.putFloat("resortLong", resortLong);
    detailFragment.setArguments(args);
    return detailFragment;
  }

  public static DetailFragment newInstance(String name, float resortLat, float resortLong, double personLat, double personLong) {
    DetailFragment detailFragment = new DetailFragment();
    Bundle args = new Bundle();
    args.putString("name", name);
    args.putFloat("resortLat", resortLat);
    args.putFloat("resortLong", resortLong);
    args.putDouble("personLat", personLat);
    args.putDouble("personLong", personLong);
    detailFragment.setArguments(args);
    return detailFragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mName = getArguments().getString(getString(R.string.key_intent_name));
    latLng = new LatLng(getArguments().getFloat("resortLat"),
        getArguments().getFloat("resortLong"));
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

    ButterKnife.bind(this, rootView);

    mMapView.onCreate(savedInstanceState);
    mMapView.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(GoogleMap googleMap) {
        float zoomLevel = 12f;

        UiSettings settings = googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setAllGesturesEnabled(true);
        settings.setMyLocationButtonEnabled(true);
        settings.setMapToolbarEnabled(true);

        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        googleMap.addMarker(new MarkerOptions().position(latLng).title(mName));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(getArguments().getDouble("personLat"), getArguments().getDouble("personLong"))));
      }
    });

    getLoaderManager().initLoader(URL_LOADER, null, this);

    return rootView;
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
        Timber.e(e, "Error while attempting MapView.onDestroy(), ignoring exception");
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

  @Override public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getContext(), ResortProvider.Resorts.CONTENT_URI, null, "name=?",
        new String[] { mName }, null);
  }

  @Override public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
    data.moveToFirst();
    mConditonTV.setText(data.getString(data.getColumnIndexOrThrow(ResortColums.CONDITIONS)));
    mNewSnowTV.setText(getString(R.string.snow, data.getString(data.getColumnIndexOrThrow(ResortColums.NEW_SNOW))));
    mSnowMountainTV.setText(getString(R.string.snow, data.getString(data.getColumnIndexOrThrow(ResortColums.SNOW_MOUNTAIN))));
    mSnowValleyTV.setText(getString(R.string.snow, data.getString(data.getColumnIndexOrThrow(ResortColums.SNOW_VALLEY))));
  }

  @Override public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
  }
}

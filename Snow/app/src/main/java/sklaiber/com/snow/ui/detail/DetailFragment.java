package sklaiber.com.snow.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

  public static DetailFragment newInstance(Context context, String name, float resortLat,
      float resortLong) {
    DetailFragment detailFragment = new DetailFragment();
    Bundle args = new Bundle();
    args.putString(context.getString(R.string.detail_fragment_name_key), name);
    args.putFloat(context.getString(R.string.detail_fragment_resort_lat_key), resortLat);
    args.putFloat(context.getString(R.string.detail_fragment_resort_longt_key), resortLong);
    detailFragment.setArguments(args);
    return detailFragment;
  }

  public static DetailFragment newInstance(Context context, String name, float resortLat,
      float resortLong, double personLat, double personLong) {
    DetailFragment detailFragment = new DetailFragment();
    Bundle args = new Bundle();
    args.putString(context.getString(R.string.detail_fragment_name_key), name);
    args.putFloat(context.getString(R.string.detail_fragment_resort_lat_key), resortLat);
    args.putFloat(context.getString(R.string.detail_fragment_resort_longt_key), resortLong);
    args.putDouble(context.getString(R.string.detail_fragment_person_lat_key), personLat);
    args.putDouble(context.getString(R.string.detail_fragment_person_longt_key), personLong);
    detailFragment.setArguments(args);
    return detailFragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mName = getArguments().getString(getString(R.string.key_intent_name));
    latLng = new LatLng(getArguments().getFloat(getString(R.string.detail_fragment_resort_lat_key)),
        getArguments().getFloat(getString(R.string.detail_fragment_resort_longt_key)));
    setHasOptionsMenu(true);
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

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        googleMap.addMarker(new MarkerOptions().position(latLng).title(mName));
        googleMap.addMarker(new MarkerOptions().position(
            new LatLng(getArguments().getDouble(getString(R.string.detail_fragment_person_lat_key)),
                getArguments().getDouble(getString(R.string.detail_fragment_person_longt_key)))));
      }
    });

    getLoaderManager().initLoader(URL_LOADER, null, this);

    return rootView;
  }

  private void finishCreatingMenu(Menu menu) {
    // Retrieve the share menu item
    MenuItem menuItem = menu.findItem(R.id.action_share);
    menuItem.setIntent(createShareForecastIntent());
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    if (getActivity() instanceof DetailActivity) {
      // Inflate the menu; this adds items to the action bar if it is present.
      inflater.inflate(R.menu.detailfragment, menu);
      finishCreatingMenu(menu);
    }
  }

  private Intent createShareForecastIntent() {
    StringBuilder sb = new StringBuilder();
    sb.append(mName);
    sb.append(mConditonTV.getText().toString());
    sb.append(mNewSnowTV.getText().toString());

    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    shareIntent.setType("text/plain");
    shareIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
    return shareIntent;
  }

  @Override public void onResume() {
    super.onResume();
    if (mMapView != null) {
      mMapView.onResume();
    }
  }

  @Override public void onPause() {
    if (mMapView != null) {
      mMapView.onPause();
    }
    super.onPause();
  }

  @Override public void onDestroy() {
    if (mMapView != null) {
      try {
        mMapView.onDestroy();
      } catch (NullPointerException e) {
        Timber.e(e, getString(R.string.mapview_destroy_error_message));
      }
    }
    super.onDestroy();
  }

  @Override public void onLowMemory() {
    super.onLowMemory();
    if (mMapView != null) {
      mMapView.onLowMemory();
    }
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (mMapView != null) {
      mMapView.onSaveInstanceState(outState);
    }
  }

  @Override public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getContext(), ResortProvider.Resorts.CONTENT_URI, null,
        getString(R.string.detail_fragment_where_clause), new String[] { mName }, null);
  }

  @Override
  public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
    data.moveToFirst();
    mConditonTV.setText(data.getString(data.getColumnIndexOrThrow(ResortColums.CONDITIONS)));
    mNewSnowTV.setText(getString(R.string.snow,
        data.getString(data.getColumnIndexOrThrow(ResortColums.NEW_SNOW))));
    mSnowMountainTV.setText(getString(R.string.snow,
        data.getString(data.getColumnIndexOrThrow(ResortColums.SNOW_MOUNTAIN))));
    mSnowValleyTV.setText(getString(R.string.snow,
        data.getString(data.getColumnIndexOrThrow(ResortColums.SNOW_VALLEY))));
  }

  @Override public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
  }
}

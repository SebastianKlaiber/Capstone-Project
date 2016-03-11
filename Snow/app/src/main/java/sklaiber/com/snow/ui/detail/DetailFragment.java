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
import sklaiber.com.snow.R;
import sklaiber.com.snow.database.ResortColums;
import sklaiber.com.snow.database.ResortProvider;

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

  @Bind(R.id.snow_conditions_tv) TextView mConditonTV;
  @Bind(R.id.new_snow_tv) TextView mNewSnowTV;
  @Bind(R.id.snow_mountain_tv) TextView mSnowMountainTV;
  @Bind(R.id.snow_valley) TextView mSnowValleyTV;

  private static final int URL_LOADER = 0;

  private String mName;

  public DetailFragment() {
    // Required empty public constructor
  }

  public static DetailFragment newInstance(String name) {
    DetailFragment detailFragment = new DetailFragment();
    Bundle args = new Bundle();
    args.putString("name", name);
    detailFragment.setArguments(args);
    return detailFragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mName = getArguments().getString("name");
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

    ButterKnife.bind(this, rootView);

    getLoaderManager().initLoader(URL_LOADER, null, this);

    return rootView;
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

package sklaiber.com.snow.ui.detail;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import sklaiber.com.snow.R;
import sklaiber.com.snow.database.ResortProvider;

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

  @Bind(R.id.conditions) TextView mConditions;

  private String mName;

  public DetailFragment() {
    // Required empty public constructor
  }

  public static DetailFragment newInstance(String name){
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

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView =  inflater.inflate(R.layout.fragment_detail, container, false);

    ButterKnife.bind(this, rootView);

    return rootView;
  }

  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getContext(), ResortProvider.Resorts.CONTENT_URI, null,
        "name=?", new String[] { mName }, null);
  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

  }

  @Override public void onLoaderReset(Loader<Cursor> loader) {

  }
}

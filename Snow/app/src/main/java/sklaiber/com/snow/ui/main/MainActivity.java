package sklaiber.com.snow.ui.main;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import javax.inject.Inject;
import sklaiber.com.snow.R;
import sklaiber.com.snow.database.ResortProvider;
import sklaiber.com.snow.sync.SyncAdapter;
import sklaiber.com.snow.ui.adapter.ResortAdapter;
import sklaiber.com.snow.ui.detail.DetailActivity;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
    SwipeRefreshLayout.OnRefreshListener{

  @Inject MainContract.UserActionListener mMainPresenter;

  @Bind(R.id.recyclerview_resort) RecyclerView mRecyclerView;
  @Bind(R.id.recyclerview_resort_empty) TextView mEmptyText;
  @Bind(R.id.swipe_to_refresh) SwipeRefreshLayout mSwipeRefresh;
  @Bind(R.id.toolbar) Toolbar toolbar;

  private static final int URL_LOADER = 0;
  private ResortAdapter mResortAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    setSupportActionBar(toolbar);

    SyncAdapter.initializeSyncAdapter(this);

    mSwipeRefresh.setOnRefreshListener(this);
    mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);

    getLoaderManager().initLoader(URL_LOADER, null, this);

    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.setHasFixedSize(true);

    mResortAdapter = new ResortAdapter(getApplicationContext(), mEmptyText, new ResortAdapter.OnClickHandler() {
      @Override public void onClick(String name, float latitude, float longitude, ResortAdapter.ViewHolder vh) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(getString(R.string.key_intent_name), name);
        intent.putExtra(getString(R.string.key_intent_latitude), latitude);
        intent.putExtra(getString(R.string.key_intent_longitude), longitude);
        startActivity(intent);
      }
    });

    mRecyclerView.setAdapter(mResortAdapter);
  }

  @Override protected void onResume() {
    super.onResume();
  }

  @Override public void onRefresh() {
    SyncAdapter.syncImmediately(this);
    mSwipeRefresh.setRefreshing(false);
  }

  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getApplicationContext(), ResortProvider.Resorts.CONTENT_URI, null, null, null, null);
  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    mResortAdapter.swapCursor(data);
    updateEmptyView();
  }

  @Override public void onLoaderReset(Loader<Cursor> loader) {
    mResortAdapter.swapCursor(null);
  }

  private void updateEmptyView() {
    if ( mResortAdapter.getItemCount() == 0 ) {
      if ( null != mEmptyText ) {
        mEmptyText.setText(R.string.empty_resort_list);
      }
    }
  }
}

package sklaiber.com.snow.ui.main;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

  @Inject MainContract.UserActionListener mMainPresenter;

  @Bind(R.id.recyclerview_resort) RecyclerView mRecyclerView;
  @Bind(R.id.recyclerview_resort_empty) TextView mEmptyText;

  private static final int URL_LOADER = 0;
  private ResortAdapter mResortAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    SyncAdapter.initializeSyncAdapter(this);

    getLoaderManager().initLoader(URL_LOADER, null, this);

    // Set the layout manager
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    // use this setting to improve performance if you know that changes
    // in content do not change the layout size of the RecyclerView
    mRecyclerView.setHasFixedSize(true);

    mResortAdapter = new ResortAdapter(getApplicationContext(), mEmptyText, new ResortAdapter.OnClickHandler() {
      @Override public void onClick(String name, ResortAdapter.ViewHolder vh) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
      }
    });
    mRecyclerView.setAdapter(mResortAdapter);
  }

  @Override protected void onResume() {
    super.onResume();
  }

  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getApplicationContext(), ResortProvider.Resorts.CONTENT_URI, null, null, null, null);
  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    mResortAdapter.swapCursor(data);
  }

  @Override public void onLoaderReset(Loader<Cursor> loader) {
    mResortAdapter.swapCursor(null);
  }
}

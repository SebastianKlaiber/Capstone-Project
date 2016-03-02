package sklaiber.com.snow.ui.main;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import javax.inject.Inject;
import sklaiber.com.snow.R;
import sklaiber.com.snow.database.ResortProvider;
import sklaiber.com.snow.di.components.AppComponent;
import sklaiber.com.snow.di.components.DaggerMainComponent;
import sklaiber.com.snow.di.modules.MainModule;
import sklaiber.com.snow.models.Resort;
import sklaiber.com.snow.sync.SyncAdapter;
import sklaiber.com.snow.ui.adapter.ResortAdapter;
import sklaiber.com.snow.ui.common.BaseActivity;

public class MainActivity extends BaseActivity implements MainContract.View, LoaderManager.LoaderCallbacks<Cursor>{

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

    mResortAdapter = new ResortAdapter(getApplicationContext(), mEmptyText);
    mRecyclerView.setAdapter(mResortAdapter);
  }

  @Override protected void onResume() {
    super.onResume();
  }

  @Override protected void setupComponent(AppComponent appComponent) {
    DaggerMainComponent.builder()
        .appComponent(appComponent)
        .mainModule(new MainModule(this))
        .build()
        .inject(this);
  }

  @Override public void showResorts(Resort resort) {

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

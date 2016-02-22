package sklaiber.com.snow.ui.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import javax.inject.Inject;
import sklaiber.com.snow.R;
import sklaiber.com.snow.di.components.AppComponent;
import sklaiber.com.snow.di.components.DaggerMainComponent;
import sklaiber.com.snow.di.modules.MainModule;
import sklaiber.com.snow.models.Items;
import sklaiber.com.snow.sync.SyncAdapter;
import sklaiber.com.snow.ui.common.BaseActivity;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainContract.View {

  @Inject MainContract.UserActionListener mMainPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    SyncAdapter.initializeSyncAdapter(this);
  }

  @Override protected void onResume() {
    super.onResume();
    SyncAdapter.syncImmediately(this);
  }

  @Override protected void setupComponent(AppComponent appComponent) {
    DaggerMainComponent.builder()
        .appComponent(appComponent)
        .mainModule(new MainModule(this))
        .build()
        .inject(this);
  }

  @Override public void showResorts(Items items) {
    Timber.d(items.getItems().get(0).getName());
  }
}

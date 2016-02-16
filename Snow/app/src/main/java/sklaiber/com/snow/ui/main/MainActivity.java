package sklaiber.com.snow.ui.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import javax.inject.Inject;

import sklaiber.com.snow.R;
import sklaiber.com.snow.di.components.AppComponent;
import sklaiber.com.snow.di.components.DaggerMainComponent;
import sklaiber.com.snow.di.modules.MainModule;
import sklaiber.com.snow.models.Items;
import sklaiber.com.snow.ui.common.BaseActivity;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainView {

    @Inject MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainPresenter.onResume();
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showProgress() {
        //Todo Show ProgressBar
    }

    @Override
    public void hideProgress() {
        //Todo Hide ProgressBar
    }

    @Override
    public void setItems(Items items) {
        Timber.d("MainActivity");
        TextView txt = (TextView) findViewById(R.id.txt);
        txt.setText(items.getItems().get(0).getName());
    }
}

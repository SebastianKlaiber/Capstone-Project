package sklaiber.com.snow.ui.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import sklaiber.com.snow.R;
import sklaiber.com.snow.di.components.AppComponent;
import sklaiber.com.snow.di.components.DaggerMainComponent;
import sklaiber.com.snow.di.modules.MainModule;
import sklaiber.com.snow.models.Repository;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
    public void setItems(ArrayList<Repository> items) {
        Timber.d(items.get(0).getName());
        TextView txt = (TextView) findViewById(R.id.txt);
        txt.setText(items.get(0).getName());
    }
}

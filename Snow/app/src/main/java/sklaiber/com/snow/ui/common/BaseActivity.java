package sklaiber.com.snow.ui.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import sklaiber.com.snow.SnowApplication;
import sklaiber.com.snow.di.components.AppComponent;

/**
 * Created by skipj on 12.01.2016.
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent((AppComponent) SnowApplication.get(this).component());
    }

    protected abstract void setupComponent(AppComponent appComponent);
}

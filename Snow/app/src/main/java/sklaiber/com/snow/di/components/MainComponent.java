package sklaiber.com.snow.di.components;

import dagger.Component;
import sklaiber.com.snow.di.modules.MainModule;
import sklaiber.com.snow.di.scopes.MainScope;
import sklaiber.com.snow.ui.main.MainActivity;

/**
 * Created by skipj on 11.01.2016.
 */
@MainScope
@Component(
    dependencies = AppComponent.class,
    modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity target);
}
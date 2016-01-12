package sklaiber.com.snow.di.modules;

import dagger.Module;
import dagger.Provides;
import sklaiber.com.snow.network.interactors.FindItemsInteractor;
import sklaiber.com.snow.ui.main.MainPresenter;
import sklaiber.com.snow.ui.main.MainPresenterImpl;
import sklaiber.com.snow.ui.main.MainView;

/**
 * Created by skipj on 12.01.2016.
 */
@Module
public class MainModule {

    private MainView mView;

    public MainModule(MainView view) {
        this.mView = view;
    }

    @Provides
    public MainView provideView() {
        return mView;
    }

    @Provides
    public MainPresenter providePresenter(MainView mainView, FindItemsInteractor findItemsInteractors) {
        return new MainPresenterImpl(mainView, findItemsInteractors);
    }
}

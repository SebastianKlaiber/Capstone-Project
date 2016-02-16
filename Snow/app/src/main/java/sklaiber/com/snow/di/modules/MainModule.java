package sklaiber.com.snow.di.modules;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;
import sklaiber.com.snow.network.ResortService;
import sklaiber.com.snow.network.ResortServiceImpl;
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
    public MainPresenter providePresenter(MainView mainView, ResortService resortService) {
        return new MainPresenterImpl(mainView, resortService);
    }

    @Provides
    public ResortService provideResortService(Retrofit retrofit) {
        return new ResortServiceImpl(retrofit);
    }
}

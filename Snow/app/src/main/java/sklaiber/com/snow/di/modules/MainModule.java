package sklaiber.com.snow.di.modules;

import dagger.Module;
import dagger.Provides;
import sklaiber.com.snow.network.ResortService;
import sklaiber.com.snow.ui.main.MainContract;
import sklaiber.com.snow.ui.main.MainPresenterImpl;

/**
 * Created by skipj on 12.01.2016.
 */
@Module
public class MainModule {

    private MainContract.View mView;

    public MainModule(MainContract.View view) {
        this.mView = view;
    }

    @Provides
    public MainContract.View provideView() {
        return mView;
    }

    @Provides
    public MainContract.UserActionListener providePresenter(MainContract.View mainView) {
        return new MainPresenterImpl(mainView);
    }

    //@Provides
    //public ResortService provideResortService(Retrofit retrofit) {
    //    return new ResortServiceImpl(retrofit);
    //}
}

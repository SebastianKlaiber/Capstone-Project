package sklaiber.com.snow;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.mockito.Mockito;
import sklaiber.com.snow.network.ResortService;
import sklaiber.com.snow.ui.main.MainPresenter;
import sklaiber.com.snow.ui.main.MainPresenterImpl;
import sklaiber.com.snow.ui.main.MainView;

@Module public class MockMainModule {

  @Provides
  public MainView provideView() {
    return Mockito.mock(MainView.class);
  }

  @Provides
  public MainPresenter providePresenter(MainView mainView, ResortService resortService) {
    return new MainPresenterImpl(mainView, resortService);
  }

  @Provides @Singleton public ResortService providesResortService() {
    return Mockito.mock(ResortService.class);
  }
}

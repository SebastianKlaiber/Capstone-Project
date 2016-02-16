package sklaiber.com.snow;

import dagger.Component;
import javax.inject.Singleton;
import sklaiber.com.snow.di.components.MainComponent;

@Singleton
@Component(modules = {MockMainModule.class})
public interface TestingComponent extends MainComponent{
  void inject(MainActivityTest target);
}

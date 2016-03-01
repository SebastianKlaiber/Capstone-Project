package sklaiber.com.snow.ui.main;

import sklaiber.com.snow.models.Resort;

/**
 * Created by sklaiber on 16.02.16.
 */
public interface MainContract {
  interface View {
    void showResorts(Resort resort);
  }

  interface UserActionListener {
    void loadResorts();
  }
}

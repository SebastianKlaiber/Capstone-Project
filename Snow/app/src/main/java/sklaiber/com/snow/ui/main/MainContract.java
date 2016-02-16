package sklaiber.com.snow.ui.main;

import sklaiber.com.snow.models.Items;

/**
 * Created by sklaiber on 16.02.16.
 */
public interface MainContract {
  interface View {
    void showResorts(Items items);
  }

  interface UserActionListener {
    void loadResorts();
  }

}

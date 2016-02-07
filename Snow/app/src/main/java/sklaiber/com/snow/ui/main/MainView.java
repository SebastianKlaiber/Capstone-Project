package sklaiber.com.snow.ui.main;

import sklaiber.com.snow.models.Items;

/**
 * Created by skipj on 12.01.2016.
 */
public interface MainView {

    public void showProgress();
    public void hideProgress();
    public  void setItems(Items items);
}

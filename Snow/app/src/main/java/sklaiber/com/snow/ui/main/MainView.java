package sklaiber.com.snow.ui.main;

import sklaiber.com.snow.models.Items;

public interface MainView {

    public void showProgress();
    public void hideProgress();
    public  void setItems(Items items);
}

package sklaiber.com.snow.ui.main;

import java.util.ArrayList;

import sklaiber.com.snow.models.Repository;

/**
 * Created by skipj on 12.01.2016.
 */
public interface MainView {

    public void showProgress();
    public void hideProgress();
    public  void setItems(ArrayList<Repository> items);
}

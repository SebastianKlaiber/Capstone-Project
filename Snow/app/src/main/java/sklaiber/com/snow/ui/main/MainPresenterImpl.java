package sklaiber.com.snow.ui.main;

import sklaiber.com.snow.models.Items;
import sklaiber.com.snow.network.ResortService;

public class MainPresenterImpl implements MainContract.UserActionListener, OnFinishedListener {

  private MainContract.View mainView;
  private ResortService resortService;

  public MainPresenterImpl(MainContract.View mainView, ResortService resortService) {
    this.resortService = resortService;
    this.mainView = mainView;
  }

  @Override public void loadResorts() {
    resortService.getResort(this);
  }

  @Override public void onFinished(Items items) {
    mainView.showResorts(items);
  }
}

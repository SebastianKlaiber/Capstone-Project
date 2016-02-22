package sklaiber.com.snow.ui.main;

import sklaiber.com.snow.models.Items;

public class MainPresenterImpl implements MainContract.UserActionListener, OnFinishedListener {

  private MainContract.View mainView;
  //private ResortService resortService;

  public MainPresenterImpl(MainContract.View mainView) {
    this.mainView = mainView;
  }

  @Override public void loadResorts() {
    //resortService.getResort(this);
  }

  @Override public void onFinished(Items items) {
    mainView.showResorts(items);
  }
}

package sklaiber.com.snow.ui.main;

import sklaiber.com.snow.models.Resort;

public class MainPresenterImpl implements MainContract.UserActionListener, OnFinishedListener {

  private MainContract.View mainView;

  public MainPresenterImpl(MainContract.View mainView) {
    this.mainView = mainView;
  }

  @Override public void loadResorts() {

  }

  @Override public void onFinished(Resort resort) {

  }
}

package sklaiber.com.snow.ui.main;

import sklaiber.com.snow.models.Items;
import sklaiber.com.snow.network.interactors.FindItemsInteractor;

/**
 * Created by skipj on 12.01.2016.
 */
public class MainPresenterImpl implements MainPresenter, OnFinishedListener {

    private MainView mainView;
    private FindItemsInteractor findItemsInteractor;

    public MainPresenterImpl(MainView mainView, FindItemsInteractor findItemsInteractor) {
        this.mainView = mainView;
        this.findItemsInteractor = findItemsInteractor;
    }

    @Override public void onResume() {
        mainView.showProgress();
        findItemsInteractor.findItems(this);
    }

    @Override public void onFinished(Items items) {
        mainView.hideProgress();
        mainView.setItems(items);
    }
}

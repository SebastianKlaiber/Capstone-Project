package sklaiber.com.snow.ui.main;

import sklaiber.com.snow.models.Items;
import sklaiber.com.snow.network.ResortService;
import timber.log.Timber;

public class MainPresenterImpl implements MainPresenter, OnFinishedListener {

    private MainView mainView;
    private ResortService resortService;

    public MainPresenterImpl(MainView mainView, ResortService resortService) {
        Timber.d("MainPresenter created");
        this.mainView = mainView;
        this.resortService = resortService;
    }

    @Override
    public void onResume() {
        mainView.showProgress();
        resortService.getResort(this);
    }

    @Override
    public void onFinished(Items items) {
        mainView.hideProgress();
        mainView.setItems(items);
        Timber.d(items.getItems().get(0).getName());
    }
}

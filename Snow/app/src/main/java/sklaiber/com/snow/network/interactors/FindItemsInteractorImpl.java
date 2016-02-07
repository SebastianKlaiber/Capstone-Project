package sklaiber.com.snow.network.interactors;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import sklaiber.com.snow.models.Items;
import sklaiber.com.snow.models.Resort;
import sklaiber.com.snow.network.MyApi;
import sklaiber.com.snow.ui.main.OnFinishedListener;
import timber.log.Timber;

/**
 * Created by skipj on 12.01.2016.
 */
public class FindItemsInteractorImpl implements FindItemsInteractor {

    MyApi mApi;

    @Inject
    public FindItemsInteractorImpl(MyApi api) {
        this.mApi = api;
    }

    @Override
    public void findItems(final OnFinishedListener listener) {

        Call<Items> call = mApi.getResort();

        call.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Response<Items> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    listener.onFinished(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}

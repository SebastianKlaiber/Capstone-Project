package sklaiber.com.snow.network.interactors;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import sklaiber.com.snow.models.Repository;
import sklaiber.com.snow.network.MyApi;
import sklaiber.com.snow.ui.main.OnFinishedListener;

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

        Call<ArrayList<Repository>> call = mApi.getRepository();

        call.enqueue(new Callback<ArrayList<Repository>>() {
            @Override
            public void onResponse(Response<ArrayList<Repository>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    listener.onFinished(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}

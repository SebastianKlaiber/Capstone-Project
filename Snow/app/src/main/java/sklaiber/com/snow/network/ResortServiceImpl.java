package sklaiber.com.snow.network;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import sklaiber.com.snow.models.Resort;
import sklaiber.com.snow.ui.main.OnFinishedListener;
import timber.log.Timber;

/**
 * Created by sklaiber on 07.02.16.
 */
public class ResortServiceImpl implements ResortService {

  private Retrofit retrofit;

  public ResortServiceImpl(Retrofit retrofit) {
    this.retrofit = retrofit;
  }

  @Override public void getResort(final OnFinishedListener listener) {
    MyApi myApi = retrofit.create(MyApi.class);

    Call<Resort> call = myApi.getResort();
    call.enqueue(new Callback<Resort>() {
      @Override public void onResponse(Response<Resort> response, Retrofit retrofit) {
        if (response.isSuccess()) {
          listener.onFinished(response.body());
        }
      }

      @Override public void onFailure(Throwable t) {
        Timber.d(t.getMessage());
      }
    });
  }
}

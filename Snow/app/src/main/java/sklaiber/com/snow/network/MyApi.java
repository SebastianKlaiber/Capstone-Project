package sklaiber.com.snow.network;

import retrofit.Call;
import retrofit.http.GET;
import sklaiber.com.snow.models.Items;
import sklaiber.com.snow.models.Resort;

/**
 * Created by skipj on 11.01.2016.
 */
public interface MyApi {
//    @GET("/_ah/api/myApi/v1/resortbean")
//    Call<ArrayList<Resort>> getRepository();

    @GET("/_ah/api/myApi/v1/resortbean")
    Call<Items> getResort();
}

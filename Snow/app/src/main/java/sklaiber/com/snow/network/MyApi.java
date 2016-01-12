package sklaiber.com.snow.network;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import sklaiber.com.snow.models.Repository;

/**
 * Created by skipj on 11.01.2016.
 */
public interface MyApi {
    @GET("/users/SebastianKlaiber/repos")
    Call<ArrayList<Repository>> getRepository();
}

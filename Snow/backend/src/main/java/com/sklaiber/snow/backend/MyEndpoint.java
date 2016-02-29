/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.sklaiber.snow.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import java.util.ArrayList;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.snow.sklaiber.com",
    ownerName = "backend.snow.sklaiber.com",
    packagePath=""
  )
)
public class MyEndpoint {

    public static ArrayList<ResortBean> resort = new ArrayList<>();

    static {
        resort.add(new ResortBean("SkiResort1", "good"));
        resort.add(new ResortBean("SkiResort2", "good"));
    }

    public ArrayList<ResortBean> listResort() {
        return resort;
    }
}

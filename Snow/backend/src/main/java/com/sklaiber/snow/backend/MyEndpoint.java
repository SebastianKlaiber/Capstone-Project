/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.sklaiber.snow.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import java.util.ArrayList;

@Api(
    name = "myApi",
    version = "v1",
    namespace = @ApiNamespace(
        ownerDomain = "backend.snow.sklaiber.com",
        ownerName = "backend.snow.sklaiber.com",
        packagePath = "")) public class MyEndpoint {

  public static ArrayList<ResortBean> resort = new ArrayList<>();

  static {
    ResortBean gerlos = new ResortBean("Gerlos", "good");
    gerlos.setLatitude(47.2268f);
    gerlos.setLongitude(12.0432f);
    gerlos.setSnowDepthMountain(100);
    gerlos.setGetSnowDepthValley(10);
    gerlos.setNewSnow(10);
    gerlos.setPhoneNumber("+4352845376");
    gerlos.setHomepage("www.zillertalarena.com");

    ResortBean alpbach = new ResortBean("Alpbachtal", "old snow");
    alpbach.setLatitude(47.4167f);
    alpbach.setLongitude(11.8833f);
    alpbach.setSnowDepthMountain(120);
    alpbach.setGetSnowDepthValley(25);
    alpbach.setNewSnow(15);
    alpbach.setPhoneNumber("+43533721200");
    alpbach.setHomepage("www.alpbachtal.at");

    resort.add(gerlos);
    resort.add(alpbach);
  }

  public ArrayList<ResortBean> listResort() {
    return resort;
  }
}

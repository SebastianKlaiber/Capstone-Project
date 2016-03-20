package sklaiber.com.snow.utils;

import com.google.android.gms.maps.model.LatLng;
import java.util.HashMap;

public class Constants {
  public static String BASE_URL = "https://ambient-tuner-123915.appspot.com";

  public static final String PACKAGE_NAME = "sklaiber.com.snow";

  public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

  public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;

  public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
      GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
  public static final float GEOFENCE_RADIUS_IN_METERS = 1;

  public static final HashMap<String, LatLng> RESORT_LANDMARKS = new HashMap<String, LatLng>();
  static {
    RESORT_LANDMARKS.put("GERLOS", new LatLng(47.228195, 12.049626));
    RESORT_LANDMARKS.put("ALPBACH", new LatLng(47.373723, 11.959354));
  }
}

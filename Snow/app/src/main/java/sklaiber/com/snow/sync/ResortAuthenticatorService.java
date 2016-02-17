package sklaiber.com.snow.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by sklaiber on 16.02.16.
 */
public class ResortAuthenticatorService extends Service{
  // Instance field that stores the authenticator object
  private ResortAuthenticator mAuthenticator;

  @Override
  public void onCreate() {
    // Create a new authenticator object
    mAuthenticator = new ResortAuthenticator(this);
  }

  /*
   * When the system binds to this Service to make the RPC call
   * return the authenticator's IBinder.
   */
  @Override
  public IBinder onBind(Intent intent) {
    return mAuthenticator.getIBinder();
  }
}

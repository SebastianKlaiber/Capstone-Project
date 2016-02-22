package sklaiber.com.snow;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.test.AndroidTestCase;
import sklaiber.com.snow.sync.StubProvider;

/**
 * Created by sklaiber on 21.02.16.
 */
public class TestProvider extends AndroidTestCase {

  @Override protected void setUp() throws Exception {
    super.setUp();
  }

  public void testProviderRegistry() {
    PackageManager pm = mContext.getPackageManager();

    ComponentName componentName = new ComponentName(mContext.getPackageName(), StubProvider.class.getName());

    try {
      ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
      // Make sure that the registered authority matches the authority from the Contract.
      //assertEquals("Error: WeatherProvider registered with authority: " + providerInfo.authority +
      //        " instead of authority: " + WeatherContract.CONTENT_AUTHORITY,
      //    providerInfo.authority, WeatherContract.CONTENT_AUTHORITY);
    } catch (PackageManager.NameNotFoundException e) {
      // I guess the provider isn't registered correctly.
      assertTrue("Error: WeatherProvider not registered at " + mContext.getPackageName(),
          false);
    }
  }
}

package sklaiber.com.snow;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.test.AndroidTestCase;
import sklaiber.com.snow.database.ResortProvider;

/**
 * Created by sklaiber on 21.02.16.
 */
public class TestProvider extends AndroidTestCase {

  public void deleteAllRecordsFromProvider() {
    mContext.getContentResolver().delete(
        ResortProvider.Resorts.CONTENT_URI,
        null,
        null
    );

    Cursor cursor = mContext.getContentResolver().query(
        ResortProvider.Resorts.CONTENT_URI,
        null,
        null,
        null,
        null
    );

    assertEquals("Error: Records not deleted from Resort table during delete", 0, cursor.getCount());
    cursor.close();
  }

  public void deleteAllRecords() {
    deleteAllRecordsFromProvider();
  }

  // Since we want each test to start with a clean slate, run deleteAllRecords
  // in setUp (called by the test runner before each test).
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    deleteAllRecords();
  }

  public void testProviderRegistry() {
    PackageManager pm = mContext.getPackageManager();

    ComponentName componentName = new ComponentName(mContext.getPackageName(), ResortProvider.class.getName());

    try {
      ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
      // Make sure that the registered authority matches the authority from the Contract.
      assertEquals("Error: ResortProvider registered with authority: " + providerInfo.authority +
              " instead of authority: " + ResortProvider.AUTHORITY,
          providerInfo.authority, ResortProvider.AUTHORITY);

    } catch (PackageManager.NameNotFoundException e) {
      // I guess the provider isn't registered correctly.
      assertTrue("Error: ResortProvider not registered at " + mContext.getPackageName(),
          false);
    }
  }
}

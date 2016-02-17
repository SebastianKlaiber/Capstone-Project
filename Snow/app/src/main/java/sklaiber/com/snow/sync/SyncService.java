package sklaiber.com.snow.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import timber.log.Timber;

/**
 * Created by sklaiber on 16.02.16.
 */
public class SyncService extends Service {

  private static SyncAdapter sSyncAdapter = null;
  private static final Object sSyncAdapterLock = new Object();

  @Override public void onCreate() {
    Timber.d("onCreate - SyncService");
    synchronized (sSyncAdapterLock) {
      if (sSyncAdapter == null) {
        sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
      }
    }
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return sSyncAdapter.getSyncAdapterBinder();
  }
}

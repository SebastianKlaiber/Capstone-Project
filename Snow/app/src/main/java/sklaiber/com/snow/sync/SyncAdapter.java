package sklaiber.com.snow.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import java.util.List;
import javax.inject.Inject;
import sklaiber.com.snow.R;
import sklaiber.com.snow.SnowApplication;
import sklaiber.com.snow.database.ResortColums;
import sklaiber.com.snow.database.ResortProvider;
import sklaiber.com.snow.models.Item;
import sklaiber.com.snow.models.Resort;
import sklaiber.com.snow.network.ResortService;
import sklaiber.com.snow.ui.main.OnFinishedListener;
import timber.log.Timber;

/**
 * Created by sklaiber on 16.02.16.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

  @Inject ResortService resortService;

  public static final String ACTION_DATA_UPDATED =
      "com.example.android.sunshine.app.ACTION_DATA_UPDATED";

  // Interval at which to sync with the weather, in seconds.
  // 60 seconds (1 minute) * 180 = 3 hours
  public static final int SYNC_INTERVAL = 60 * 180;
  public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
  private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

  public SyncAdapter(Context context, boolean autoInitialize) {
    super(context, autoInitialize);
    ((SnowApplication) getContext()).createAppComponent().inject(this);
  }

  @Override public void onPerformSync(Account account, Bundle extras, String authority,
      ContentProviderClient provider, SyncResult syncResult) {
    Timber.d("Starting Sync");

    resortService.getResort(new OnFinishedListener() {
      @Override public void onFinished(Resort resort) {
        List<Item> resortArray = resort.getItems();

        for (int i = 0; i < resortArray.size(); i++) {
          ContentValues values = new ContentValues();
          values.put(ResortColums.NAME, resortArray.get(i).getName());
          values.put(ResortColums.CONDITIONS, resortArray.get(i).getConditions());
          values.put(ResortColums.LATITUDE, resortArray.get(i).getLatitude());
          values.put(ResortColums.LONGITUDE, resortArray.get(i).getLongitude());
          values.put(ResortColums.SNOW_MOUNTAIN, resortArray.get(i).getSnowDepthMountain());
          values.put(ResortColums.SNOW_VALLEY, resortArray.get(i).getGetSnowDepthValley());
          values.put(ResortColums.NEW_SNOW, resortArray.get(i).getNewSnow());
          values.put(ResortColums.HOMEPAGE, resortArray.get(i).getHomepage());
          values.put(ResortColums.PHONE_NUMBER, resortArray.get(i).getPhoneNumber());

          int rows = getContext().getContentResolver()
              .update(ResortProvider.Resorts.CONTENT_URI, values, ResortColums.NAME + "=?",
                  new String[] { resortArray.get(i).getName() });

          if (rows == 0) {
            getContext().getContentResolver().insert(ResortProvider.Resorts.CONTENT_URI, values);
            Timber.d("Add %s to Database.", resortArray.get(i).getName());
          }
        }
      }
    });
    Timber.d("Sync finished.");
  }

  /**
   * Helper method to schedule the sync adapter periodic execution
   */
  public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
    Account account = getSyncAccount(context);
    String authority = context.getString(R.string.content_authority);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      // we can enable inexact timers in our periodic sync
      SyncRequest request = new SyncRequest.Builder().
          syncPeriodic(syncInterval, flexTime).
          setSyncAdapter(account, authority).
          setExtras(new Bundle()).build();
      ContentResolver.requestSync(request);
    } else {
      ContentResolver.addPeriodicSync(account, authority, new Bundle(), syncInterval);
    }
  }

  /**
   * Helper method to have the sync adapter sync immediately
   *
   * @param context The context used to access the account service
   */
  public static void syncImmediately(Context context) {
    Bundle bundle = new Bundle();
    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
    ContentResolver.requestSync(getSyncAccount(context),
        context.getString(R.string.content_authority), bundle);
  }

  /**
   * Helper method to get the fake account to be used with SyncAdapter, or make a new one
   * if the fake account doesn't exist yet.  If we make a new account, we call the
   * onAccountCreated method so we can initialize things.
   *
   * @param context The context used to access the account service
   * @return a fake account.
   */
  public static Account getSyncAccount(Context context) {
    // Get an instance of the Android account manager
    AccountManager accountManager =
        (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

    // Create the account type and default account
    Account newAccount = new Account(context.getString(R.string.app_name),
        context.getString(R.string.sync_account_type));

    // If the password doesn't exist, the account doesn't exist
    if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
      if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
        return null;
      }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

      onAccountCreated(newAccount, context);
    }
    return newAccount;
  }

  private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
    SyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
    ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority),
        true);

        /*
         * Finally, let's do a sync to get things started
         */
    syncImmediately(context);
  }

  public static void initializeSyncAdapter(Context context) {
    getSyncAccount(context);
  }
}

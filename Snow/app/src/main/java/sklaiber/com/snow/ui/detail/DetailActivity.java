package sklaiber.com.snow.ui.detail;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import sklaiber.com.snow.R;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

  GoogleApiClient mGoogleApiClient;
  Location mLastLocation;
  String name;
  float lat;
  float longt;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    ButterKnife.bind(this);

    name = getIntent().getStringExtra(getString(R.string.key_intent_name));

    lat = getIntent().getFloatExtra(getString(R.string.key_intent_latitude), 0);
    longt = getIntent().getFloatExtra(getString(R.string.key_intent_longitude), 0);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(name);
    setSupportActionBar(toolbar);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    if (mGoogleApiClient == null) {
      mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this)
          .addApi(LocationServices.API)
          .build();
    }
  }

  @Override protected void onStart() {
    mGoogleApiClient.connect();
    super.onStart();
  }

  @Override protected void onStop() {
    mGoogleApiClient.disconnect();
    super.onStop();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.meun_detail, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      case R.id.action_info:
        FragmentManager fm = getSupportFragmentManager();
        InfoFragment infoFragment = InfoFragment.newInstance(getApplicationContext(), name);
        infoFragment.show(fm, "fragment_info");
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onConnected(@Nullable Bundle bundle) {
    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    if (mLastLocation != null) {
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.detail_fragment_container,
          DetailFragment.newInstance(getApplicationContext(), name, lat, longt,
              mLastLocation.getLatitude(), mLastLocation.getLongitude()));
      ft.commit();
    } else {
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.detail_fragment_container,
          DetailFragment.newInstance(getApplicationContext(), name, lat, longt));
      ft.commit();
    }
  }

  @Override public void onConnectionSuspended(int i) {
    Timber.d("Suspended");
  }

  @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Timber.d(connectionResult.getErrorMessage());
  }
}

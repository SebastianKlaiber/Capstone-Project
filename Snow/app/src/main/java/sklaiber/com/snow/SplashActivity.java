package sklaiber.com.snow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import sklaiber.com.snow.ui.main.MainActivity;

/**
 * Created by sklaiber on 17.03.16.
 */
public class SplashActivity extends AppCompatActivity{
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }
}
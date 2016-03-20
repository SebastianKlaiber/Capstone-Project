package sklaiber.com.snow.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import sklaiber.com.snow.ui.main.MainActivity;


public class SplashActivity extends AppCompatActivity{
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
    finish();
  }
}

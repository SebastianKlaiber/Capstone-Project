package sklaiber.com.snow;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import sklaiber.com.snow.ui.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

  @Rule public final ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class);

  @Test
  public void shouldClickOnRecylcerView() {
    onView(withText("SkiResort2")).check(matches(isDisplayed()));

    onView(withId(R.id.recyclerview_resort)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    onView(withText("old snow")).check(matches(isDisplayed()));
  }
}

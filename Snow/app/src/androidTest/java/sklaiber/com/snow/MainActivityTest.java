package sklaiber.com.snow;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import sklaiber.com.snow.ui.main.MainActivity;
import sklaiber.com.snow.utils.Constants;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by skipj on 30.01.2016.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends InstrumentationTestCase {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class, true, false);

    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        Constants.BASE_URL = server.url("/").toString();
    }

    @Test
    public void testQuoteIsShown() throws Exception {
        String fileName = "api_200_ok_response.json";
        server.enqueue(new MockResponse()
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withText("This is a Test")).check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

}

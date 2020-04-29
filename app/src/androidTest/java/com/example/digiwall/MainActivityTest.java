package com.example.digiwall;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> mlogin=new ActivityTestRule<LoginActivity>(LoginActivity.class);
    private LoginActivity mActivity = null;
    Instrumentation.ActivityMonitor MainActivitymonitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor SigupActivitymonitor = getInstrumentation().addMonitor(SignupActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor ResetActivitymonitor = getInstrumentation().addMonitor(ResetActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mlogin.getActivity();
    }
    @Test
    public void FragmentTestingIncomePage() {
        onView(withId(R.id.email_login)).perform(typeText("saisankeerthreddy909@gmail.com"));
        onView(withId(R.id.password_login)).perform(typeText("saivaishu10"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText(R.string.login_successfull)).inRoot(new ToastMatcher()).check(matches(withText("Login Successfull!")));
        Activity MainActivity = getInstrumentation().waitForMonitorWithTimeout(MainActivitymonitor,5000);
        assertNotNull(MainActivity);
        onView(withId(R.id.income)).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Log Out")).perform(click());
        onView(withText(R.string.logoutsuccessful)).inRoot(new ToastMatcher()).check(matches(withText("Logged Out Succesfully!")));
    }
    @Test
    public void FragmentTestingExpensePage() {
        onView(withId(R.id.email_login)).perform(typeText("saisankeerthreddy909@gmail.com"));
        onView(withId(R.id.password_login)).perform(typeText("saivaishu10"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText(R.string.login_successfull)).inRoot(new ToastMatcher()).check(matches(withText("Login Successfull!")));
        Activity MainActivity = getInstrumentation().waitForMonitorWithTimeout(MainActivitymonitor,10000);
        assertNotNull(MainActivity);
        onView(withId(R.id.expense)).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Log Out")).perform(click());
        onView(withText(R.string.logoutsuccessful)).inRoot(new ToastMatcher()).check(matches(withText("Logged Out Succesfully!")));
    }
    @Test
    public void FragmentTestingProfilePage() {
        onView(withId(R.id.email_login)).perform(typeText("saisankeerthreddy909@gmail.com"));
        onView(withId(R.id.password_login)).perform(typeText("saivaishu10"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText(R.string.login_successfull)).inRoot(new ToastMatcher()).check(matches(withText("Login Successfull!")));
        Activity MainActivity = getInstrumentation().waitForMonitorWithTimeout(MainActivitymonitor,10000);
        assertNotNull(MainActivity);
        onView(withId(R.id.profile)).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Log Out")).perform(click());
        onView(withText(R.string.logoutsuccessful)).inRoot(new ToastMatcher()).check(matches(withText("Logged Out Succesfully!")));
    }

    @After
    public void tearDown() throws Exception {
        mActivity =null;
    }

}

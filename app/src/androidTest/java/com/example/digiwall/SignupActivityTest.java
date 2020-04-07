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

public class SignupActivityTest {
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
    public void GoToLoginButtonCheck() {
        onView(withId(R.id.btn_noaccount)).perform(click());
        Activity SigupActivity = getInstrumentation().waitForMonitorWithTimeout(SigupActivitymonitor,5000);
        assertNotNull(SigupActivity);
        View view = mActivity.findViewById(R.id.sign_in_button);
        assertNotNull(view);
        onView(withId(R.id.sign_in_button)).perform(click());
    }
    @Test
    public void SuccessfulRegistrationTest() {
        onView(withId(R.id.btn_noaccount)).perform(click());
        onView(withId(R.id.email_reg)).perform(typeText("saisankeerth1180@gmail.com"));
        onView(withId(R.id.password_reg)).perform(typeText("saivaishu10"));
        onView(withId(R.id.Cpassword_reg)).perform(typeText("saivaishu10"));
        closeSoftKeyboard();
        onView(withId(R.id.TC_Signup)).perform(click());
        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withText(R.string.email_verification)).inRoot(new ToastMatcher()).check(matches(withText("Registration Successful! Check your Email for Verification link!")));
    }
    @Test
    public void FailedRegistrationTest() {
        onView(withId(R.id.btn_noaccount)).perform(click());
        onView(withId(R.id.email_reg)).perform(typeText("saisankeerth1480@gmail.com"));
        onView(withId(R.id.password_reg)).perform(typeText("saiva"));
        onView(withId(R.id.Cpassword_reg)).perform(typeText("saiva"));
        closeSoftKeyboard();
        onView(withId(R.id.TC_Signup)).perform(click());
        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withText(R.string.failed_email_verification)).inRoot(new ToastMatcher()).check(matches(withText("Password too short, enter minimum 6 characters")));
        onView(withId(R.id.sign_in_button)).perform(click());
    }
    @After
    public void tearDown() throws Exception {
        mActivity =null;
    }

}

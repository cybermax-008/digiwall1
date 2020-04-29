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
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class ResetActivityTest
{
    @Rule
    public ActivityTestRule<LoginActivity> mlogin=new ActivityTestRule<LoginActivity>(LoginActivity.class);
    private LoginActivity mActivity = null;
    public ActivityTestRule<ResetActivity> mReset=new ActivityTestRule<ResetActivity>(ResetActivity.class);
    private ResetActivity mActivity1 = null;
    Instrumentation.ActivityMonitor ResetActivitymonitor = getInstrumentation().addMonitor(ResetActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mlogin.getActivity();
        mActivity1=mReset.getActivity();
    }

    @Test
    public void ResetButtonCheck()
    {
        onView(withId(R.id.email_login)).perform(typeText("saisankeerthreddy909@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.forgot_password)).perform(click());
        Activity ResetActivity = getInstrumentation().waitForMonitorWithTimeout(ResetActivitymonitor,5000);
        assertNotNull(ResetActivity);
    }
    @Test
    public void ResetActivityChecker() {
        onView(withId(R.id.email_login)).perform(typeText("saisankeerthreddy909@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.forgot_password)).perform(click());
        Activity ResetActivity = getInstrumentation().waitForMonitorWithTimeout(ResetActivitymonitor,5000);
        assertNotNull(ResetActivity);
        onView(withId(R.id.resetEmail)).perform(typeText("saisankeerthreddy909@gmail.com"));
        onView(withId(R.id.btn_reset)).perform(click());
        ResetActivity.finish();
    }
    @Test
    public void ResetActivityToastChecker() {
        onView(withId(R.id.email_login)).perform(typeText("saisankeerthreddy909@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.forgot_password)).perform(click());
        Activity ResetActivity = getInstrumentation().waitForMonitorWithTimeout(ResetActivitymonitor,5000);
        assertNotNull(ResetActivity);
        onView(withId(R.id.resetEmail)).perform(typeText("saisankeerthreddy909@gmail.com"));
        onView(withId(R.id.btn_reset)).perform(click());
        onView(withText(R.string.resetlink)).inRoot(new ToastMatcher()).check(matches(withText("Password sent to your Email!")));
        ResetActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
            mActivity =null;
            mActivity1=null;
        }

}

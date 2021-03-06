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

public class LoginActivityTest {
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
    public void LoginButtonCheck() {
        View view = mActivity.findViewById(R.id.btn_login);
        assertNotNull(view);
    }
    @Test
    public void SignupButtonCheck() {
        View view = mActivity.findViewById(R.id.btn_noaccount);
        assertNotNull(view);
    }
    @Test
    public void ForgotPasswordButtonCheck() {
        View view = mActivity.findViewById(R.id.forgot_password);
        assertNotNull(view);
    }
    @Test
    public void SuccessfulLoginTest() {
        onView(withId(R.id.email_login)).perform(typeText("saisankeerthreddy909@gmail.com"));
        onView(withId(R.id.password_login)).perform(typeText("saivaishu10"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText(R.string.login_successfull)).inRoot(new ToastMatcher()).check(matches(withText("Login Successfull!")));
        Activity MainActivity = getInstrumentation().waitForMonitorWithTimeout(MainActivitymonitor,5000);
        assertNotNull(MainActivity);
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Log Out")).perform(click());
        onView(withText(R.string.logoutsuccessful)).inRoot(new ToastMatcher()).check(matches(withText("Logged Out Succesfully!")));
    }
    @Test
    public void RegistrationPageTest() {
        onView(withId(R.id.btn_noaccount)).perform(click());
        Activity SigupActivity = getInstrumentation().waitForMonitorWithTimeout(SigupActivitymonitor,5000);
        assertNotNull(SigupActivity);
        SigupActivity.finish();
    }
    @Test
    public void ResetActivityTest() {
        onView(withId(R.id.forgot_password)).perform(click());
        Activity ResetActivity = getInstrumentation().waitForMonitorWithTimeout(ResetActivitymonitor,5000);
        assertNotNull(ResetActivity);
        ResetActivity.finish();
    }
    @Test
    public void ToastCheckIncorrectPasswordLenGretSix()
    {
        onView(withId(R.id.email_login)).perform(typeText("saisankeerthreddy909@gmail.com"));
        onView(withId(R.id.password_login)).perform(typeText("saivaishu1"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText(R.string.auth_failed)).inRoot(new ToastMatcher()).check(matches(withText("Authentication failed, check your email and password or sign up")));
    }
    @Test
    public void ToastCheckIncorrectPasswordLenLessSix()
    {
        onView(withId(R.id.email_login)).perform(typeText("saisankeerthreddy909@gmail.com"));
        onView(withId(R.id.password_login)).perform(typeText("saiva"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText(R.string.minimum_password)).inRoot(new ToastMatcher()).check(matches(withText("Password too short, enter minimum 6 characters!")));
    }
    @Test
    public void IncomeFragmentTest() {
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
    public void ExpenseFragmentTest() {
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
    public void ProfileFragmentTest() {
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
    @Test
    public void ToastCheckVerificationRequired()
    {
        onView(withId(R.id.email_login)).perform(typeText("saisankeerthreddym@gmail.com"));
        onView(withId(R.id.password_login)).perform(typeText("saivaishu10"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText(R.string.requires_verification)).inRoot(new ToastMatcher()).check(matches(withText("Email not verified yet!")));
    }
    @Test
    public void RegisterTest() {
        onView(withId(R.id.btn_noaccount)).perform(click());
        onView(withId(R.id.email_reg)).perform(typeText("saisankeerth112344@gmail.com"));
        onView(withId(R.id.password_reg)).perform(typeText("saivaishu10"));
        onView(withId(R.id.Cpassword_reg)).perform(typeText("saivaishu10"));
        closeSoftKeyboard();
        onView(withId(R.id.TC_Signup)).perform(click());
        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withText(R.string.email_verification)).inRoot(new ToastMatcher()).check(matches(withText("Registration Successful! Check your Email for Verification link!")));
    }
    @After
    public void tearDown() throws Exception {
        mActivity =null;
    }

}

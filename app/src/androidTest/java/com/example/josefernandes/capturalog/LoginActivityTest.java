package com.example.josefernandes.capturalog;

import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.josefernandes.capturalog.activity.LoginActivity;
import com.example.josefernandes.capturalog.activity.LoginActivity_;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity_> loginActivityRule = new ActivityTestRule<>(LoginActivity_.class);

    @Test
    public void shouldShowWelcomeText(){
        onView(withText("ZBRA Soluções")).check(matches(isDisplayed()));
    }

    @Test
    public void testButtonClick(){
        onView(withId(R.id.login_botao)).perform().check(matches(isDisplayed()));
    }
}

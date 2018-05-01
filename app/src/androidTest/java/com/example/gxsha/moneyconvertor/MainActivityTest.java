package com.example.gxsha.moneyconvertor;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void DefaultCurrencies_Calculate() {
        ViewInteraction appCompatEditText = onView(withId(R.id.firstValue));
        appCompatEditText.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.firstValue), withText("1")));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(withId(R.id.secondValue));
        appCompatEditText3.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.secondValue), withText("1")));
        appCompatEditText4.perform(pressImeActionButton());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.convertButton), withText("Add values")));
        appCompatButton.perform(click());

        onView(withId(R.id.resultValue))
                .check(matches(withText("2.0")));
    }

    @Test
    public void Button_Disabled_When_No_Values()
    {
        //initially there are no values on inputs
        //button should not enabled
        onView(withId(R.id.convertButton))
            .check(matches(not(isEnabled())));

        //only first field has value, button should be disabled
        ViewInteraction appCompatEditText = onView(
                withId(R.id.firstValue));
        appCompatEditText.perform(replaceText("1"), closeSoftKeyboard());

        onView(withId(R.id.convertButton))
                .check(matches(not(isEnabled())));

        appCompatEditText.perform(replaceText(""), closeSoftKeyboard());

        //only second value has value, button is disabled
        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.secondValue));
        appCompatEditText2.perform(replaceText("1"), closeSoftKeyboard());

        onView(withId(R.id.convertButton))
                .check(matches(not(isEnabled())));

        //only second value has value, button is disabled
        appCompatEditText.perform(replaceText("1"), closeSoftKeyboard());

        onView(withId(R.id.convertButton))
                .check(matches(isEnabled()));
    }

    @Test
    public void ChangeCurrencies_WithoutValues_CannotCalculate()
    {
        //Change currencies without values
        ViewInteraction appCompatSpinner = onView(withId(R.id.firstSelector));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatSpinner2 = onView(withId(R.id.secondSelector));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatSpinner3 = onView(withId(R.id.resultSelector));
        appCompatSpinner3.perform(click());

        DataInteraction appCompatCheckedTextView3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatCheckedTextView3.perform(click());

        //with changed currencies but without values button is disabled
        onView(withId(R.id.convertButton))
                .check(matches(not(isEnabled())));
    }


    @Test
    public void ChangeCurrencies_WithValues_Calculate()
    {
        //fill with values
        ViewInteraction appCompatEditText = onView(
                withId(R.id.firstValue));
        appCompatEditText.perform(replaceText("10"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.secondValue));
        appCompatEditText2.perform(replaceText("10"), closeSoftKeyboard());

        //button should be enabled
        onView(withId(R.id.convertButton))
                .check(matches(isEnabled()));

        //change currencies from usd to eur
        ViewInteraction appCompatSpinner = onView(withId(R.id.firstSelector));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatSpinner2 = onView(withId(R.id.secondSelector));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView2.perform(click());

        //calculate result from eur in usd
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.convertButton), withText("Add values")));
        appCompatButton.perform(click());

        onView(withId(R.id.resultValue))
                .check(matches(withText("24.7066")));

        //change the first currency to gbp
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatCheckedTextView3.perform(click());

        //click on button
        appCompatButton.perform(click());

        onView(withId(R.id.resultValue))
                .check(matches(withText("26.6772")));

        //change the result currency from usd to inr
        ViewInteraction appCompatSpinner3 = onView(withId(R.id.resultSelector));
        appCompatSpinner3.perform(click());

        DataInteraction appCompatCheckedTextView4 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(3);
        appCompatCheckedTextView4.perform(click());

        //click on button
        appCompatButton.perform(click());

        //check the result
        onView(withId(R.id.resultValue))
                .check(matches(withText("1751.6150000000002")));

    }
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

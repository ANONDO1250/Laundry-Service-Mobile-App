package com.example.laundryservice;

import android.content.Intent;
import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class AddCustomerActivityTest {

    private static final String TAG = "AddCustomerActivityTest";

    @Rule
    public ActivityTestRule<AddCustomerActivity> activityTestRule =
            new ActivityTestRule<>(AddCustomerActivity.class, true, true);

    @Test
    public void testAddCustomer_Successful() {
        launchAddCustomerActivity("Mehedi", "Mirpur Sewrapara", "Mohammadmehedi1250@gmail.com", "2023-01-01", "01314857541");
        checkSuccessMessage("Customer added successfully.");
    }

    @Test
    public void testAddCustomer_MissingName() {
        launchAddCustomerActivity("", "123 Main St", "mohammadmehedi1250@gmail.com", "2023-01-01", "12345678901");
        checkErrorMessage("Name is required.");
      
    }

    @Test
    public void testAddCustomer_InvalidEmail() {
        launchAddCustomerActivity("Maruf Mazumder", "456 Side St", "invalid-email", "2023-02-01", "9876543210");
        checkErrorMessage("Invalid email address.");
    }

    @Test
    public void testAddCustomer_LongPhone() {
        launchAddCustomerActivity("Monisha Akter Asha", "789 Back St", "monishagub@gmail.com", "2023-03-01", "123456789012");
        checkErrorMessage("Phone number must be 11 digits long.");
    }

    @Test
    public void testAddCustomer_EmptyFields() {
        launchAddCustomerActivity("", "", "", "", "");
        checkErrorMessage("Name is required.");
    }

    private void launchAddCustomerActivity(String name, String address, String email, String creationDate, String phone) {
        Intent intent = new Intent();
        activityTestRule.launchActivity(intent);

        Espresso.onView(ViewMatchers.withId(R.id.editTextName)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.editTextAddress)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.editTextEmail)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.editTextCreationDate)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.editTextPhone)).perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.editTextName)).perform(ViewActions.typeText(name));
        Espresso.onView(ViewMatchers.withId(R.id.editTextAddress)).perform(ViewActions.typeText(address));
        Espresso.onView(ViewMatchers.withId(R.id.editTextEmail)).perform(ViewActions.typeText(email));
        Espresso.onView(ViewMatchers.withId(R.id.editTextCreationDate)).perform(ViewActions.typeText(creationDate));
        Espresso.onView(ViewMatchers.withId(R.id.editTextPhone)).perform(ViewActions.typeText(phone));

        Espresso.onView(ViewMatchers.withId(R.id.saveButton)).perform(ViewActions.click());
    }

    private void checkSuccessMessage(String successMessage) {
        Espresso.onView(ViewMatchers.withText(successMessage))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Log.d(TAG, "Test Passed: " + successMessage);
    }

    private void checkErrorMessage(String errorMessage) {
        Espresso.onView(ViewMatchers.withText(errorMessage))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Log.e(TAG, "Test Failed: " + errorMessage);
    }

    @After
    public void tearDown() {
        activityTestRule.finishActivity();
    }
}

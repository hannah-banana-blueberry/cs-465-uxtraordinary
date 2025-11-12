package com.example.uxtraordinary;

import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented tests for FilterActivity
 * Tests UI components, user interactions, and data handling
 *
 * Run with: ./gradlew connectedAndroidTest
 */
@RunWith(AndroidJUnit4.class)
public class FilterActivityTest {

    @Rule
    public ActivityScenarioRule<FilterActivity> activityRule =
            new ActivityScenarioRule<>(FilterActivity.class);

    /**
     * Test that all UI components are displayed on launch
     */
    @Test
    public void testAllComponentsDisplayed() {
        // Check header texts are displayed
        onView(withId(R.id.tvAppTitle))
                .check(matches(isDisplayed()))
                .check(matches(withText("UIUC Study Space Finder")));

        onView(withId(R.id.tvFilterTitle))
                .check(matches(isDisplayed()))
                .check(matches(withText("Filter Study Spaces")));

        // Check current location is displayed
        onView(withId(R.id.tvCurrentLocation))
                .check(matches(isDisplayed()))
                .check(matches(withText("Thomas M. Siebel Center for Computer Science")));

        // Check distance components are displayed
        onView(withId(R.id.tvDistanceValue))
                .check(matches(isDisplayed()));

        onView(withId(R.id.seekBarDistance))
                .check(matches(isDisplayed()));

        // Check checkboxes are displayed
        onView(withId(R.id.checkBoxOpenNow))
                .check(matches(isDisplayed()));

        onView(withId(R.id.checkBoxCafeFood))
                .check(matches(isDisplayed()));

        // Check apply button is displayed
        onView(withId(R.id.btnApply))
                .check(matches(isDisplayed()))
                .check(matches(withText("Apply")));
    }

    /**
     * Test initial state of checkboxes (should be unchecked)
     */
    @Test
    public void testInitialCheckboxState() {
        onView(withId(R.id.checkBoxOpenNow))
                .check(matches(isNotChecked()));

        onView(withId(R.id.checkBoxCafeFood))
                .check(matches(isNotChecked()));
    }

    /**
     * Test checking "Open Now" checkbox
     */
    @Test
    public void testCheckOpenNowCheckbox() {
        // Click the checkbox
        onView(withId(R.id.checkBoxOpenNow))
                .perform(click());

        // Verify it's checked
        onView(withId(R.id.checkBoxOpenNow))
                .check(matches(isChecked()));

        // Click again to uncheck
        onView(withId(R.id.checkBoxOpenNow))
                .perform(click());

        // Verify it's unchecked
        onView(withId(R.id.checkBoxOpenNow))
                .check(matches(isNotChecked()));
    }

    /**
     * Test checking "Cafe/Food" checkbox
     */
    @Test
    public void testCheckCafeFoodCheckbox() {
        // Click the checkbox
        onView(withId(R.id.checkBoxCafeFood))
                .perform(click());

        // Verify it's checked
        onView(withId(R.id.checkBoxCafeFood))
                .check(matches(isChecked()));

        // Click again to uncheck
        onView(withId(R.id.checkBoxCafeFood))
                .perform(click());

        // Verify it's unchecked
        onView(withId(R.id.checkBoxCafeFood))
                .check(matches(isNotChecked()));
    }

    /**
     * Test both checkboxes can be checked simultaneously
     */
    @Test
    public void testBothCheckboxesCanBeChecked() {
        // Check both checkboxes
        onView(withId(R.id.checkBoxOpenNow))
                .perform(click());
        onView(withId(R.id.checkBoxCafeFood))
                .perform(click());

        // Verify both are checked
        onView(withId(R.id.checkBoxOpenNow))
                .check(matches(isChecked()));
        onView(withId(R.id.checkBoxCafeFood))
                .check(matches(isChecked()));
    }

    /**
     * Test initial distance value is 0.5 miles
     */
    @Test
    public void testInitialDistanceValue() {
        onView(withId(R.id.tvDistanceValue))
                .check(matches(withText("0.5 miles")));
    }

    /**
     * Test SeekBar interaction and distance display update
     */
    @Test
    public void testSeekBarInteraction() {
        activityRule.getScenario().onActivity(activity -> {
            SeekBar seekBar = activity.findViewById(R.id.seekBarDistance);
            TextView distanceValue = activity.findViewById(R.id.tvDistanceValue);

            // Set progress to 0 (0.0 miles)
            activity.runOnUiThread(() -> seekBar.setProgress(0));
            assertEquals("0.0 miles", distanceValue.getText().toString());

            // Set progress to 2 (1.0 miles)
            activity.runOnUiThread(() -> seekBar.setProgress(2));
            assertEquals("1.0 miles", distanceValue.getText().toString());

            // Set progress to 4 (2.0 miles)
            activity.runOnUiThread(() -> seekBar.setProgress(4));
            assertEquals("2.0 miles", distanceValue.getText().toString());

            // Set progress to 10 (5.0 miles)
            activity.runOnUiThread(() -> seekBar.setProgress(10));
            assertEquals("5.0 miles", distanceValue.getText().toString());
        });
    }

    /**
     * Test getFilterCriteria returns correct default values
     */
    @Test
    public void testGetFilterCriteriaDefaults() {
        activityRule.getScenario().onActivity(activity -> {
            FilterCriteria criteria = activity.getFilterCriteria();

            assertEquals(0.5f, criteria.getDistanceMiles(), 0.001f);
            assertFalse(criteria.isOpenNow());
            assertFalse(criteria.isHasCafeFood());
            assertEquals("Thomas M. Siebel Center for Computer Science",
                    criteria.getCurrentLocation());
        });
    }

    /**
     * Test getFilterCriteria after user interactions
     */
    @Test
    public void testGetFilterCriteriaAfterInteraction() {
        activityRule.getScenario().onActivity(activity -> {
            // Simulate user interactions
            SeekBar seekBar = activity.findViewById(R.id.seekBarDistance);
            CheckBox openNow = activity.findViewById(R.id.checkBoxOpenNow);
            CheckBox cafeFood = activity.findViewById(R.id.checkBoxCafeFood);

            activity.runOnUiThread(() -> {
                seekBar.setProgress(6); // 3.0 miles
                openNow.setChecked(true);
                cafeFood.setChecked(true);
            });

            // Get filter criteria
            FilterCriteria criteria = activity.getFilterCriteria();

            // Verify values
            assertEquals(3.0f, criteria.getDistanceMiles(), 0.001f);
            assertTrue(criteria.isOpenNow());
            assertTrue(criteria.isHasCafeFood());
        });
    }

    /**
     * Test setCurrentLocation method updates the display
     */
    @Test
    public void testSetCurrentLocation() {
        activityRule.getScenario().onActivity(activity -> {
            String newLocation = "Grainger Engineering Library";

            activity.runOnUiThread(() -> {
                activity.setCurrentLocation(newLocation);
            });

            TextView locationView = activity.findViewById(R.id.tvCurrentLocation);
            assertEquals(newLocation, locationView.getText().toString());

            // Verify it's also in filter criteria
            FilterCriteria criteria = activity.getFilterCriteria();
            assertEquals(newLocation, criteria.getCurrentLocation());
        });
    }

    /**
     * Test Apply button click finishes the activity
     */
    @Test
    public void testApplyButtonFinishesActivity() {
        // Click apply button
        onView(withId(R.id.btnApply))
                .perform(click());

        // Verify activity is finished
        activityRule.getScenario().onActivity(activity -> {
            assertTrue(activity.isFinishing() || activity.isDestroyed());
        });
    }

    /**
     * Test SeekBar boundary values
     */
    @Test
    public void testSeekBarBoundaryValues() {
        activityRule.getScenario().onActivity(activity -> {
            SeekBar seekBar = activity.findViewById(R.id.seekBarDistance);
            TextView distanceValue = activity.findViewById(R.id.tvDistanceValue);

            // Test minimum (0)
            activity.runOnUiThread(() -> seekBar.setProgress(0));
            assertEquals("0.0 miles", distanceValue.getText().toString());

            // Test maximum (10)
            activity.runOnUiThread(() -> seekBar.setProgress(10));
            assertEquals("5.0 miles", distanceValue.getText().toString());

            // Test mid-range values
            activity.runOnUiThread(() -> seekBar.setProgress(5));
            assertEquals("2.5 miles", distanceValue.getText().toString());

            activity.runOnUiThread(() -> seekBar.setProgress(7));
            assertEquals("3.5 miles", distanceValue.getText().toString());
        });
    }

    /**
     * Test multiple filter combinations
     */
    @Test
    public void testMultipleFilterCombinations() {
        activityRule.getScenario().onActivity(activity -> {
            SeekBar seekBar = activity.findViewById(R.id.seekBarDistance);
            CheckBox openNow = activity.findViewById(R.id.checkBoxOpenNow);
            CheckBox cafeFood = activity.findViewById(R.id.checkBoxCafeFood);

            // Combination 1: Distance only
            activity.runOnUiThread(() -> {
                seekBar.setProgress(4);
                openNow.setChecked(false);
                cafeFood.setChecked(false);
            });
            FilterCriteria criteria1 = activity.getFilterCriteria();
            assertEquals(2.0f, criteria1.getDistanceMiles(), 0.001f);
            assertFalse(criteria1.isOpenNow());
            assertFalse(criteria1.isHasCafeFood());

            // Combination 2: Open now only
            activity.runOnUiThread(() -> {
                seekBar.setProgress(1);
                openNow.setChecked(true);
                cafeFood.setChecked(false);
            });
            FilterCriteria criteria2 = activity.getFilterCriteria();
            assertEquals(0.5f, criteria2.getDistanceMiles(), 0.001f);
            assertTrue(criteria2.isOpenNow());
            assertFalse(criteria2.isHasCafeFood());

            // Combination 3: All filters active
            activity.runOnUiThread(() -> {
                seekBar.setProgress(8);
                openNow.setChecked(true);
                cafeFood.setChecked(true);
            });
            FilterCriteria criteria3 = activity.getFilterCriteria();
            assertEquals(4.0f, criteria3.getDistanceMiles(), 0.001f);
            assertTrue(criteria3.isOpenNow());
            assertTrue(criteria3.isHasCafeFood());
        });
    }

    /**
     * Test distance increments of 0.5 miles
     */
    @Test
    public void testDistanceIncrements() {
        activityRule.getScenario().onActivity(activity -> {
            SeekBar seekBar = activity.findViewById(R.id.seekBarDistance);
            TextView distanceValue = activity.findViewById(R.id.tvDistanceValue);

            // Test each increment
            for (int i = 0; i <= 10; i++) {
                final int progress = i;
                activity.runOnUiThread(() -> seekBar.setProgress(progress));

                float expectedMiles = progress * 0.5f;
                String expectedText = String.format("%.1f miles", expectedMiles);
                assertEquals(expectedText, distanceValue.getText().toString());
            }
        });
    }
}
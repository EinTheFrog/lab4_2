package com.example.myapplication

import android.content.pm.ActivityInfo
import androidx.core.app.ActivityCompat.recreate
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.pressBack
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @Test
    fun testAbout() {
        launchActivity<MainActivity>()
        openAbout()
        checkAbout()
    }

    @Test
    fun testFragment1() {
        launchActivity<MainActivity>()
        checkFragment1()
    }

    @Test
    fun testFragment2() {
        launchActivity<MainActivity>()
        onView(withId(R.id.bnToSecond)).perform(click())
        checkFragment2()
    }

    @Test
    fun testFragment3() {
        launchActivity<MainActivity>()
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        checkFragment3()
    }

    @Test
    fun testBackFromSecond1() {
        launchActivity<MainActivity>()
        onView(withId(R.id.bnToSecond)).perform(click())
        pressBack()
        checkFragment1()
    }

    @Test
    fun testBackFromSecond2() {
        launchActivity<MainActivity>()
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        onView(withId(R.id.bnToSecond)).perform(click())
        pressBack()
        checkFragment1()
    }

    @Test
    fun testBackFromThird() {
        launchActivity<MainActivity>()
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        pressBack()
        checkFragment2()
    }

    @Test
    fun testBackFromAbout1() {
        launchActivity<MainActivity>()
        openAbout()
        pressBack()
        checkFragment1()
    }

    @Test
    fun testBackFromAbout2() {
        launchActivity<MainActivity>()
        onView(withId(R.id.bnToSecond)).perform(click())
        openAbout()
        pressBack()
        checkFragment2()
    }

    @Test
    fun testBackFromAbout3() {
        launchActivity<MainActivity>()
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        openAbout()
        pressBack()
        checkFragment3()
    }

    @Test
    fun testOrientationChange1() {
        val activityScenario = launchActivity<MainActivity>()
        checkFragment1()
        changeScreenOrientation(activityScenario)
        checkFragment1()
    }

    @Test
    fun testOrientationChange2() {
        val activityScenario = launchActivity<MainActivity>()
        onView(withId(R.id.bnToSecond)).perform(click())
        checkFragment2()
        changeScreenOrientation(activityScenario)
        checkFragment2()
    }

    @Test
    fun testOrientationChange3() {
        val activityScenario = launchActivity<MainActivity>()
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        checkFragment3()
        changeScreenOrientation(activityScenario)
        checkFragment3()
    }

    @Test
    fun testOrientationChangeAbout() {
        val activityScenario = launchActivity<MainActivity>()
        openAbout()
        checkAbout()
        changeScreenOrientation(activityScenario)
        checkAbout()
    }

    @Test
    fun testNavigateUp() {
        launchActivity<MainActivity>()
        openAbout()
        checkAbout()
        onView(withContentDescription(R.string.nav_app_bar_navigate_up_description)).perform(click())
        checkFragment1()
    }

    @Test
    fun testNavigateUpAfterRotation() {
        val activityScenario = launchActivity<MainActivity>()
        openAbout()
        checkAbout()
        changeScreenOrientation(activityScenario)
        onView(withContentDescription(R.string.nav_app_bar_navigate_up_description)).perform(click())
        checkFragment1()
    }

    private fun checkFragment1() {
        onView(withId(R.id.fragment1)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_main)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToSecond)).check(matches(isDisplayed()))
    }

    private fun checkFragment2() {
        onView(withId(R.id.fragment2)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_main)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToFirst)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToThird)).check(matches(isDisplayed()))
    }

    private fun checkFragment3() {
        onView(withId(R.id.fragment3)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_main)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToFirst)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToSecond)).check(matches(isDisplayed()))
    }

    private fun checkAbout() {
        onView(withId(R.id.activity_about)).check(matches(isDisplayed()))
        onView(withId(R.id.tvAbout)).check(matches(isDisplayed()))
    }

    private fun changeScreenOrientation(activityScenario: ActivityScenario<MainActivity>) {
        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        Thread.sleep(500)
        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        Thread.sleep(500)
    }
}
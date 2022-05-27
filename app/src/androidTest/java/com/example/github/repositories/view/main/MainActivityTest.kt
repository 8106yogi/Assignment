package com.example.github.repositories.view.main

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.github.repositories.R
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testLoadingOfFragment() {
        onView(withId(R.id.parent)).check(matches(isDisplayed()))
    }


    fun findElement(arr: IntArray, size: Int): Int {
        var right_sum = 0
        var left_sum = 0

        // Computing right_sum
        for (i in 1 until size) right_sum += arr[i]

        // Checking the point of partition
        // i.e. left_Sum == right_sum
        var i = 0
        var j = 1
        while (j < size) {
            right_sum -= arr[j]
            left_sum += arr[i]
            if (left_sum == right_sum) return i+1
            i++
            j++
        }
        return -1
    }

    @After
    fun tearDown() {
    }
}
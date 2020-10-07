package com.antonystudio.facts

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.antonystudio.facts.ui.main.MainActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

class FactTest {
    @get:Rule
    val main: ActivityTestRule<MainActivity> =
        ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun testcaseForRecyclerScroll() {
        //scroll to the end of the page with position
        Espresso.onView(ViewMatchers.withId(R.id.recyclerFacts))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(main.getActivity().getWindow().getDecorView())
                )
            )
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
    }

    @Test
    fun testCaseForRecyclerItemView() {
        //viewing the items in recyclerview
        Espresso.onView(ViewMatchers.withId(R.id.recyclerFacts))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testCaseForSwipeRefreshLayout() {
        //swipe down in swipe refresh layout
        Espresso.onView(ViewMatchers.withId(R.id.swipeRefresh)).perform(ViewActions.swipeDown())
    }
}
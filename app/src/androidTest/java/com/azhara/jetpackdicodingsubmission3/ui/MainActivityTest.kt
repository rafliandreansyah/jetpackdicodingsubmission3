package com.azhara.jetpackdicodingsubmission3.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.azhara.jetpackdicodingsubmission3.R
import com.azhara.jetpackdicodingsubmission3.utils.DataDummy
import com.azhara.jetpackdicodingsubmission3.utils.IdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    private val dataMovie = DataDummy.generateDataMovie()[0]
    private val dataTv = DataDummy.generateDataTv()[0]

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(IdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(IdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadAllMovie() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.movie_detail_title)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_detail_title)).check(matches(withText(dataMovie.title)))
        onView(withId(R.id.movie_detail_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_detail_rating)).check(matches(withText(dataMovie.vote_average)))
        onView(withId(R.id.movie_detail_release)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_detail_release)).check(matches(withText(dataMovie.release_date)))
        onView(withId(R.id.movie_detail_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_detail_overview)).check(matches(withText(dataMovie.overview)))
    }

    @Test
    fun loadAllTv() {
        onView(withId(R.id.navigation_tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )
    }

    @Test
    fun loadDetailTv() {
        onView(withId(R.id.navigation_tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_detail_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_title)).check(matches(withText(dataTv.name)))
        onView(withId(R.id.tv_detail_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_rating)).check(matches(withText(dataTv.vote_average)))
        onView(withId(R.id.tv_detail_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_release)).check(matches(withText(dataTv.first_air_date)))
        onView(withId(R.id.tv_detail_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_overview)).check(matches(withText(dataTv.overview)))
    }

    @Test
    fun loadMovieFavorite() {
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.action_bookmark)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.rv_movie_local)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvFavorite() {
        onView(withId(R.id.navigation_tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.action_bookmark)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_tv_local)).check(matches(isDisplayed()))
    }

}
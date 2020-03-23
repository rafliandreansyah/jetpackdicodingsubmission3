package com.azhara.jetpackdicodingsubmission3.utils

import androidx.test.espresso.idling.CountingIdlingResource

object IdlingResource {
    private const val RESOURCE = "GLOBAL"

    val espressoTestIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        espressoTestIdlingResource.increment()
    }

    fun decrement() {
        espressoTestIdlingResource.decrement()
    }
}
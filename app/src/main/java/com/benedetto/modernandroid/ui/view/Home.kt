package com.benedetto.modernandroid.ui.view

import com.benedetto.modernandroid.ui.navigation.ScreenItem
import com.benedetto.modernandroid.ui.navigation.ScreenTag


private val screenItems = listOf(
    ScreenItem("Users", "A list of users using Retrofit", ScreenTag.USERS),
    ScreenItem("SpaceX", "GraphQL data for space x launch/flight list", ScreenTag.SPACEX),
    ScreenItem("Bluetooth", "Scan available Bluetooth Connections", ScreenTag.BLE),
    ScreenItem("Counter", "Simple counter", ScreenTag.COUNTER),
    ScreenItem("Transaction", "Fake transaction screen", ScreenTag.TRANSACTION),
)

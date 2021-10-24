package org.goodexpert.apps.smartpay.ui

sealed class Routing {
    object Splash : Routing()
    object MainContent : Routing()

    object HomeScreen : Routing()
    object EnterAmount : Routing()
}

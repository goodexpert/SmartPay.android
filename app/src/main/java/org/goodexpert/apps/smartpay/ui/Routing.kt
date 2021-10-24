package org.goodexpert.apps.smartpay.ui

sealed class Routing {
    object Splash : Routing()
    object MainContent : Routing()

    object HomeScreen : Routing()
    object EnterAmount : Routing()

    data class PurchaseScreen(val amount: Number) : Routing()
    data class PurchasedResultScreen(val isSuccess: Boolean) : Routing()

    object PurchaseMainScreen : Routing()
    object ProcessingScreen : Routing()
}

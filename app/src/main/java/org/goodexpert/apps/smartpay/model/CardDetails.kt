package org.goodexpert.apps.smartpay.model

data class CardDetails(
    val pan: String = "",
    val expiryDate: String = "",
    val cvv: String = "",
    val isRecurring: Boolean = false,
    val isSavedOnFile: Boolean = false
)

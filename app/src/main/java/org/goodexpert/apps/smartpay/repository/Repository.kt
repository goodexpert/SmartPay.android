package org.goodexpert.apps.smartpay.repository

import kotlinx.coroutines.delay
import org.goodexpert.apps.smartpay.model.CardDetails

interface Repository {
    suspend fun save(cardDetails: CardDetails): CardDetails?
}

class MotoRepository : Repository {

    override suspend fun save(cardDetails: CardDetails): CardDetails? {
        delay(1000L)
        return null
    }
}
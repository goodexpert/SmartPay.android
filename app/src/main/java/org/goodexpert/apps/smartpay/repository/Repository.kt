package org.goodexpert.apps.smartpay.repository

import org.goodexpert.apps.smartpay.model.CardDetails

interface Repository {
    suspend fun save(cardDetails: CardDetails): CardDetails?
}

class MotoRepository : Repository {

    override suspend fun save(cardDetails: CardDetails): CardDetails? {
        Thread.sleep(1000L)
        return null
    }
}
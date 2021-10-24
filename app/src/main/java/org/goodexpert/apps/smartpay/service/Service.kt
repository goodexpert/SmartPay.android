package org.goodexpert.apps.smartpay.service

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.goodexpert.apps.smartpay.model.CardDetails
import java.io.Closeable
import java.io.IOException
import java.util.HashMap
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface Service {
    suspend fun purchase(amount: Number, cardDetails: CardDetails): Boolean
}

internal class CloseableCoroutineScope(context: CoroutineContext) : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = context

    override fun close() {
        coroutineContext.cancel()
    }
}

abstract class BaseService : Service {
    companion object {
        private val JOB_KEY = "org.goodexpert.apps.smartpay.service.JOB_KEY"

        private fun closeWithRuntimeException(obj: Any) {
            if (obj is Closeable) {
                try {
                    obj.close()
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }
        }
    }

    private val bagOfTags: HashMap<String, Any> = HashMap()
    private var isCleared: Boolean = false

    val serviceScope: CoroutineScope
        get() {
            val scope: CoroutineScope? = this.getTag(JOB_KEY)
            if (scope != null) {
                return scope
            }
            return setTagIfAbsent(
                JOB_KEY,
                CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
            )
        }

    open fun <T : Any> setTagIfAbsent(key: String, newValue: T): T {
        var previous: T?
        synchronized(bagOfTags) {
            previous = bagOfTags.get(key) as T
            if (previous == null) {
                bagOfTags.put(key, newValue)
            }
        }
        val result = if (previous == null) newValue else previous!!
        if (isCleared) {
            // It is possible that we'll call close() multiple times on the same object, but
            // Closeable interface requires close method to be idempotent:
            // "if the stream is already closed then invoking this method has no effect." (c)
            closeWithRuntimeException(result)
        }
        return result
    }

    /**
     * Returns the tag associated with this viewmodel and the specified key.
     */
    open fun <T : Any> getTag(key: String?): T? {
        synchronized(bagOfTags) { return bagOfTags.get(key) as? T }
    }

    open fun onCleared() {
        if (bagOfTags != null) {
            synchronized(bagOfTags) {
                for (value in bagOfTags.values) {
                    // see comment for the similar call in setTagIfAbsent
                    closeWithRuntimeException(value)
                }
            }
        }
    }
}

@ViewModelScoped
class MotoService @Inject constructor() : BaseService() {

    override suspend fun purchase(amount: Number, cardDetails: CardDetails): Boolean {
        Thread.sleep(1000L)
        return true
    }
}
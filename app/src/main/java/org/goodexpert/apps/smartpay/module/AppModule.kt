package org.goodexpert.apps.smartpay.module

import com.github.zsoltk.compose.backpress.BackPressHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBackPressHandler(): BackPressHandler {
        return BackPressHandler()
    }
}
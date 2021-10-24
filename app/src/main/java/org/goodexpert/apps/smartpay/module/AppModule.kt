package org.goodexpert.apps.smartpay.module

import com.github.zsoltk.compose.backpress.BackPressHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.goodexpert.apps.smartpay.repository.MotoRepository
import org.goodexpert.apps.smartpay.repository.Repository
import org.goodexpert.apps.smartpay.service.MotoService
import org.goodexpert.apps.smartpay.service.Service

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBackPressHandler(): BackPressHandler {
        return BackPressHandler()
    }

    @Provides
    fun provideRepository(): Repository {
        return MotoRepository()
    }

    @Provides
    fun provideService(): Service {
        return MotoService()
    }
}
package org.goodexpert.apps.smartpay.viewmodel

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runBlockingTest
import org.goodexpert.apps.smartpay.model.CardDetails
import org.goodexpert.apps.smartpay.repository.Repository
import org.goodexpert.apps.smartpay.rule.MainCoroutineRule
import org.goodexpert.apps.smartpay.service.Service
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MotoViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Inject lateinit var repository: Repository
    @Inject lateinit var service: Service

    private lateinit var viewModel: MotoViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        viewModel = MotoViewModel(repository, service)
    }

    @Test
    fun motoViewModelTest_purchase() = runBlockingTest {
        viewModel.purchase(200.0, CardDetails("5456789012345670", "1024", "123"))

        val result = viewModel.uiEffect.firstOrNull() as? MotoViewModel.Effect.PurchasedResult
        Assert.assertNotNull("PurchasedResult is always not null", result)
        Assert.assertTrue("Purchase is always success", (result?.isSuccess == true))

        Thread.sleep(1000L)
    }
}
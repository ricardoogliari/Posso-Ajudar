package com.atitus.possoajudar.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.atitus.possoajudar.model.History
import com.atitus.possoajudar.services.ApiState
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoriesViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HistoriesViewModel

    @Before
    fun setupViewModel() {
        viewModel = HistoriesViewModel()
    }

    @Test
    fun addHistory() {
        // Given a fresh ViewModel
        //ApplicationProvider.getApplicationContext()
        val observer = Observer<ApiState> {}
        try {
            viewModel.response.observeForever(observer)

            viewModel.addHistory(History("Teste", "Teste", 0.0, 0.0))

            assertThat(viewModel.histories.size, `is`(1))
            assertThat(viewModel.histories[0].title, `is` ("Teste"))
        } finally {
            viewModel.response.removeObserver(observer)
        }
    }
}
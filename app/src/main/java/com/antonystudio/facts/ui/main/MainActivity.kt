package com.antonystudio.facts.ui.main

import android.os.Bundle
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.antonystudio.facts.R
import com.antonystudio.facts.adapter.FactsAdapter
import com.antonystudio.facts.api.ApiResponse
import com.antonystudio.facts.api.NetworkState
import com.antonystudio.facts.databinding.ActivityMainBinding
import com.antonystudio.facts.extensions.autoDispose
import com.antonystudio.facts.extensions.getViewModel
import com.antonystudio.facts.ui.base.BaseActivity
import com.antonystudio.facts.utility.Utilities
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<MainActivityViewModel>() {
    val LOAD_ELEMENTS_WITH_DELAY: Long = 500L
    private val factsItemAdapter by lazy { FactsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityBinding.viewModel = viewModel
        activityBinding.adapter = factsItemAdapter
        try {
            Observable.timer(LOAD_ELEMENTS_WITH_DELAY, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    getObservableDataStream()
                    viewModel.getFactDetails(this)
                }.autoDispose(disposables)
        } catch (e: Exception) {
            println("TAG -- MyData --> ${e.message}")
        }
    }


    //Method for observing the data stream
    private fun getObservableDataStream() {
        viewModel.getFacts().observe(this, Observer {
            factsItemAdapter.setData(it)
            if (viewModel.getAppBarTitle().isEmpty() && it.isNotEmpty()) {
                toolBar.title = it[0].mainTitle
            } else {
                toolBar.title = viewModel.getAppBarTitle()
            }
        })

        viewModel.getDataStream().observe(this, Observer {
            when (it) {
                is NetworkState.LoadingData -> {
                    tvNoItem.visibility = View.GONE
                    if (!swipeRefresh.isRefreshing && factsItemAdapter.itemCount == 0) {
                        progressBar.visibility = View.VISIBLE
                    }
                }
                is NetworkState.NetworkNotAvailable -> {
                    manageProgressBar()
                    showErrorMessage()
                }
                is NetworkState.ErrorResponse -> {
                    manageProgressBar()
                    showErrorMessage()
                }
                is NetworkState.SuccessResponse<*> -> {
                    val data = it.data as ApiResponse
                    tvNoItem.visibility = View.GONE
                    if (data.getFactsData().isEmpty() && factsItemAdapter.itemCount == 0) {
                        tvNoItem.visibility = View.VISIBLE
                        if (viewModel.getAppBarTitle().isNotEmpty())
                            toolBar.title = viewModel.getAppBarTitle()
                    }
                    manageProgressBar()
                }
            }
        })
    }

    //Method for managing the progress bar
    private fun manageProgressBar() {
        swipeRefresh.isRefreshing = false
        progressBar.visibility = View.GONE
    }

    //Method for showing the error message
    private fun showErrorMessage() {
        Utilities.showAlertMessage(
            this, R.string.error, R.string.api_connection_error
        ).setOnDismissListener {
            if (factsItemAdapter.itemCount == 0) {
                tvNoItem.visibility = View.VISIBLE
            }
        }
    }

    override fun provideViewModel(): MainActivityViewModel =
        getViewModel { MainActivityViewModel.getInstance(dataManager) }

}

@BindingAdapter("setAdapter")
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
    this.run {
        this.adapter = adapter
    }
}

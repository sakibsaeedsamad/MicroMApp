package com.sssakib.micromapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sssakib.micromapp.model.AccountModel
import com.sssakib.micromapp.model.ApproveModel
import com.sssakib.micromapp.model.DetailPendingModel
import com.sssakib.micromapp.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class AccountViewModel: ViewModel() {

    private val apiService = RetrofitClient()
    private val disposable = CompositeDisposable()

    var accountPendingList_response = MutableLiveData<List<AccountModel>>();
    var accountPendingList_response_error = MutableLiveData<Boolean>();

    var transactionApprove_response = MutableLiveData<AccountModel>();
    var transactionApprove_response_error = MutableLiveData<Boolean>();

    fun doGetAllPendingTransaction(model: DetailPendingModel) {

        disposable.add(
            apiService.doGetAllPendingTransaction(model)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<AccountModel>>() {
                    override fun onSuccess(model: List< AccountModel>) {
                        model?.let {
                            accountPendingList_response.value=model
                        }

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        accountPendingList_response_error.value = true
                        Log.e("Login-->", e.toString())

                    }

                })
        )
    }

    fun doApproveTransaction(model: ApproveModel) {

        disposable.add(
            apiService.doApproveTransaction(model)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<AccountModel>() {
                    override fun onSuccess(model: AccountModel) {
                        model?.let {
                            transactionApprove_response.value=model
                        }

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        transactionApprove_response_error.value = true
                        Log.e("Login-->", e.toString())

                    }

                })
        )
    }


}
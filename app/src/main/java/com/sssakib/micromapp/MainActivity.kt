package com.sssakib.micromapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.sssakib.micromapp.adapter.PendingListAdapter
import com.sssakib.micromapp.model.AccountModel
import com.sssakib.micromapp.model.ApproveModel
import com.sssakib.micromapp.model.DetailPendingModel
import com.sssakib.micromapp.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_dialog.*
import kotlinx.android.synthetic.main.pending_list.*

class MainActivity : AppCompatActivity(), PendingListAdapter.OnRowClickListener {

    private lateinit var accountViewModel: AccountViewModel
    private val pendingListAdapter = PendingListAdapter(arrayListOf(), this@MainActivity)

    lateinit var id: String
    lateinit var amount: String
    lateinit var remarks: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)


        doGetAllPendingTransaction()
        observeViewModel()

       attachRecyclerView()


    }

    private fun attachRecyclerView() {
        recyclerViewPendingList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pendingListAdapter
        }
    }

    private fun observeViewModel() {

        accountViewModel.accountPendingList_response_error.observe(
            this,
            androidx.lifecycle.Observer {
                it?.let {


                    Log.e("Login-->", "Error Found")

                }
            })

        accountViewModel.accountPendingList_response.observe(this, Observer { it ->

            it?.let {

                if (it.size >= 1) {

                    pendingListAdapter.updatePendingAccountList(it)

                } else {
                    Log.e("Login-->", "Error Found")
                }


            }
        })

        accountViewModel.transactionApprove_response_error.observe(
            this,
            androidx.lifecycle.Observer {
                it?.let {


                    Log.e("Login-->", "Error Found")

                }
            })

        accountViewModel.transactionApprove_response.observe(this, androidx.lifecycle.Observer {
            it?.let {

                if ("0".equals(it.outCode)) {

                    Log.d("Message", it.outMessage.toString())
                    Toast.makeText(
                        this,
                        "Message : " + it.outMessage.toString(),
                        Toast.LENGTH_SHORT
                    ).show()


                }


                Log.e("Login-->", "Error Found")

            }
        })


    }

    private fun doGetAllPendingTransaction() {
        var model = DetailPendingModel()
        model.requestCode = ("1")

        this.let { it1 -> accountViewModel.doGetAllPendingTransaction(model) }
    }

    override fun onClick(accountModel: AccountModel) {



        val dialog = MaterialDialog(this)
            .noAutoDismiss()
            .customView(R.layout.main_dialog)

        id = accountModel.id.toString()
        amount = accountModel.amount.toString()
        remarks = accountModel.remarks.toString()

        dialog.tvIdDialog.text= "Id: " + id
        dialog.tvAmountDialog.text = "Amount: " + amount
        dialog.tvRemarksDialog.text = "Remarks: " + remarks



        dialog.btnApproveDialog.setOnClickListener {

            doApproveTransaction()
            doGetAllPendingTransaction()
            dialog.dismiss()

        }

        dialog.btnNO.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()


    }

    private fun doApproveTransaction() {
        var model = ApproveModel()
        model.requestCode = ("2")
        model.id = id
        model.flag ="Approved"

        this.let { it1 -> accountViewModel.doApproveTransaction(model) }
    }




}
package com.sssakib.micromapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sssakib.micromapp.R
import com.sssakib.micromapp.model.AccountModel
import kotlinx.android.synthetic.main.pending_list.view.*

class PendingListAdapter (val pendingList: ArrayList<AccountModel>, val listener: OnRowClickListener): RecyclerView.Adapter<PendingListAdapter.PendingListViewHolder>() {

    fun updatePendingAccountList(newPendingAccountList : List<AccountModel>){
        pendingList.clear()
        pendingList.addAll(newPendingAccountList)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PendingListAdapter.PendingListViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.pending_list,parent,false)

        return PendingListViewHolder(inflater,listener)
    }

    override fun onBindViewHolder(holder: PendingListAdapter.PendingListViewHolder, position: Int) {


        holder.itemView.setOnClickListener {
            listener.onClick(pendingList[position])
        }
        holder.bind(pendingList[position])
    }

    override fun getItemCount(): Int {
        return pendingList.size
    }

    class PendingListViewHolder(view: View, val listener: OnRowClickListener): RecyclerView.ViewHolder(view) {

        val id = view.tvId
        val amount = view.tvAmount
        val remarks = view.tvRemarks

        fun bind(data : AccountModel){
            id.text = "Id: " + data.id
            amount.text = "Amount: "+ data.amount
            remarks.text = "Remarks: " + data.remarks
        }

    }

    interface OnRowClickListener {
        fun onClick(accountModel: AccountModel)
    }
}
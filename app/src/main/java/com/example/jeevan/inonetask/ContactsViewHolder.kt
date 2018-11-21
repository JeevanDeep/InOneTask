package com.example.jeevan.inonetask

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.contacts_item_row.view.*

class ContactsViewHolder(itemView: View, var listener: ContactListener) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.tvName
    private val phone: TextView = itemView.tvPhoneNumber
    private val rootView: ConstraintLayout = itemView.findViewById(R.id.rootView)

    interface ContactListener {
        fun onContactClicked(contactsModel: ContactsModel)
    }

    fun setItems(contactsModel: ContactsModel) {
        name.text = contactsModel.name
        phone.text = contactsModel.phoneNumber
        rootView.setOnClickListener {
            listener.onContactClicked(contactsModel)
        }
    }

}
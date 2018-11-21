package com.example.jeevan.inonetask

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ContactsAdapter : RecyclerView.Adapter<ContactsViewHolder> {
    private val contactsList: MutableList<ContactsModel>
    private val listener: ContactsViewHolder.ContactListener

    constructor(listener: ContactsViewHolder.ContactListener, list: MutableList<ContactsModel>) {
        this.contactsList = list
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contacts_item_row, parent, false)
        return ContactsViewHolder(v, listener)
    }

    override fun getItemCount(): Int = contactsList.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.setItems(contactsList[position])
    }
}
package com.example.jeevan.inonetask.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jeevan.inonetask.model.ContactsModel
import com.example.jeevan.inonetask.R
import kotlinx.android.synthetic.main.fragment_call.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class CallFragment : Fragment() {

    lateinit var contactsModel: ContactsModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_call, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewCall.setOnClickListener {

            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contactsModel.phoneNumber))
            startActivity(intent)
        }
    }

    @Subscribe(sticky = true)
    fun updateContct(contactsModel: ContactsModel) {
        if (viewCall.visibility == View.GONE)
            viewCall.visibility = View.VISIBLE

        this.contactsModel = contactsModel
        tvName.text = contactsModel.name
        tvPhoneNumber.text = contactsModel.phoneNumber
    }

    override fun onStart() {
        super.onStart()

        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()

        EventBus.getDefault().unregister(this)
    }
}

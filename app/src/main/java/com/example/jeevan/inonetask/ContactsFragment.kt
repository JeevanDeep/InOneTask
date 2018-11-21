package com.example.jeevan.inonetask


import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.greenrobot.eventbus.EventBus


class ContactsFragment : Fragment(), ContactsViewHolder.ContactListener {
    private lateinit var contactsList: ArrayList<ContactsModel>
    private lateinit var adapter: ContactsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactsList = ArrayList()

        initRecyclerView()
        checkPermission()
    }

    private fun checkPermission() {

        Dexter.withActivity(activity)
            .withPermission(android.Manifest.permission.READ_CONTACTS)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    getContacts()

                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(context, "Please accept contact permission", Toast.LENGTH_SHORT).show()

                }
            }).check()
    }

    private fun initRecyclerView() {
        adapter = ContactsAdapter(this@ContactsFragment, contactsList)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ContactsFragment.context)
            adapter = this@ContactsFragment.adapter
            addItemDecoration(DividerItemDecoration(this@ContactsFragment.context, DividerItemDecoration.VERTICAL))
        }


    }

    private fun getContacts() {

        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val selection = ContactsContract.Contacts.HAS_PHONE_NUMBER
        val cursor = context?.contentResolver?.query(
            uri,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.Contacts._ID
            ),
            selection,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        cursor?.moveToFirst()

        while (cursor?.isAfterLast == false) {

            val contactNumber =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contactName =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            contactsList.add(ContactsModel(contactNumber, contactName))

            cursor.moveToNext()

        }
        cursor?.close()
        if (contactsList.size > 0) {
            EventBus.getDefault().postSticky(contactsList[0])
            Log.e("frag", "gya iske andar")
        }
        adapter.notifyDataSetChanged()
    }


    override fun onContactClicked(contactsModel: ContactsModel) {

        EventBus.getDefault().postSticky(contactsModel)

        EventBus.getDefault().postSticky(MoveToSecondTabEvent())
    }

}

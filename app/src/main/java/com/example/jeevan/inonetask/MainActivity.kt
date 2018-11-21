package com.example.jeevan.inonetask

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.jeevan.inonetask.adapter.ViewPagerAdapter
import com.example.jeevan.inonetask.fragment.CallFragment
import com.example.jeevan.inonetask.fragment.ContactsFragment
import com.example.jeevan.inonetask.model.MoveToSecondTabEvent
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        setupViewPager()

    }

    private fun setupViewPager() {
        val pagerAdapter = ViewPagerAdapter(supportFragmentManager)

        val contactsFragment = ContactsFragment()
        val callFragment = CallFragment()

        pagerAdapter.addFragment(contactsFragment, "Contacts")
        pagerAdapter.addFragment(callFragment, "Make Call")

        viewpager.adapter = pagerAdapter
        tabs.setupWithViewPager(viewpager)
    }

    override fun onBackPressed() {
        if (viewpager.currentItem == 1)
            viewpager.currentItem = 0
        else
            super.onBackPressed()
    }

    @Subscribe
    fun moveToSecondTab(moveToSecondTabEvent: MoveToSecondTabEvent) {
        viewpager.currentItem = 1
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

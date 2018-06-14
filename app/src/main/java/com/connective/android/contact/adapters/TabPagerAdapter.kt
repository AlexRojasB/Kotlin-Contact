package com.connective.android.contact.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.connective.android.contact.ContactFragment
import com.connective.android.contact.DialFragment
import com.connective.android.contact.MessagesFragment

class TabPagerAdapter(fm: FragmentManager, private var tabCount: Int) :
        FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        when (position) {
            0 -> return DialFragment()
            1 -> return ContactFragment()
            2 -> return MessagesFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}
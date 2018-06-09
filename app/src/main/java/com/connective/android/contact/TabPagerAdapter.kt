package com.connective.android.contact

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

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
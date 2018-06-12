package com.connective.android.contact.models

import java.text.SimpleDateFormat
import java.util.*

class RecentCallers {
    val CallerName: String?
    var CallerNumber: String?
    val CallerDate:String?
    val CallerChildren: ArrayList<RecentChild>?

    constructor(callerName: String?, callerNumber: String?, callerDate: String?, recentChild: ArrayList<RecentChild>?) {
        this.CallerName = callerName
        this.CallerNumber = callerNumber
        this.CallerChildren = recentChild
        val dateFormat = SimpleDateFormat("MM/dd")
        val netDate = Date(callerDate!!.toLong())
        this.CallerDate = dateFormat.format(netDate)
    }


}
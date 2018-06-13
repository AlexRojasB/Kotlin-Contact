package com.connective.android.contact.models

import java.text.SimpleDateFormat
import java.util.*

class RecentCallers {
    val CallerName: String?
    var CallerNumber: String?
    var CallerDate: String?
    var OriginalDate: Long = 0
    val CallerChildren: ArrayList<RecentChild>?
    val CallDuration: String

    constructor(callerName: String?, callerNumber: String?, callerDate: Long, recentChild: ArrayList<RecentChild>?, callDuration: String) {
        this.CallerName = callerName
        this.CallerNumber = callerNumber
        this.CallerChildren = recentChild
        this.OriginalDate = callerDate
        val dateFormat = SimpleDateFormat("MM/dd")
        val netDate = Date(callerDate)
        this.CallerDate = dateFormat.format(netDate)
        this.CallDuration = callDuration
    }


}
package com.connective.android.contact.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import kotlinx.android.synthetic.main.recent_calls_row.view.*
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.connective.android.contact.R
import com.connective.android.contact.models.RecentCallers
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class RecentCallsAdapter(callerList: ArrayList<RecentCallers>) : RecyclerView.Adapter<RecentCallsAdapter.ViewHolder>() {
    val CallersList: ArrayList<RecentCallers> = callerList
    val generator: ColorGenerator = ColorGenerator.MATERIAL

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent!!.context)
        return ViewHolder(inflater.inflate(viewType, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        if(this.CallersList[position].DiferentDate){
            return R.layout.recent_calls_header_row
        }
        return  R.layout.recent_calls_row
    }

    override fun getItemCount(): Int {
        return this.CallersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val recentCall: RecentCallers = this.CallersList[position]

        holder!!.tvCallerName.text = "${recentCall.CallerName}(${recentCall.CallerChildren!!.size})"
        holder.tvCallerNumber.text = recentCall.CallerNumber
        if (recentCall.DiferentDate){
            val dateFormat = SimpleDateFormat("MM/dd/YYYY")
            val netDate = Date(recentCall.OriginalDate)
            val recentCallDay = Date(dateFormat.format(netDate))
            val dateToday = Date(dateFormat.format(Date().time))
             var diff:Long = dateToday.time - recentCallDay.time
            diff = (diff / (1000 * 60 * 60 * 24))
            var headerText: String

            when(diff.toInt()) {
                0 -> {
                    headerText = "Today"
                }
                1 -> {
                    headerText = "Yesterday"
                }
                else -> {
                    headerText = recentCall.CallerDate!!
                }
            }
            holder.header.text = headerText
        }
        var firstLetter = recentCall.CallerName!![0].toString() ?: "A" //check this
        val drawable: TextDrawable = TextDrawable.builder().buildRound(firstLetter, generator.randomColor)
        holder.letter.setImageDrawable(drawable)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCallerName: TextView = itemView.callerName
        val tvCallerNumber: TextView = itemView.callerNumber
        val letter: ImageView = itemView.gmailitem_letter
        val header: TextView = itemView.tvHeaderRow
    }

}
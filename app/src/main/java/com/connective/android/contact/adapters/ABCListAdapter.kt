package com.connective.android.contact.adapters


import android.graphics.Color
import com.connective.android.contact.R
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import kotlinx.android.synthetic.main.abc_layout.view.*

class ABCListAdapter(abcList: ArrayList<String>) : RecyclerView.Adapter<ABCListAdapter.ViewHolder>() {
    val ABCList: ArrayList<String> = abcList
    val generator: ColorGenerator = ColorGenerator.MATERIAL

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ABCListAdapter.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent!!.context)
        return ViewHolder(inflater.inflate(R.layout.abc_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return this.ABCList.size
    }

    override fun onBindViewHolder(holder: ABCListAdapter.ViewHolder?, position: Int) {
        val drawable: TextDrawable = TextDrawable.builder().beginConfig()
                .withBorder(4)
                .textColor(Color.BLACK)
                .endConfig().buildRound(this.ABCList[position], Color.WHITE )
        holder!!.ivLetter.setImageDrawable(drawable)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivLetter: ImageView = itemView.gmailitem_letter
    }
}
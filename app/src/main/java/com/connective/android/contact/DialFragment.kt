package com.connective.android.contact

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.provider.CallLog.*
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.connective.android.contact.adapters.RecentCallsAdapter
import com.connective.android.contact.models.RecentCallers
import kotlinx.android.synthetic.main.fragment_dial.*
import kotlinx.android.synthetic.main.fragment_dial.view.*


/**
 * A simple [Fragment] subclass.
 * [DialFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DialFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var mListener: OnFragmentInteractionListener? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var recentCalls: ArrayList<RecentCallers> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }


    }

    @SuppressLint("MissingPermission")
    fun getRecentContacts(): ArrayList<RecentCallers> {
       val cursor = context.contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC")
       // val cursor = context.contentResolver.query(Calls.CONTENT_URI, null, null, null, null); not working
        var tempRecentList: ArrayList<RecentCallers> = arrayListOf()
        while (cursor.moveToNext()) {
            var name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME))
            var date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE))
            val phoneNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER))
            val duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION))
            if (name == null) {
                name = phoneNumber
            }
            var tempCall: RecentCallers = RecentCallers(name, phoneNumber, date, null)
            tempRecentList.add(tempCall)
        }

        var temp = tempRecentList.groupBy { it.CallerDate }.map { Pair(it.key, it) }
        temp.forEach{
            val (date, list) = it
            var recentCallers = RecentCallers()
            list.value.forEach {

            }
        }
       /* var temp = tempRecentList.map { r -> Pair(r.CallerDate, tempRecentList.groupBy { it.CallerName }.map { Pair(it.key, it) }) }
        temp.forEach{
            val (callerDate, callerGroup) = it
            callerGroup.forEach {
                val (callerName, detailGroup) = it
                detailGroup.value.forEach {

                }
            }
        }*/
        return tempRecentList;
    }


/*    fun getContacts(): ArrayList<RecentCallers> {
        val cursor = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        var tempRecentList: ArrayList<RecentCallers> = arrayListOf()
        while (cursor.moveToNext()) {

            val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            var tempCall: RecentCallers = RecentCallers(name, phoneNumber, id)
            tempRecentList.add(tempCall)
        }
        return tempRecentList;
    }*/

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var dialView = inflater!!.inflate(R.layout.fragment_dial, container, false)
        this.layoutManager = LinearLayoutManager(context)
        dialView.lvRecentCalls.layoutManager = this.layoutManager


        dialView.lvRecentCalls.adapter = RecentCallsAdapter(this.getRecentContacts())
        dialView.lvRecentCalls.setHasFixedSize(true)
        val divider: DividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(context.getDrawable(R.drawable.recent_call_divider))
        dialView.lvRecentCalls.addItemDecoration(divider)
        return dialView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            mListener = context
//        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
//        }

    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DialFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): DialFragment {
            val fragment = DialFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor

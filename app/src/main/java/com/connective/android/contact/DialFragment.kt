package com.connective.android.contact

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.CallLog
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.connective.android.contact.adapters.RecentCallsAdapter
import com.connective.android.contact.models.RecentCallers
import com.connective.android.contact.models.RecentChild
import kotlinx.android.synthetic.main.activity_main.*
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }


    }
    //<editor-fold desc="User Preferences">
    private val PREFS_FILENAME = "com.connective.android.contact"
    private  val Call_Log_PERMISSION = "callLogsPermission"
    var prefs: SharedPreferences? = null
    //</editor-fold>

    //<editor-fold desc="Check permissions">
    private val accessCallLogsCode = 124

    fun checkPermissionCallLogs(view:View){
        if(Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_CALL_LOG), accessCallLogsCode)
                return
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            accessCallLogsCode ->{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    val editor = prefs!!.edit()
                    editor.putBoolean(this.Call_Log_PERMISSION, true)
                    editor.apply()
                    this.constructListView(this.view)
                }else{
                    Toast.makeText(context, "We cannot access to the call logs.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    //</editor-fold>

    @SuppressLint("MissingPermission")
    fun getRecentContacts(): ArrayList<RecentCallers> {
       val cursor = context.contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC")
        var localCallLogs: ArrayList<RecentCallers> = arrayListOf()
        while (cursor.moveToNext()) {
            var name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME))
            var date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE))
            val phoneNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER))
            val duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION))
            if (name == null) {
                name = "Unknown"
            }
            localCallLogs.add(RecentCallers(name, phoneNumber, date!!.toLong(), null, duration))
        }
        return localCallLogs;
    }

    fun getFormattedCallLogs(unordererdCallLogs: ArrayList<RecentCallers>): ArrayList<RecentCallers>{
        var reorderedCallLogs: ArrayList<RecentCallers> = arrayListOf()
        var groupCallLogs = unordererdCallLogs.groupBy { it.CallerDate }.map{ Pair(it.key, it.value.groupBy { it.CallerName }) }
        groupCallLogs.forEach{
            val (date, list) = it
            var dateDiff = true
            list.forEach {
                val (name, list) = it
                var tempRecentChilds: ArrayList<RecentChild> = arrayListOf()
                list.forEach {
                    var child = RecentChild(it.OriginalDate, it.CallDuration)
                    tempRecentChilds.add(child)
                }
                var recentCall = RecentCallers(name, list[0].CallerNumber, list[0].OriginalDate, tempRecentChilds, "")
                recentCall.DiferentDate = dateDiff
                reorderedCallLogs.add(recentCall)
                dateDiff = false
            }
        }
        return reorderedCallLogs
    }

    private fun constructListView(dialView: View?) {

        dialView!!.lvRecentCalls.adapter = RecentCallsAdapter(this.getFormattedCallLogs(this.getRecentContacts()))
        dialView!!.lvRecentCalls.setHasFixedSize(true)
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(context.getDrawable(R.drawable.recent_call_divider))
        dialView.lvRecentCalls.addItemDecoration(divider)
        lvRecentCalls.visibility = View.VISIBLE
        llCheckPermission.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var dialView = inflater!!.inflate(R.layout.fragment_dial, container, false)
        this.layoutManager = LinearLayoutManager(context)
        dialView.lvRecentCalls.layoutManager = this.layoutManager
        dialView.btnCheckPermisson.setOnClickListener {
            this.checkPermissionCallLogs(it)
        }
        this.prefs = context.getSharedPreferences(this.PREFS_FILENAME, 0)
        val callLogsPermission = prefs!!.getBoolean(this.Call_Log_PERMISSION,false)
        if(callLogsPermission){
           this.constructListView(dialView)
        }
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

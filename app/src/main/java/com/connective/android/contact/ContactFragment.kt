package com.connective.android.contact

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.connective.android.contact.adapters.ABCListAdapter
import com.connective.android.contact.models.ContactParent
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.fragment_contact.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ContactFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    private var mListener: OnFragmentInteractionListener? = null
    val ABCList: ArrayList<String> = arrayListOf("A", "B", "C","D", "E", "F","G", "H", "I","J", "K", "L","M", "N", "O","P", "Q", "R","S", "T", "U","V", "W", "X","Y","Z")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1)
            mParam2 = getArguments().getString(ARG_PARAM2)
        }
    }

    fun getContacts(): ArrayList<ContactParent> {
        val cursor = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        var tempRecentList: ArrayList<ContactParent> = arrayListOf()
        while (cursor.moveToNext()) {

            val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            var tempCall: ContactParent = ContactParent(id, name, phoneNumber)
            tempRecentList.add(tempCall)
        }
        return tempRecentList;
    }

    //<editor-fold desc="Check permissions">

    //</editor-fold>

    public override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                     savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view!!.lvABC.layoutManager = this.layoutManager
        view!!.lvABC.adapter = ABCListAdapter(this.ABCList)
        view.lvABC.setHasFixedSize(true)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    public override fun onAttach(context: Context?) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            mListener = context as OnFragmentInteractionListener?
//        } else {
//            throw RuntimeException((context!!.toString() + " must implement OnFragmentInteractionListener"))
//        }
    }

    public override fun onDetach() {
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
         * @return A new instance of fragment ContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): ContactFragment {
            val fragment = ContactFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.setArguments(args)
            return fragment
        }
    }
}// Required empty public constructor

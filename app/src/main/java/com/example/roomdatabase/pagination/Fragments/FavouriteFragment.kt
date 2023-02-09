package com.example.roomdatabase.pagination.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.pagination.FavouriteDatabase.favdatabaseAdapter
import com.example.roomdatabase.pagination.R
import com.example.roomdatabase.pagination.Roomdatabase.RoomDatabase.Companion.db

class FavouriteFragment : Fragment() {


    private var recyclerview: RecyclerView? = null
    private var iv_not_available: ImageView? = null
    private var roomadapter: favdatabaseAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favourite, container, false)

        recyclerview = view.findViewById<RecyclerView>(R.id.rv)
        iv_not_available = view.findViewById<ImageView>(R.id.no_data)
        return view

    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.e("====", "onResume: 12" + isVisibleToUser)
        if (isVisibleToUser) {
            if (db!!.favouriteDao().getAllMovie() != null && db!!.favouriteDao().getAllMovie().size != 0){
                iv_not_available!!.setVisibility(View.GONE)
                recyclerview!!.setVisibility(View.VISIBLE)
                val linearLayoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                roomadapter = favdatabaseAdapter(requireContext(), db!!.favouriteDao().getAllMovie())
                recyclerview!!.layoutManager = linearLayoutManager
                recyclerview!!.adapter = roomadapter
            }else{
                iv_not_available!!.setVisibility(View.VISIBLE)
                recyclerview!!.setVisibility(View.GONE)
            }

        }

    }
}


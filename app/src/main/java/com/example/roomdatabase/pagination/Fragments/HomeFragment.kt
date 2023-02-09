package com.example.roomdatabase.pagination.Fragments


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.roomdatabase.pagination.Api.ApiModel
import com.example.roomdatabase.pagination.Api.ApiService
import com.example.roomdatabase.pagination.Api.ClientApi
import com.example.roomdatabase.pagination.Pagination.PaginationAdapter
import com.example.roomdatabase.pagination.Pagination.PaginationScrollListener
import com.example.roomdatabase.pagination.Pagination.jun_u_dialog
import com.example.roomdatabase.pagination.R
import com.example.roomdatabase.pagination.Roomdatabase.RoomDatabase
import com.example.roomdatabase.pagination.Roomdatabase.RoomDatabase.Companion.db
import com.example.roomdatabase.pagination.RoomdatabaseAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private var currentPage = 0
    var isLoading = false
    var isLastPage = false
    var roomAdapter: RoomdatabaseAdapter? = null
    var paginationAdapter: PaginationAdapter? = null
    private lateinit var apiService: ApiService
    private lateinit var reyclerView: RecyclerView


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        reyclerView = view.findViewById<RecyclerView>(R.id.reyclerView)
        apiService = ClientApi.client!!.create(ApiService::class.java)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        paginationAdapter = PaginationAdapter(requireContext())
        reyclerView.layoutManager = linearLayoutManager
        reyclerView.adapter = paginationAdapter

        db = Room.databaseBuilder(
            requireActivity().applicationContext,
            RoomDatabase::class.java,
            "movieTable"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
        if (!isNetworkConnected()) {
            db!!.movieDao().getAllMovie()
            Log.e("====", "notconnect: " + db!!.movieDao().getAllMovie())

            roomAdapter = RoomdatabaseAdapter(requireContext(), db!!.movieDao().getAllMovie())
            reyclerView.layoutManager = linearLayoutManager
            reyclerView.adapter = roomAdapter
        } else {
            reyclerView.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
                override fun loadMoreItems() {
                    this@HomeFragment.isLoading = true
                    currentPage += 1
                    loadNextPage()
                }

                override val isLastPage: Boolean = false
                    get() = field
                override val isLoading: Boolean = false
                    get() = field

            })
            loadFirstPage()
        }

        return view
    }

    fun loadNextPage() {
        jun_u_dialog.pdialog(requireActivity())
        apiService.getData(currentPage).enqueue(object : Callback<List<ApiModel>> {
            override fun onResponse(
                call: Call<List<ApiModel>>,
                response: Response<List<ApiModel>>
            ) {
                jun_u_dialog.pdialog_dismiss()
                paginationAdapter!!.removeLoadingFooter()
                isLoading = false
                val results = response.body()
                if (results != null) {
                    paginationAdapter!!.addAll(results)
                    db?.movieDao()?.addMovie(response.body())

                    var alldata = db?.movieDao()?.getAllMovie()

                }
                if (currentPage != 1) {
                    paginationAdapter!!.addLoadingFooter()
                } else {
                    isLastPage = true
                }


            }

            override fun onFailure(call: Call<List<ApiModel>>, t: Throwable) {
                jun_u_dialog.pdialog_dismiss()
                Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()

            }

        })


    }

    fun loadFirstPage() {
        jun_u_dialog.pdialog(requireActivity())
        apiService.getData(currentPage).enqueue(object : Callback<List<ApiModel>> {
            override fun onResponse(
                call: Call<List<ApiModel>>,
                response: Response<List<ApiModel>>
            ) {
                jun_u_dialog.pdialog_dismiss()
                val results = response.body()
                paginationAdapter!!.addAll(results!!)
                if (currentPage == 0) {
                    db?.movieDao()?.clearAllData()
                }
                if (currentPage <= 15) {
                    paginationAdapter!!.addLoadingFooter()

                } else isLastPage = true
            }

            override fun onFailure(call: Call<List<ApiModel>>, t: Throwable) {
                jun_u_dialog.pdialog_dismiss()
                Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun isNetworkConnected(): Boolean {
        val cm =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

}





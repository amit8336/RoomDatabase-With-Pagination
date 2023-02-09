package com.example.roomdatabase.pagination.Activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.viewpager.widget.ViewPager
import com.example.roomdatabase.pagination.Fragments.TabAdapter
import com.example.roomdatabase.pagination.R
import com.example.roomdatabase.pagination.Roomdatabase.RoomDatabase
import com.example.roomdatabase.pagination.Roomdatabase.RoomDatabase.Companion.db
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var tabslayout: TabLayout? = null
    var viewpager: ViewPager? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabslayout = findViewById<TabLayout>(R.id.tabslayout)
        viewpager = findViewById<ViewPager>(R.id.viewpager)

        db = Room.databaseBuilder(
           applicationContext,
            RoomDatabase::class.java,
            "movieTable"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

        if (db!!.movieDao().getAllMovie().size == 0){
            if (!isNetworkConnected()) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("No internet Connection")
                builder.setMessage("Please turn on internet connection to continue")
                builder.setNegativeButton("close",
                    DialogInterface.OnClickListener { dialog, which ->  })
                builder.setNegativeButton(
                    "close"
                ) { dialog, which -> finish() }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()

            }
        }


        tabslayout!!.addTab(tabslayout!!.newTab().setText("Home"))
        tabslayout!!.addTab(tabslayout!!.newTab().setText("Favourite"))
        tabslayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TabAdapter(this, supportFragmentManager, tabslayout!!.tabCount)
        viewpager!!.adapter = adapter

        viewpager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabslayout))
        tabslayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewpager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}
package com.example.roomdatabase.pagination

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.pagination.Activities.DetailActivity
import com.example.roomdatabase.pagination.Api.ApiModel
import com.example.roomdatabase.pagination.FavouriteDatabase.favouritemodel
import com.example.roomdatabase.pagination.Roomdatabase.RoomDatabase
import com.squareup.picasso.Picasso

class RoomdatabaseAdapter(private val context: Context, private var apiModelList: List<ApiModel>) :
    RecyclerView.Adapter<RoomdatabaseAdapter.ViewHolder>() {


    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val img = itemview.findViewById<ImageView>(R.id.img_1)
        val favourite = itemview.findViewById<ImageView>(R.id.favourite)
        val albumid = itemview.findViewById<TextView>(R.id.txt_albumid)
        val tittle = itemview.findViewById<TextView>(R.id.txt_tittle)
        val rrl = itemview.findViewById<RelativeLayout>(R.id.rrl)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.list_rv_item, parent, false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movie = apiModelList[position]

        holder.tittle.text = movie.title
        holder.albumid.text = movie.albumid.toString()

        Picasso.get().load(movie.url)
            .placeholder(R.drawable.iv_not_load)
            .into(holder.img)

        holder.rrl.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, DetailActivity::class.java)

            intent.putExtra("id", movie.albumid)
            intent.putExtra("tittle", movie.title)
            intent.putExtra("uri", movie.url)

            context.startActivity(intent)
        })

        if (RoomDatabase.db!!.favouriteDao().getAllFavourite(movie.id) == 1) {
            holder.favourite.setImageResource(R.drawable.favourite)
        } else {
            holder.favourite.setImageResource(R.drawable.unfavourite)
        }

        holder.favourite.setOnClickListener(View.OnClickListener {
            val favoriteList = favouritemodel()
            val fav: Int = movie.id
            val alubm: Int = movie.albumid
            val tittle: String = movie.title
            val uri: String = movie.url

            favoriteList.favourite = fav
            favoriteList.albumid = alubm
            favoriteList.title = tittle
            favoriteList.url = uri

            if (RoomDatabase.db!!.favouriteDao().getAllFavourite(fav) != 1) {
                holder.favourite.setImageResource(R.drawable.favourite)
                RoomDatabase.db!!.favouriteDao().addData(favoriteList)
            } else {
                holder.favourite.setImageResource(R.drawable.unfavourite);
                RoomDatabase.db!!.favouriteDao().deleteMovie(fav);
            }
        })

    }

    override fun getItemCount(): Int {
        return apiModelList.size
    }


}
package com.example.roomdatabase.pagination.FavouriteDatabase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.pagination.R
import com.squareup.picasso.Picasso

class favdatabaseAdapter(private val context: Context, private var fav: List<favouritemodel>) :
    RecyclerView.Adapter<favdatabaseAdapter.ViewHolder>() {


    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val img = itemview.findViewById<ImageView>(R.id.fav_img)
        val albumid = itemview.findViewById<TextView>(R.id.fav_txt_id)
        val tittle = itemview.findViewById<TextView>(R.id.fav_txt_tittle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.list_fav_item, parent, false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movie = fav[position]
        holder.tittle.text = movie.title
        holder.albumid.text = movie.albumid.toString()

        
//        Glide.with(context).load(movie.url)
//            .apply(RequestOptions.centerCropTransform())
//            .into(holder.img)

        Picasso.get().load(movie.url)
            .placeholder(R.drawable.iv_not_load)
            .into(holder.img)
//        Picasso.get().load(movie.url).into(holder.img)

    }

    override fun getItemCount(): Int {
        return fav.size
    }


}
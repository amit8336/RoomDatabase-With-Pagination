package com.example.roomdatabase.pagination.Pagination

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.pagination.Activities.DetailActivity
import com.example.roomdatabase.pagination.Api.ApiModel
import com.example.roomdatabase.pagination.FavouriteDatabase.favouritemodel
import com.example.roomdatabase.pagination.R
import com.example.roomdatabase.pagination.Roomdatabase.RoomDatabase.Companion.db
import com.squareup.picasso.Picasso
import java.util.*

class PaginationAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var apiModelList: MutableList<ApiModel>?
    private var searchlist: MutableList<ApiModel>? = null


    private var isLoadingAdded = false

    init {
        apiModelList = LinkedList()
    }

    fun setMovieList(apiModelList: MutableList<ApiModel>?) {
        this.apiModelList = apiModelList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM -> {
                val viewItem: View = inflater.inflate(R.layout.list_rv_item, parent, false)
                viewHolder = MovieViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading: View = inflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
        }
        return viewHolder!!
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = apiModelList!![position]
        when (getItemViewType(position)) {
            ITEM -> {
                val movieViewHolder = holder as MovieViewHolder
                movieViewHolder.albumid.text =
                    movie.albumid.toString()
                movieViewHolder.txt_Title.text =
                    movie.title
                Picasso.get()
                    .load(movie.url)
                    .placeholder(R.drawable.iv_not_load)
                    .into(movieViewHolder.apiImage)


                holder.rrl.setOnClickListener(View.OnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)

                    intent.putExtra("id", movie.albumid)
                    intent.putExtra("tittle", movie.title)
                    intent.putExtra("uri", movie.url)

                    context.startActivity(intent)
                })


                if (db!!.favouriteDao().getAllFavourite(movie.id) == 1) {
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

                    if (db!!.favouriteDao().getAllFavourite(fav) != 1) {
                        holder.favourite.setImageResource(R.drawable.favourite)
                        db!!.favouriteDao().addData(favoriteList)
                    } else {
                        holder.favourite.setImageResource(R.drawable.unfavourite);
                        db!!.favouriteDao().deleteMovie(fav);
                    }
                })


//                Glide.with(context).load(movie.url)
//                    .apply(RequestOptions.centerCropTransform()).into(movieViewHolder.movieImage)

            }
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.progressBar.visibility = View.VISIBLE
            }
        }
    }


    override fun getItemCount(): Int {
        return if (apiModelList == null) 0 else apiModelList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == apiModelList!!.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(ApiModel())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = apiModelList!!.size - 1
        val result = getItem(position)
        if (result != null) {
            apiModelList!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun add(apiModel: ApiModel) {
        apiModelList!!.add(apiModel)
        notifyItemInserted(apiModelList!!.size - 1)
    }

    fun addAll(moveResults: List<ApiModel>?) {
        for (result in moveResults!!) {
            add(result)
        }
    }

    fun getItem(position: Int): ApiModel {
        return apiModelList!![position]
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_Title: TextView
        val apiImage: ImageView
        val favourite: ImageView
        val albumid: TextView
        val rrl: RelativeLayout

        init {

            txt_Title = itemView.findViewById<View>(R.id.txt_tittle) as TextView
            apiImage = itemView.findViewById<View>(R.id.img_1) as ImageView
            favourite = itemView.findViewById<View>(R.id.favourite) as ImageView
            albumid = itemView.findViewById<View>(R.id.txt_albumid) as TextView
            rrl = itemView.findViewById<View>(R.id.rrl) as RelativeLayout
        }

    }


    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById<View>(R.id.loadmore_progress) as ProgressBar
        }
    }


    companion object {
        private const val LOADING = 0
        private const val ITEM = 1
    }


}
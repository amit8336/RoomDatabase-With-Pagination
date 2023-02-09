package com.example.roomdatabase.pagination.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.roomdatabase.pagination.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.detail_activity.*


class DetailActivity : AppCompatActivity() {
    private var img: CircleImageView? = null
    private var back: ImageView? = null
    private var text_album_id: TextView? = null
    private var text_tittle: TextView? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        img = findViewById(R.id.img)
        text_album_id = findViewById(R.id.text_album_id)
        text_tittle = findViewById(R.id.text_tittle)
        back = findViewById(R.id.back)


//        back!!.setOnClickListener(back1)


        back!!.setOnClickListener(View.OnClickListener {
           onBackPressed()
        })

        text_album_id!!.text = intent.extras?.getInt("id").toString()
        text_tittle!!.text = intent.extras?.getString("tittle")

        val url = intent.extras?.getString("uri")

        Picasso.get().load(url)
            .placeholder(R.drawable.iv_not_load)
            .into(img)

//        Glide.with(applicationContext).load(url)
//            .apply(RequestOptions.centerCropTransform()).into(img!!)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }



}
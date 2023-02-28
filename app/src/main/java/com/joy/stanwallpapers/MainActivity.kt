package com.joy.stanwallpapers

import android.app.ProgressDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContentInfo
import android.view.OnReceiveContentListener
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joy.stanwallpapers.Models.CuratedApiResponse
import com.joy.stanwallpapers.Models.Photo
import com.joy.stanwallpapers.adapters.CuratedAdapter
import com.joy.stanwallpapers.listeners.CuratedResponceListeners
import com.joy.stanwallpapers.listeners.OnRecyclerClickListener

lateinit var recycle_view:RecyclerView
lateinit var adapter : CuratedAdapter
lateinit var dialog:ProgressDialog
lateinit var manager: RequestManager
@RequiresApi(Build.VERSION_CODES.S)
class MainActivity : AppCompatActivity() ,  OnRecyclerClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog = ProgressDialog(this)
        dialog.setTitle("Loading...")

        manager = RequestManager(this)
        manager.getCuratedWallpapers(listener , "1")
    }


    private val listener = object : CuratedResponceListeners{
        override fun onFetch(response: CuratedApiResponse?, message: String) {
            if (response != null) {
                if(response.photos?.isEmpty() == true){
                    Toast.makeText(applicationContext, "No image found!" , Toast.LENGTH_SHORT).show()
                    return

                }

                response.photos?.let { showData(it) }
            }

        }

        override fun onError(message: String?) {
            Toast.makeText(applicationContext, message , Toast.LENGTH_SHORT).show()
        }


    }

    private fun showData(photos: ArrayList<Photo>) {
        recycle_view = findViewById(R.id.recycle_view)
        recycle_view.setHasFixedSize(true)
        recycle_view.layoutManager = GridLayoutManager(this , 2)
        adapter = CuratedAdapter(this@MainActivity , photos , this )
        recycle_view.adapter = adapter

    }

    override fun onClick(photo: Photo) {
       Toast.makeText(this@MainActivity , photo.photographer , Toast.LENGTH_SHORT).show()
    }


}
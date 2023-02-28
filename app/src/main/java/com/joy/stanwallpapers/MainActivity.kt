package com.joy.stanwallpapers

import android.app.ProgressDialog
import android.content.Intent
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.joy.stanwallpapers.Models.CuratedApiResponse
import com.joy.stanwallpapers.Models.Photo
import com.joy.stanwallpapers.adapters.CuratedAdapter
import com.joy.stanwallpapers.listeners.CuratedResponceListeners
import com.joy.stanwallpapers.listeners.OnRecyclerClickListener

lateinit var recycle_view:RecyclerView
lateinit var adapter : CuratedAdapter
lateinit var dialog:ProgressDialog
lateinit var manager: RequestManager
lateinit var next_btn:FloatingActionButton
lateinit var prev_btn:FloatingActionButton
var page = 0
@RequiresApi(Build.VERSION_CODES.S)
class MainActivity : AppCompatActivity() ,  OnRecyclerClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog = ProgressDialog(this)
        dialog.setTitle("Loading...")

        manager = RequestManager(this)
        val random1 = (0..500).shuffled().last()
        manager.getCuratedWallpapers(listener , random1.toString())
        next_btn = findViewById(R.id.next_btn)
        prev_btn = findViewById(R.id.prev_btn)

        next_btn.setOnClickListener {
            var next_page:String = (page+1).toString()
            manager.getCuratedWallpapers(listener , next_page)
            dialog.show()
        }

        prev_btn.setOnClickListener {
            if(page>1){
                var prev_page:String = (page-1).toString()
                manager.getCuratedWallpapers(listener , prev_page)
                dialog.show()
            }

        }
    }


    private val listener = object : CuratedResponceListeners{
        override fun onFetch(response: CuratedApiResponse?, message: String) {
            dialog.dismiss()
            if (response != null) {
                if(response.photos?.isEmpty() == true){
                    Toast.makeText(applicationContext, "No image found!" , Toast.LENGTH_SHORT).show()
                    return

                }
                page = response.page

                response.photos?.let { showData(it) }
            }

        }

        override fun onError(message: String?) {
            dialog.dismiss()
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
        val intent = Intent(this, WallpaperActivity::class.java)
        startActivity(intent.putExtra("photo" , photo))

    }


}
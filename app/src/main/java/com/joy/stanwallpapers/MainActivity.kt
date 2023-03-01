package com.joy.stanwallpapers

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContentInfo
import android.view.Menu
import android.view.MenuItem
import android.view.OnReceiveContentListener
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.joy.stanwallpapers.Models.CuratedApiResponse
import com.joy.stanwallpapers.Models.Photo
import com.joy.stanwallpapers.Models.SearchApiResponse
import com.joy.stanwallpapers.adapters.CuratedAdapter
import com.joy.stanwallpapers.listeners.CuratedResponceListeners
import com.joy.stanwallpapers.listeners.OnRecyclerClickListener
import com.joy.stanwallpapers.listeners.SearchResponceListener
import retrofit2.http.Query
import java.util.Objects

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
        val random1 = (0..100).shuffled().last()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu , menu)
       var  menuItem:MenuItem = menu.findItem(R.id.action_search)
       var searchView:SearchView = menuItem.actionView as SearchView
        searchView.queryHint = "Type here to search..."

        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(query: String): Boolean {
                return false;
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                val random2 = (0..10).shuffled().last().toString()
                manager.getSearchWallpapers(searchResponceListener , random2 , query )
                dialog.show()
                return true
            }

        })


        return super.onCreateOptionsMenu(menu)
    }


     val searchResponceListener:SearchResponceListener = object : SearchResponceListener{
        override fun onFetch(responce: SearchApiResponse?, message: String) {
            dialog.dismiss()
            if (responce != null) {

                if(responce.photos?.isEmpty() == true){
                    Toast.makeText(this@MainActivity , "No Image Found!!" , Toast.LENGTH_SHORT).show()
                    return;
                }

                responce.photos?.let { showData(it) }
            }
        }

        override fun onError(message: String?) {
            dialog.dismiss()
            Toast.makeText(this@MainActivity , message , Toast.LENGTH_SHORT).show()

        }

    }


}
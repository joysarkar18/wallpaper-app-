package com.joy.stanwallpapers

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.ProgressDialog
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.joy.stanwallpapers.Models.Photo
import com.squareup.picasso.Picasso

lateinit var wallpaper_image:ImageView
lateinit var photo : Photo

lateinit var fab_download :FloatingActionButton
lateinit var fab_wallpaper:FloatingActionButton

class WallpaperActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)


        wallpaper_image = findViewById(R.id.wallpaper_image)
        fab_download = findViewById(R.id.fab_download)
        fab_wallpaper = findViewById(R.id.fab_wallpaper)
        photo = intent.getSerializableExtra("photo") as Photo
        Picasso.get().load(photo.src?.original).placeholder(R.drawable.placeholder_big).into(
            wallpaper_image)

             fab_download.setOnClickListener {
            var downloadManager : DownloadManager? = null
            downloadManager = (getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?)!!

            val uri:Uri =Uri.parse(photo.src?.large  )
            val request:DownloadManager.Request = DownloadManager.Request(uri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or  DownloadManager.Request.NETWORK_MOBILE).setAllowedOverRoaming(true).setTitle("wallpaper_"+photo.photographer).setMimeType("image/jpeg").setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED).setDestinationInExternalFilesDir(this , Environment.DIRECTORY_DOWNLOADS.toString(), "wallpaper_"+photo.photographer+".jpg")
            downloadManager.enqueue(request);

            Toast.makeText(this , "Wallpaper Downloaded" , Toast.LENGTH_SHORT).show()

            }

        fab_wallpaper.setOnClickListener {
            val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(this)
            val bitMap: Bitmap = (wallpaper_image.drawable as BitmapDrawable).bitmap
            try {
                wallpaperManager.setBitmap(bitMap)
                Toast.makeText(this , "Wallpaper Applied" , Toast.LENGTH_SHORT).show()
            } catch (e:java.lang.Exception){
                e.printStackTrace()
                Toast.makeText(this , "An Error Occard" , Toast.LENGTH_SHORT).show()
            }

        }
        }
    }






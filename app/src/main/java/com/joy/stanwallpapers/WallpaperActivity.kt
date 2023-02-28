package com.joy.stanwallpapers

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
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
        Picasso.get().load(photo.src?.original).placeholder(R.drawable.placeholder).into(
            wallpaper_image)
    }
}
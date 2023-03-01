package com.joy.stanwallpapers.listeners


import com.joy.stanwallpapers.Models.SearchApiResponse

interface SearchResponceListener {
    fun onFetch(responce: SearchApiResponse?, message :String)
    fun onError(message: String?)
}
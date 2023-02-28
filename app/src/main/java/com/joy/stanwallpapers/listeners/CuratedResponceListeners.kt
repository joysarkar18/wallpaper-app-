package com.joy.stanwallpapers.listeners

import com.joy.stanwallpapers.Models.CuratedApiResponse

interface CuratedResponceListeners {
    fun onFetch(responce:CuratedApiResponse? , message :String)
    fun onError(message: String?)

}
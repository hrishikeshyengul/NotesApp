package com.example.notesapp

import android.content.Context
import com.scottyab.rootbeer.RootBeer

object RootDetection {
    fun isDeviceRooted(context: Context): Boolean {
        val rootBeer = RootBeer(context)
        return rootBeer.isRooted
    }
}
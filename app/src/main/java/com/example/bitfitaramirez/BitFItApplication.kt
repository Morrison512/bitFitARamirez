package com.example.bitfitaramirez

import android.app.Application

class BitFItApplication : Application() {
    val db by lazy { BitFitDataBase.getInstance(this) }
}
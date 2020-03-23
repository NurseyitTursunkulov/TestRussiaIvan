package com.example.testrussiaivan

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val bitmap: MutableLiveData<Event<Bitmap>> = MutableLiveData()
    val uri: MutableLiveData<Event<Uri>> = MutableLiveData()
}
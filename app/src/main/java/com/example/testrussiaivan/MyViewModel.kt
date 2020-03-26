package com.example.testrussiaivan

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val bitmap: MutableLiveData<Event<Bitmap>> = MutableLiveData()
    val galleryImageUri: MutableLiveData<Event<Uri>> = MutableLiveData()
    val date: MutableLiveData<Event<String>> = MutableLiveData()
    val sexOfChild: MutableLiveData<Event<Sex>> = MutableLiveData(Event(Sex.GIRL))
    val name: MutableLiveData<Event<String>> = MutableLiveData()

    val requiredFieldsWritten = MediatorLiveData<Boolean>()

    init {
        requiredFieldsWritten.notifyWhenFieldsFilled(
            bitmap,
            galleryImageUri,
            date,
            name
        )
    }


}
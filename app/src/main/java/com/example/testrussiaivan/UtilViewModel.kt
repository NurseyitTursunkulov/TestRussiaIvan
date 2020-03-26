package com.example.testrussiaivan

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

fun MediatorLiveData<Boolean>.notifyWhenFieldsFilled(
    bitmap: MutableLiveData<Event<Bitmap>>,
    galleryImageUri: MutableLiveData<Event<Uri>>,
    date: MutableLiveData<Event<String>>,
    name: MutableLiveData<Event<String>>
) {
    this.addSource(name) {
        if (galleryImageUri.value?.peekContent() != null || bitmap.value?.peekContent() != null) {
            date.value?.peekContent()?.isNotEmpty()?.let {
                if (it)
                    this.value = true
            }

        }
        name.value?.peekContent()?.isEmpty()?.let {
            if (it) this.value = false
        }

    }

    this.addSource(date) {
        if (galleryImageUri.value?.peekContent() != null || bitmap.value?.peekContent() != null) {
            name.value?.peekContent()?.isNotEmpty()?.let {
                if (it)
                    this.value = true
            }

        }

    }
    this.addSource(bitmap) {
        date.value?.peekContent()?.isNotEmpty()?.let {
            if (it) {
                name.value?.peekContent()?.isNotEmpty()?.let {
                    if (it)
                        this.value = true
                }

            }
        }

    }

    this.addSource(galleryImageUri) { uri ->
        date.value?.peekContent()?.isNotEmpty()?.let {
            if (it) {
                name.value?.peekContent()?.isNotEmpty()?.let {
                    if (it)
                        this.value = true
                }

            }
        }

    }
}
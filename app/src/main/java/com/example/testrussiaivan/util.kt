package com.example.testrussiaivan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import com.example.permissionlib.MY_PERMISSIONS_REQUEST_ACCESS_CAMERA
import com.example.permissionlib.checkPermission
import com.example.uploadPhoto.ImageResourceChooserDialogFragment

const val REQUEST_IMAGE_CAPTURE = 1
const val MY_PERMISSIONS_REQUEST_ACCESS_GALLERY = 2
const val PICK_IMAGE = 100

fun Fragment.dispatchTakePictureIntent() {
    Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        .also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
}

fun Fragment.checkCameraPermission(onGranted: () -> Unit) {
    checkPermission(
        fragment = this,
        onGranted = {
            onGranted()
        },
        permission = Manifest.permission.CAMERA,
        requestCode = MY_PERMISSIONS_REQUEST_ACCESS_CAMERA
    )
}

fun Fragment.checkPickImagePermission(onGranted: () -> Unit) {
    checkPermission(
        fragment = this,
        onGranted = {
            onGranted()
        },
        permission = Manifest.permission.READ_EXTERNAL_STORAGE,
        requestCode = MY_PERMISSIONS_REQUEST_ACCESS_GALLERY
    )
}

 fun isPermissionGranted(grantResults: IntArray) : Boolean =
    (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)

 fun Fragment.dispatchGetPictureFromGallery() {
    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
    startActivityForResult(gallery, PICK_IMAGE)
}

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

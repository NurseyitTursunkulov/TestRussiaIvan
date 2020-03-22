package com.example.testrussiaivan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.permissionlib.MY_PERMISSIONS_REQUEST_ACCESS_CAMERA
import com.example.permissionlib.checkPermission

const val REQUEST_IMAGE_CAPTURE = 1
const val MY_PERMISSIONS_REQUEST_ACCESS_GALLERY = 2
const val PICK_IMAGE = 100

fun FirstFragment.dispatchTakePictureIntent() {
    Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        .also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
}

fun FirstFragment.checkCameraPermission(onGranted: () -> Unit) {
    checkPermission(
        fragment = this,
        onGranted = {
            onGranted()
        },
        permission = Manifest.permission.CAMERA,
        requestCode = MY_PERMISSIONS_REQUEST_ACCESS_CAMERA
    )
}

fun FirstFragment.checkPickImagePermission(onGranted: () -> Unit) {
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

 fun FirstFragment.dispatchGetPictureFromGallery() {
    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
    startActivityForResult(gallery, PICK_IMAGE)
}
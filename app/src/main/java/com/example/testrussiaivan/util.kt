package com.example.testrussiaivan

import android.Manifest
import com.example.permissionlib.MY_PERMISSIONS_REQUEST_ACCESS_CAMERA
import com.example.permissionlib.checkPermission

const val REQUEST_IMAGE_CAPTURE = 1

fun FirstFragment.dispatchTakePictureIntent() {
    android.content.Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
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
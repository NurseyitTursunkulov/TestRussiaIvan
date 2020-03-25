package com.example.uploadPhoto

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.permissionlib.MY_PERMISSIONS_REQUEST_ACCESS_CAMERA
import com.example.testrussiaivan.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ImageResourceChooserDialogFragment() : RoundedBottomSheetDialogFragment() {

    val viewModel: MyViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_photo_tv.setOnClickListener {
            checkCameraPermission { dispatchTakePictureIntent() }
        }
        add_image_tv.setOnClickListener {
            checkPickImagePermission {
                dispatchGetPictureFromGallery()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (isPermissionGranted(grantResults)) {
            when (requestCode) {
                MY_PERMISSIONS_REQUEST_ACCESS_CAMERA -> {
                    dispatchTakePictureIntent()
                }
                MY_PERMISSIONS_REQUEST_ACCESS_GALLERY -> {
                    dispatchGetPictureFromGallery()
                }
            }
        }
        dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            viewModel.bitmap.postValue(Event(imageBitmap))
        }
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            data?.data?.let {
                viewModel.galleryImageUri.postValue(Event(it))
            }
        }
        dismiss()
    }

    companion object {
        const val TAG = "ActionBottomDialog"
        fun newInstance(): ImageResourceChooserDialogFragment {
            return ImageResourceChooserDialogFragment()
        }
    }
}

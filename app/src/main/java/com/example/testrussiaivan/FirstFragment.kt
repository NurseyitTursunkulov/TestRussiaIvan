package com.example.testrussiaivan

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.permissionlib.MY_PERMISSIONS_REQUEST_ACCESS_CAMERA
import kotlinx.android.synthetic.main.fragment_first.*


class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.save_btn).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        add_avatar_imageview.setOnClickListener {
//                        checkCameraPermission { dispatchTakePictureIntent() }
            checkPickImagePermission{
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            image_view_avatar.setImageBitmap(imageBitmap)
        }
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            val imageUri = data?.data;
            image_view_avatar.setImageURI(imageUri);
        }
    }
}
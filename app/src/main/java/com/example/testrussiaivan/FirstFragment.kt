package com.example.testrussiaivan

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.permissionlib.MY_PERMISSIONS_REQUEST_ACCESS_CAMERA
import com.example.uploadPhoto.ImageResourceChooserDialogFragment
import kotlinx.android.synthetic.main.fragment_first.*


class FirstFragment : Fragment(R.layout.fragment_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.save_btn).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        add_avatar_imageview.setOnClickListener {
//                        checkCameraPermission { dispatchTakePictureIntent() }
//            checkPickImagePermission{
//                dispatchGetPictureFromGallery()
//            }
            showBottomSheet()
        }
    }

    fun showBottomSheet() {
        val addPhotoBottomDialogFragment =
            ImageResourceChooserDialogFragment.newInstance()
        addPhotoBottomDialogFragment.show(
            parentFragmentManager,
            ImageResourceChooserDialogFragment.TAG
        )
    }

}
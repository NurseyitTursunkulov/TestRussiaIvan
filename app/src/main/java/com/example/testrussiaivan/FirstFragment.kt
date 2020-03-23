package com.example.testrussiaivan

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.uploadPhoto.ImageResourceChooserDialogFragment
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class FirstFragment : Fragment(R.layout.fragment_first) {

    val myviewModel: MyViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.save_btn).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        add_avatar_imageview.setOnClickListener {
            showBottomSheetFragment()
        }
        myviewModel.bitmap.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { bitmap ->
                image_view_avatar.load(bitmap){
                    transformations(CircleCropTransformation())
                }
            }
        })

        myviewModel.uri.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { bitmap ->
                image_view_avatar.load(bitmap){
                    transformations(CircleCropTransformation())
                }
            }
        })
    }

    fun showBottomSheetFragment() {
        val addPhotoBottomDialogFragment =
            ImageResourceChooserDialogFragment.newInstance()
        addPhotoBottomDialogFragment.show(
            parentFragmentManager,
            ImageResourceChooserDialogFragment.TAG
        )
    }

}
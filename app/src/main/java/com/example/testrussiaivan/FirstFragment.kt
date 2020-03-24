package com.example.testrussiaivan

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
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

    private val viewModel: MyViewModel by sharedViewModel()

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.save_btn).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        add_avatar_imageview.setOnClickListener {
            showBottomSheetFragment()
        }
        viewModel.bitmap.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { bitmap ->
                image_view_avatar.load(bitmap) {
                    transformations(CircleCropTransformation())
                }
            }
        })

        viewModel.uri.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { bitmap ->
                image_view_avatar.load(bitmap) {
                    transformations(CircleCropTransformation())
                }
            }
        })

        viewModel.date.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { date ->
                filled_exposed_dropdown.setText("${date} ")
            }
        })

        filled_exposed_dropdown.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                chooseDate { date ->
                    viewModel.date.postValue(Event(date))
                }
            }
            return@setOnTouchListener false
        }
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
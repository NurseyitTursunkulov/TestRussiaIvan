package com.example.testrussiaivan

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.testrussiaivan.databinding.FragmentFirstBinding
import com.example.uploadPhoto.ImageResourceChooserDialogFragment
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class FirstFragment : Fragment() {

    lateinit var binding: FragmentFirstBinding
    private val viewModel: MyViewModel by sharedViewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

    @SuppressLint( "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners(view)

        observeViewModelForChanges()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setClickListeners(view: View) {
        view.findViewById<Button>(R.id.save_btn).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        add_avatar_imageview.setOnClickListener {
            showBottomSheetFragment()
        }
        child_name_et.doOnTextChanged { text, start, count, after ->
            viewModel.name.postValue(Event(text.toString()))
        }
        select_birthday_et.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                chooseDate { date ->
                    viewModel.date.postValue(Event(date))
                }
            }
            return@setOnTouchListener false
        }

        boy_btn.setOnClickListener {
            viewModel.sexOfChild.postValue(Event(Sex.BOY))
        }

        //this button only for view of boy_btn substitution
        boy_btn_not_selected.setOnClickListener {
            viewModel.sexOfChild.postValue(Event(Sex.BOY))
        }
        girl_btn.setOnClickListener {
            viewModel.sexOfChild.postValue(Event(Sex.GIRL))
        }

        //this button only for view of girl_btn substitution
        girl_btn_selected.setOnClickListener {
            viewModel.sexOfChild.postValue(Event(Sex.GIRL))
        }
    }

    private fun observeViewModelForChanges() {
        viewModel.bitmap.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { bitmap ->
                image_view_avatar.load(bitmap) {
                    transformations(CircleCropTransformation())
                }
            }
        })

        viewModel.galleryImageUri.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { bitmap ->
                image_view_avatar.load(bitmap) {
                    transformations(CircleCropTransformation())
                }
            }
        })

        viewModel.date.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { date ->
                select_birthday_et.setText("${date} ")
            }
        })
        viewModel.requiredFieldsWritten.observe(viewLifecycleOwner, Observer {
            if (it) {
                save_btn.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
            } else {
                save_btn.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorPrimary
                    )
                )
            }
        })

        viewModel.sexOfChild.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                when (it) {
                    Sex.GIRL -> {
                        hightLightOnlyGirlButton()
                    }
                    Sex.BOY -> {
                        hightLightOnlyBoyButton()
                    }
                }
            }
        }
        )
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
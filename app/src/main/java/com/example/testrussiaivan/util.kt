package com.example.testrussiaivan

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import com.example.permissionlib.MY_PERMISSIONS_REQUEST_ACCESS_CAMERA
import com.example.permissionlib.checkPermission
import com.example.uploadPhoto.ImageResourceChooserDialogFragment
import java.text.DateFormat
import java.util.*

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

fun MainActivity.closeKeyboard() {
    currentFocus?.let {
        val imm: InputMethodManager = getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as (InputMethodManager)
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Fragment.chooseDate(onDateChoosen: (date:String) -> Unit) {
    val calendar = Calendar.getInstance();
    val datapicker = DatePickerDialog(
        requireContext(),
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal[Calendar.YEAR] = year
            cal[Calendar.MONTH] = month
            cal[Calendar.DAY_OF_MONTH] = dayOfMonth
            val dateRepresentation = cal.time

            val dateFormat: DateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT)
            val dateAsString: String = dateFormat.format(dateRepresentation)

            onDateChoosen(dateAsString)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datapicker.setTitle("");
    datapicker.show()
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

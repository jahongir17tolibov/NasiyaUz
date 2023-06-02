package com.example.nasiyauz.utils

import android.os.Message
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.nasiyauz.R
import com.example.nasiyauz.utils.BaseUtils.showSnackToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

object BaseUtils {

    fun getCurrentDataTime(): String {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())
        return dateFormat.format(currentDate)
    }

    fun Fragment.showSnackToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
        Snackbar.make(requireView(), message!!, duration).show()
    }

    fun alphaSimpleAnimation(
        dur: Long? = null,
        setInterpolator: Interpolator? = null
    ): Animation = AlphaAnimation(0.0f, 1.0f).apply {
        duration = dur ?: 500
        if (setInterpolator != null) {
            interpolator = setInterpolator
        }
    }


}
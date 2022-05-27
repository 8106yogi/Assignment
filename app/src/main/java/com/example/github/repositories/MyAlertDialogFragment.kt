package com.example.github.repositories

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class MyAlertDialogFragment : DialogFragment() {
    private var retryClicked: OnRetryClicked? = null

    fun setRetryClick(retryClicked: OnRetryClicked?) {
        this.retryClicked = retryClicked
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Making no internet dialog is not cancelable because our app is working on internet only
        dialog!!.setCancelable(false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    //WE can use custom dialog ui here , for now using default dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(
            requireActivity(), R.style.FullScreenDialog
        )
        builder
            .setMessage("No Internet found. Check your connection and Try again")
            .setNeutralButton(
                "Retry"
            ) { arg0, arg1 -> if (retryClicked != null) retryClicked!!.onRetryClicked() }
        val dialog: Dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.GRAY)
        )
        return dialog
    }

    interface OnRetryClicked {
        fun onRetryClicked()
    }


    companion object {
        fun newInstance(dataToShow: String?): MyAlertDialogFragment {
            return MyAlertDialogFragment()
        }
    }
}
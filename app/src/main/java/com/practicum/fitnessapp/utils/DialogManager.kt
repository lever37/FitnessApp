package com.practicum.fitnessapp.utils

import android.app.AlertDialog
import android.content.Context
import com.practicum.fitnessapp.R

object DialogManager {
    fun showDialog(context: Context, messageID: Int, listener:Listener){
        val builder = AlertDialog.Builder(context)
        var dialog : AlertDialog ? = null
        builder.setTitle(R.string.attention)
        builder.setMessage(messageID)
        builder.setPositiveButton(R.string.agree,){_,_->
            listener.onClick()
            dialog?.dismiss()
        }
        builder.setNegativeButton(R.string.cancel,){_,_->
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }
    interface Listener{
        fun onClick()
    }

}
package com.example.obukeweatherapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class MessageDialogFragment: DialogFragment() {

    var message: String? = ""

    companion object {
        fun newInstance(
            message: String
        ): MessageDialogFragment {
            val dialogFragment = MessageDialogFragment()
            val args = Bundle()
            args.putString("MESSAGE", message)
            dialogFragment.arguments = args
            return dialogFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        message = arguments?.getString("MESSAGE")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = inflater.inflate(R.layout.fragment_dialog_message, container, false)
        val messageTextView: TextView = view.findViewById(R.id.messageText)
        message?.let { value ->
            messageTextView.text = value
        }
        return view
    }
}
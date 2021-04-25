package com.cg.gweatherapplication.model

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.cg.gweatherapplication.R

class MyDialog : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder= AlertDialog.Builder(activity)
        builder.setIcon(R.drawable.ic_launcher_we)
        builder.setMessage("Please wait...")
        builder.setTitle("Getting Data...")
        return builder.create()
    }

}
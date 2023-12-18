package com.example.t_ket.presentation.components.dialogs


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.t_ket.databinding.ConfirmationDialogBinding

class ConfirmationDialog(
    private val onSubmitClickListener: (Float) -> Unit
): DialogFragment() {

    private lateinit var binding: ConfirmationDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = ConfirmationDialogBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)


        binding.dialog.setOnClickListener {
            dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

}
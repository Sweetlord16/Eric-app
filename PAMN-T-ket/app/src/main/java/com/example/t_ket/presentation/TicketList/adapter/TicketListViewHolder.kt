package com.example.t_ket.presentation.TicketList.adapter


import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.t_ket.core.domain.model.Ticket
import com.example.t_ket.databinding.ItemTicketBinding
import kotlinx.coroutines.launch

class TicketListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemTicketBinding.bind(view)

    fun render(ticket: Ticket, onItemSelected: (Ticket) -> Unit,  onGroupSelected:(Boolean) -> Unit) {

        binding.itemTitle.text = "Nombre: ${ticket.fullName}"

        binding.itemDni.text = "DNI: ${ticket.dni}"

        binding.itemGid.text = "Id de Grupo: ${ticket.idGroup}"

        binding.squareVerification.setOnClickListener(){
                onGroupSelected(false)
                onItemSelected(ticket)
                binding.confirmationView.isVisible = true
        }
        binding.groupBottom.setOnClickListener(){
            onGroupSelected(true)
            onItemSelected(ticket)
            binding.confirmationView.isVisible = true
        }
    }
}
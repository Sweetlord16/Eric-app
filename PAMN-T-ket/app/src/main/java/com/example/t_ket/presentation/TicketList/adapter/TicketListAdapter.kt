package com.example.t_ket.presentation.TicketList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.t_ket.R
import com.example.t_ket.core.domain.model.Ticket

class TicketListAdapter(private var ticketList: List<Ticket> = emptyList(),
                        private val onItemSelected:(Ticket) -> Unit,
                        private val onGroupSelected:(Boolean) -> Unit):
    RecyclerView.Adapter<TicketListViewHolder>() {

    fun updateList(list:List<Ticket>){
        ticketList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketListViewHolder {
        return TicketListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        )
    }

    override fun getItemCount() = ticketList.size

    override fun onBindViewHolder(holder: TicketListViewHolder, position: Int) {
        holder.render(ticketList[position],onItemSelected, onGroupSelected )
    }
    fun updateTickets(ticketlists: List<Ticket>){
        this.ticketList = ticketlists
        notifyDataSetChanged()
    }

}
package com.thangcao.ghichu.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thangcao.ghichu.R
import com.thangcao.ghichu.entities.Notes
import kotlinx.android.synthetic.main.item_rv_notes.view.*

class NotesAdapter(var arrList : List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_notes, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.tvTitle.text = arrList[position].title
        holder.itemView.tvDesc.text = arrList[position].noteText
        holder.itemView.tvDateTime.text = arrList[position].dateTime
        if (arrList[position].color!=null){
            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(arrList[position].color))
        }else{
            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(R.color.colorLightBlack.toString()))
        }
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }

}
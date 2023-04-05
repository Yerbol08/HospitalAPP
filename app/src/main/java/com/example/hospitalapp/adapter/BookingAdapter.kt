package com.example.hospitalapp.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalapp.HistoryActivity
import com.example.hospitalapp.R


class BookingAdapter(private val list: List<Booking>) : RecyclerView.Adapter<BookingAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingAdapter.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BookingAdapter.ViewHolder, position: Int) {
        val item = list.get(position)
        holder.dateTV.text = item.date +" " + item.time
        holder.name.text = item.f_name +" "+ item.name
        holder.diagnostics.text = item.text
        holder.doc_name.text = item.doctor
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTV:TextView
        val name: TextView
        val diagnostics: TextView
        val doc_name: TextView

        init {
            dateTV = itemView.findViewById(R.id.idTVDate)
            name = itemView.findViewById(R.id.idTVName)
            doc_name = itemView.findViewById(R.id.idTVDocName)
            diagnostics = itemView.findViewById(R.id.idTVDiagnosis)
        }
    }
}




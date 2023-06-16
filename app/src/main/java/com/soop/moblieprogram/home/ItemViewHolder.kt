package com.soop.moblieprogram

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import com.example.ahndwon.fragmentrecyclerviewexample.BoardActivity
//import com.example.ahndwon.fragmentrecyclerviewexample.databinding.ItemBasicBinding
import com.soop.moblieprogram.databinding.ItemBasicBinding

class ItemViewHolder(private val binding: ItemBasicBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: String) {
        Glide.with(binding.root.context)
            .load(item)
            .centerCrop()
            .into(binding.basicImageView)

        binding.root.setOnClickListener {
            val intent = Intent(binding.root.context, BoardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            binding.root.context.startActivity(intent)
        }
    }
}



//
//import android.content.Intent
//import android.view.View
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import kotlinx.android.synthetic.main.item_basic.view.*
//
//class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//    fun bind(item: String) {
//        Glide.with(itemView.context).load(item)
//            .centerCrop()
//            .into(itemView.basicImageView)
//
//        itemView.setOnClickListener {
//            Intent(itemView.context, BoardActivity::class.java).apply {
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            }.run { itemView.context.startActivity(this) }
//        }
//    }
//}
package com.soop.moblieprogram

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import com.example.ahndwon.fragmentrecyclerviewexample.databinding.ItemBasicBinding
import com.soop.moblieprogram.databinding.ItemBasicBinding

class ItemListAdapter : RecyclerView.Adapter<ItemViewHolder>() {
    var items: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemBasicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }
}





//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.ahndwon.fragmentrecyclerviewexample.databinding.ItemBasicBinding
//
//class ItemListAdapter : RecyclerView.Adapter<ItemViewHolder>() {
//    var items: List<String> = emptyList()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        val binding = ItemBasicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ItemViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        holder.bind(items[position])
//    }
//}
//

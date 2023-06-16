package com.soop.moblieprogram.board

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.soop.moblieprogram.R

class ContentAdapter(val items: MutableList<ContentModel>) :
    RecyclerView.Adapter<ContentAdapter.ViewHolder>() {
    // View Holder를 생성하고 View를 붙여주는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_content_list, parent, false)
        return ViewHolder(v)
    }

    // 생성된 View Holder에 데이터를 바인딩 해주는 메서드
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    // 데이터의 개수를 반환하는 메서드
    override fun getItemCount(): Int {
        return items.count()
    }

    // 화면에 표시 될 뷰를 저장하는 역할의 메서드
    // View를 재활용 하기 위해 각 요소를 저장해두고 사용한다.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { // 리사이클러뷰 item 클릭시 처리
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = items[position]
                    // 클릭된 아이템에 대한 처리를 수행합니다.
//                    val message = "Clicked: ${clickedItem.title}"
//                    Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
                    val intent: Intent = Intent(itemView.context, UserBoardActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    itemView.context.startActivity(intent)
                }
            }
        }
        fun bindItems(items: ContentModel) {
            val title = itemView.findViewById<TextView>(R.id.titleArea)
            val content = itemView.findViewById<TextView>(R.id.contentArea)
            val time = itemView.findViewById<TextView>(R.id.timeArea)

            title.text = items.title
            content.text = items.content
            time.text = items.time

        }
    }
}
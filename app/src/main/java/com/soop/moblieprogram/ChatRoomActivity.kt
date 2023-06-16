package com.soop.moblieprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.soop.moblieprogram.databinding.ActivityChatRoomBinding
import com.soop.moblieprogram.databinding.ItemMsgListBinding


class ChatRoomActivity : AppCompatActivity() {
    val binding by lazy { ActivityChatRoomBinding.inflate(layoutInflater) }
    val database = Firebase.database("https://mobliesoop-default-rtdb.firebaseio.com/")
    lateinit var msgRef: DatabaseReference

    var roomId: String = ""
    var roomTitle: String = ""

    val msgList = mutableListOf<com.soop.moblieprogram.chat.Message>()  // 메시지 목록 데이터
    lateinit var adapter: MsgListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 인텐트로 전달된 방 정보와 사용자 정보를 꺼낸다.
        roomId = intent.getStringExtra("roomId") ?: "none"
        roomTitle = intent.getStringExtra("roomTitle") ?: "없음"

        msgRef = database.getReference("rooms").child(roomId).child("messages")

        adapter = MsgListAdapter(msgList)
        with(binding) {
            recyclerMsgs.adapter = adapter
            recyclerMsgs.layoutManager = LinearLayoutManager(baseContext)
            textTitle.setText(roomTitle)
            btnBack.setOnClickListener { finish() }
            btnSend.setOnClickListener { sendMsg() }
        }
        loadMsgs()
    }

    // 메시지 목록을 읽어오는 메소드
    fun loadMsgs() {
        msgRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                msgList.clear()
                for (item in snapshot.children) {
                    item.getValue(com.soop.moblieprogram.chat.Message::class.java)?.let { msg ->
                        msgList.add(msg)        // 메시지 목록에 추가
                    }
                }
                adapter.notifyDataSetChanged()      // 어댑터 갱신
            }

            override fun onCancelled(error: DatabaseError) {
                print(error.message)
            }
        })
    }

    // 메시지를 파이어베이스에 전송하는 메소드
    fun sendMsg() {
        with(binding) {
            if (editMsg.text.isNotEmpty()) {
                val message =com.soop.moblieprogram.chat.Message(
                    editMsg.text.toString(),
                    ChatListFragment.userName
                )
                val msgId = msgRef.push().key!!
                message.id = msgId
                msgRef.child(msgId).setValue(message)
                editMsg.setText("")     // 메시지 전송 후 입력 필드 초기화화            }
            }
        }
    }
}
class MsgListAdapter(val msgList: MutableList<com.soop.moblieprogram.chat.Message>) :
    RecyclerView.Adapter<MsgListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemMsgListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val msg = msgList.get(position)
        holder.setMsg(msg)
    }

    override fun getItemCount(): Int {
        return msgList.size
    }

    class Holder(val binding: ItemMsgListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setMsg(msg: com.soop.moblieprogram.chat.Message) {
            binding.textName.setText(msg.userName)
            binding.textMsg.setText(msg.msg)
            binding.textDate.setText("${msg.timestamp}")
        }
    }
}
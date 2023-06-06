package com.soop.moblieprogram

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatlistFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatListAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var database: FirebaseDatabase
    private lateinit var chatRoomList: ArrayList<ChatRoom>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chatlist, container, false)

        chatRoomList = ArrayList()
        database = FirebaseDatabase.getInstance()

        recyclerView = view.findViewById(R.id.chatListRecyclerView)
        recyclerView.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        adapter = ChatListAdapter(chatRoomList)
        recyclerView.adapter = adapter

//        loadChatRooms()
        addDummyChatRooms() // 채팅방 더미 데이터 추가

        return view
    }

//    private fun loadChatRooms() {
//        val chatRoomsRef = database.getReference("chatRooms")
//        chatRoomsRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                chatRoomList.clear()
//
//                for (roomSnapshot in dataSnapshot.children) {
//                    val chatRoom = roomSnapshot.getValue(ChatRoom::class.java)
//                    chatRoom?.let {
//                        chatRoomList.add(it)
//                    }
//                }
//
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.e(TAG, "Failed to load chat rooms: ${databaseError.message}")
//            }
//        })
//    }

    private fun addDummyChatRooms() {
        // 채팅방 더미 데이터 3개 추가
        for (i in 1..3) {
            val chatRoom = ChatRoom("Chat Room $i", "Dummy Message $i")
            chatRoomList.add(chatRoom)
        }

        adapter.notifyDataSetChanged()
    }
}

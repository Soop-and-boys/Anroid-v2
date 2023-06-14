package com.soop.moblieprogram

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.soop.moblieprogram.chat.Room

class ChatListFragment : Fragment() {
    val database = Firebase.database("https://mobliesoop-default-rtdb.firebaseio.com/")
    val roomsRef = database.getReference("rooms")       // database와 rooms노드 연결
    private val roomList = mutableListOf<Room>()
    private lateinit var adapter: ChatRoomListAdapter

    companion object {
        var userId: String = ""
        var userName: String = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat_list, container, false)

        userId = arguments?.getString("userId") ?: "None"
        userName = arguments?.getString("userName") ?: "Anonymous"

        val btnCreate = view.findViewById<Button>(R.id.btnCreate)
        btnCreate.setOnClickListener { openCreateRoom() }

        adapter = ChatRoomListAdapter(roomList)
        val recyclerRooms = view.findViewById<RecyclerView>(R.id.recyclerRooms)
        recyclerRooms.adapter = adapter
        recyclerRooms.layoutManager = LinearLayoutManager(requireContext())

        loadRooms()

        return view
    }

    private fun loadRooms() {
        roomsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                roomList.clear()
                for (item in snapshot.children) {
                    item.getValue(Room::class.java)?.let { room ->
                        roomList.add(room)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                print(error.message)
            }
        })
    }

    private fun openCreateRoom() {
        val editTitle = EditText(requireContext())
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("방 이름")
            .setView(editTitle)
            .setPositiveButton("만들기") { _, _ ->
                createRoom(editTitle.text.toString())
            }
        dialog.show()
    }

    private fun createRoom(title: String) {
        val room = Room(title, userName)
        val roomId = roomsRef.push().key!!
        room.id = roomId
        roomsRef.child(roomId).setValue(room)
    }
}

class ChatRoomListAdapter(val roomList: MutableList<Room>) :
    RecyclerView.Adapter<ChatRoomListAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val room = roomList.get(position)
        holder.setRoom(room)
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var mRoom: Room

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatRoomActivity::class.java)
                intent.putExtra("roomId", mRoom.id)
                intent.putExtra("roomTitle", mRoom.title)
                itemView.context.startActivity(intent)

            }
        }

        fun setRoom(room: Room) {
            this.mRoom = room
            itemView.findViewById<TextView>(android.R.id.text1).setText(room.title)
        }
    }
}
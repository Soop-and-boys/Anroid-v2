package com.soop.moblieprogram.board

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.soop.moblieprogram.ChatRoomActivity
import com.soop.moblieprogram.databinding.ActivityUserBoardBinding


class UserBoardActivity : AppCompatActivity() {

    lateinit var binding:ActivityUserBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBoardBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        binding.contactBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://open.kakao.com/o/gt3bFcqf"))
            startActivity(intent)
        }
        binding.createChatBtn.setOnClickListener {
            val intent = Intent(this, ChatRoomActivity::class.java)
            val roomId = "asd"
            intent.putExtra("roomId", roomId) // 채팅방 ID를 전달
            val roomTitle ="사람들아 모여라"
            intent.putExtra("roomTitle", roomTitle) // 채팅방 제목을 전달
            startActivity(intent)
        }
    }
}
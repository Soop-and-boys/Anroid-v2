package com.soop.moblieprogram.board

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }
}
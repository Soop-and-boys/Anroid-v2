package com.soop.moblieprogram.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soop.moblieprogram.databinding.ActivityUserBoardBinding

class UserBoardActivity : AppCompatActivity() {

    lateinit var binding:ActivityUserBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBoardBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
    }
}
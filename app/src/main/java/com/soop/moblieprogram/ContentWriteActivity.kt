package com.soop.moblieprogram

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.soop.moblieprogram.databinding.ActivityContentWriteBinding
import java.text.SimpleDateFormat
import java.util.*

// ContentWriteActivity.kt
class ContentWriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityContentWriteBinding
    lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var contentRef: DatabaseReference // 전체 작성글

    private val category = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_content_write)

        val mAuth = Firebase.auth.currentUser // 현재 로그인한 유저
//        if (mAuth != null) {
//            // User is signed in
//        } else {
//            // No user is signed in
//        }
        //db 초기화
        mDbRef = Firebase.database.getReference("user")
        contentRef = Firebase.database.getReference("AllContent")

        binding.writeBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val time = getTime()
            val chatUrl = binding.chatUrlArea.text.toString()
            val location = binding.locationArea.text.toString()

            // push()는 목록을 만들어주며 랜덤한 문자열을 할당한다.
            val key = contentRef.push().key.toString()

            // child()는 해당 키 위치로 이동하는 메서드로 child()를 사용하여 key 값의 하위에 값을 저장한다.
            // setValue() 메서드를 사용하여 값을 저장한다.
            contentRef
                .child(key)
                .setValue(ContentModel(title, content, time, category, chatUrl, location))

            mDbRef.child(mAuth!!.uid).child("content").child(title)
                .setValue(ContentModel(title, content, time, category, chatUrl, location))

            Toast.makeText(this, "게시글 입력 완료", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkbox_pj -> {
                    if (checked) {
                        category.add(binding.checkboxPj.text.toString())
                    } else {
                        category.remove(binding.checkboxPj.text.toString())
                    }
                }
                R.id.checkbox_study -> {
                    if (checked) {
                        category.add(binding.checkboxStudy.text.toString())
                    } else {
                        category.remove(binding.checkboxStudy.text.toString())
                    }
                }
                R.id.checkbox_mentoring -> {
                    if (checked) {
                        category.add(binding.checkboxMentoring.text.toString())
                    } else {
                        category.remove(binding.checkboxMentoring.text.toString())
                    }
                }
                R.id.checkbox_etc -> {
                    if (checked) {
                        category.add(binding.checkboxEtc.text.toString())
                    } else {
                        category.remove(binding.checkboxEtc.text.toString())
                    }
                }
                R.id.checkbox_volunteer -> {
                    if (checked) {
                        category.add(binding.checkboxVolunteer.text.toString())
                    } else {
                        category.remove(binding.checkboxVolunteer.text.toString())
                    }
                }
                R.id.checkbox_competition -> {
                    if (checked) {
                        category.add(binding.checkboxCompetition.text.toString())
                    } else {
                        category.remove(binding.checkboxCompetition.text.toString())
                    }
                }
            }
        }
    }

    // 시간을 구하는 함수
    fun getTime(): String {
        val currentDateTime = Calendar.getInstance().time
        val dateFormat =
            SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(currentDateTime)

        return dateFormat
    }
}
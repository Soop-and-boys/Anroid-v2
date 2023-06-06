package com.soop.moblieprogram

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.soop.moblieprogram.databinding.ActivityMypageWriteListBinding

// 마이페이지에서 내가 작성한 게시글 보기
class MypageWriteListActivity: AppCompatActivity() {
    lateinit var binding: ActivityMypageWriteListBinding
    lateinit var contentAdapter: ContentAdapter
    private val contentList = mutableListOf<ContentModel>()
    lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypage_write_list)

        contentAdapter = ContentAdapter(contentList)
        // RecyclerView의 adapter에 ContentAdapter를 설정한다.
        binding.recyclerview.adapter = contentAdapter
        // layoutManager 설정
        // LinearLayoutManager을 사용하여 수직으로 아이템을 배치한다.
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
//
//        // 글쓰기 버튼을 클릭 했을 경우 ContentWriteActivity로 이동한다.
//        binding.contentWriteBtn.setOnClickListener {
//            startActivity(Intent(this, ContentWriteActivity::class.java))
//        }

        // 데이터베이스에서 데이터 읽어오기
        getFBContentData()
    }

    private fun getFBContentData() {
        val mAuth = Firebase.auth.currentUser // 현재 로그인한 유저

        mDbRef = Firebase.database.getReference("user")

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                contentList.clear()

                for (data in snapshot.children) {
                    val item = data.getValue(ContentModel::class.java)
                    Log.d("MypageWriteListActivity", "item: ${item}")
                    // 리스트에 읽어 온 데이터를 넣어준다.
                    contentList.add(item!!)

                }
                contentList.reverse()
                // notifyDataSetChanged()를 호출하여 adapter에게 값이 변경 되었음을 알려준다.
                contentAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        mDbRef.child(mAuth!!.uid).child("content").addValueEventListener((postListener))

    }
}

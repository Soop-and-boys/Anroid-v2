package com.soop.moblieprogram.board

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.soop.moblieprogram.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {
    private lateinit var binding: FragmentBoardBinding
    private lateinit var contentAdapter: ContentAdapter
    private val contentList = mutableListOf<ContentModel>()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoardBinding.inflate(inflater, container, false)

        contentAdapter = ContentAdapter(contentList)
        binding.recyclerview.adapter = contentAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        binding.contentWriteBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ContentWriteActivity::class.java))
        }

        getFBContentData()

        return binding.root
    }

    private fun getFBContentData() {
        val mAuth = Firebase.auth
        mDbRef = Firebase.database.getReference("AllContent")

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                contentList.clear()

                for (data in snapshot.children) {
                    val item = data.getValue(ContentModel::class.java)
//                    Log.d("MainActivity", "item: $item")
                    contentList.add(item!!)
                }
                contentList.reverse()
                contentAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        mDbRef.addValueEventListener(postListener)
    }
}

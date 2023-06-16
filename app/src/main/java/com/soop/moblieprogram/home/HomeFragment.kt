package com.soop.moblieprogram.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.soop.moblieprogram.*
//import com.example.ahndwon.fragmentrecyclerviewexample.databinding.FragmentHomeBinding
import com.soop.moblieprogram.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = FragmentListAdapter(childFragmentManager)

        adapter.fragments = listOf(
            BasicFragment.newInstance("시흥시 활동", Data_siheung.imageList),
            BasicFragment.newInstance("봉사활동", Data_volunteer.imageList),
            BasicFragment.newInstance("공모전", Data_contest.imageList),
            BasicFragment.newInstance("대외활동", Data_offCampus.imageList),
            BasicFragment.newInstance("개발행사", Data_devEvent.imageList)

//            BasicFragment.newInstance("SIXTH", Dummies.imageList),
//            BasicFragment.newInstance("SEVENTH", Dummies.imageList),
//            BasicFragment.newInstance("EIGHTH", Dummies.imageList),
//            BasicFragment.newInstance("NINTH", Dummies.imageList),
//            BasicFragment.newInstance("TENTH", Dummies.imageList)
        )

        binding.homeRecyclerView.adapter = adapter
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(view.context)

        return view
    }
}
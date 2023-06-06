package com.soop.moblieprogram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.soop.moblieprogram.databinding.FragmentBasicBinding

class BasicFragment : Fragment() {
    private lateinit var binding: FragmentBasicBinding

    companion object {
        const val FRAGMENT_TITLE = "fragment_title"
        const val FRAGMENT_IMAGE = "fragment_image"

        fun newInstance(title: String, images: ArrayList<String>): BasicFragment {
            return BasicFragment().apply {
                val bundle = Bundle()
                bundle.putString(FRAGMENT_TITLE, title)
                bundle.putStringArrayList(FRAGMENT_IMAGE, images)
                arguments = bundle
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentBasicBinding.inflate(inflater, container, false)
        val view = binding.root

        val title = arguments?.getString(FRAGMENT_TITLE)
        val images = arguments?.getStringArrayList(FRAGMENT_IMAGE) ?: ArrayList<String>()

        binding.fragmentTitle.text = title

        val adapter = ItemListAdapter()
        adapter.items = images
        binding.recyclerView.apply {
            this.adapter = adapter
            this.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        }

        return view
    }
}

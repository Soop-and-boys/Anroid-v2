package com.soop.moblieprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.soop.moblieprogram.CalendarFragment
import com.soop.moblieprogram.ChatFragment
import com.soop.moblieprogram.databinding.ActivityNaviBinding

private const val TAG_CALENDER = "calender_fragment"
private const val TAG_HOME = "home_fragment"
private const val TAG_MY_PAGE = "my_page_fragment"
private const val TAG_CHAT = "chat_fragment"

class NaviActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNaviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setFragment(TAG_HOME, HomeFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.calenderFragment -> setFragment(TAG_CALENDER, CalendarFragment())
                R.id.homeFragment -> setFragment(TAG_HOME, HomeFragment())
                R.id.myPageFragment-> setFragment(TAG_MY_PAGE, MypageFragment())
                R.id.chatFragment-> setFragment(TAG_CHAT, ChatFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val calender = manager.findFragmentByTag(TAG_CALENDER)
        val home = manager.findFragmentByTag(TAG_HOME)
        val myPage = manager.findFragmentByTag(TAG_MY_PAGE)
        val chat = manager.findFragmentByTag(TAG_CHAT)


        if (calender != null){
            fragTransaction.hide(calender)
        }
        if (home != null){
            fragTransaction.hide(home)
        }
        if (myPage != null) {
            fragTransaction.hide(myPage)
        }
        if (chat != null) {
            fragTransaction.hide(chat)
        }

        if (tag == TAG_CALENDER) {
            if (calender!=null){
                fragTransaction.show(calender)
            }
        }
        else if (tag == TAG_HOME) {
            if (home != null) {
                fragTransaction.show(home)
            }
        }
        else if (tag == TAG_MY_PAGE){
            if (myPage != null){
                fragTransaction.show(myPage)
            }
        }
        else if (tag == TAG_CHAT){
            if (chat != null){
                fragTransaction.show(chat)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}
package com.soop.moblieprogram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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

        //아마 처음 시작하는 화면을 보여주는 코드 인듯(?)
//        setActivity(CalenderActivity::class.java)

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {

//                R.id.calenderFragment -> setActivity(CalenderActivity::class.java)
//                R.id.homeFragment -> setActivity(HomeActivity::class.java)
//                R.id.myPageFragment-> setActivity(MypagegActivity::class.java)
//                R.id.chatFragment-> setActivity(ChatActivity::class.java)

                //여기 아래는 임시 주석 위에가 필요한거
                R.id.calenderFragment -> setFragment(TAG_CALENDER, CalendarFragment())
                R.id.homeFragment -> setFragment(TAG_HOME, HomeFragment())
                R.id.myPageFragment-> setFragment(TAG_MY_PAGE, MypageFragment())
                R.id.chatFragment-> setFragment(TAG_CHAT, ChatFragment())
                /*
                R.id.calendarFragment -> setActivity(CalendarActivity::class.java)
                R.id.homeFragment -> setFragment(TAG_HOME, HomeFragment())
                R.id.myPageFragment -> setFragment(TAG_MY_PAGE, MyPageFragment())
                R.id.chatFragment -> setActivity(ChatActivity::class.java)
                */

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
//    private fun setActivity(activityClass: Class<*>) {
//        val intent = Intent(this, activityClass)
//        startActivity(intent)
//    }

}
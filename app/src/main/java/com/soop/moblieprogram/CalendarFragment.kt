package com.soop.moblieprogram

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context.MODE_NO_LOCALIZED_COLLATORS
import java.io.FileInputStream
import java.io.FileOutputStream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class CalendarFragment : Fragment() {
    private var userID: String = "userID"
    private lateinit var fname: String
    private lateinit var str: String
    private lateinit var calendarView: CalendarView
    private lateinit var updateBtn: Button
    private lateinit var deleteBtn: Button
    private lateinit var saveBtn: Button
    private lateinit var diaryTextView: TextView
    private lateinit var diaryContent: TextView
//    private lateinit var title: TextView
    private lateinit var contextEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        // UI 요소 초기화
        calendarView = view.findViewById(R.id.calendarView)
        diaryTextView = view.findViewById(R.id.diaryTextView)
        saveBtn = view.findViewById(R.id.saveBtn)
        deleteBtn = view.findViewById(R.id.deleteBtn)
        updateBtn = view.findViewById(R.id.updateBtn)
        diaryContent = view.findViewById(R.id.diaryContent)
//        title = view.findViewById(R.id.title)
        contextEditText = view.findViewById(R.id.contextEditText)

//        title.text = "달력 일기장"

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            diaryTextView.visibility = View.VISIBLE
            saveBtn.visibility = View.VISIBLE
            contextEditText.visibility = View.VISIBLE
            diaryContent.visibility = View.INVISIBLE
            updateBtn.visibility = View.INVISIBLE
            deleteBtn.visibility = View.INVISIBLE
            diaryTextView.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
            contextEditText.setText("")
            checkDay(year, month, dayOfMonth, userID)
        }

        saveBtn.setOnClickListener {
            saveDiary(fname)
            contextEditText.visibility = View.INVISIBLE
            saveBtn.visibility = View.INVISIBLE
            updateBtn.visibility = View.VISIBLE
            deleteBtn.visibility = View.VISIBLE
            str = contextEditText.text.toString()
            diaryContent.text = str
            diaryContent.visibility = View.VISIBLE
        }

        return view
    }

    // 달력 내용 조회, 수정
    private fun checkDay(cYear: Int, cMonth: Int, cDay: Int, userID: String) {
        // 저장할 파일 이름 설정
        fname = "" + userID + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt"

        var fileInputStream: FileInputStream
        try {
            fileInputStream = requireActivity().openFileInput(fname)
            val fileData = ByteArray(fileInputStream.available())
            fileInputStream.read(fileData)
            fileInputStream.close()
            str = String(fileData)
            contextEditText.visibility = View.INVISIBLE
            diaryContent.visibility = View.VISIBLE
            diaryContent.text = str
            saveBtn.visibility = View.INVISIBLE
            updateBtn.visibility = View.VISIBLE
            deleteBtn.visibility = View.VISIBLE
            updateBtn.setOnClickListener {
                contextEditText.visibility = View.VISIBLE
                diaryContent.visibility = View.INVISIBLE
                contextEditText.setText(str)
                saveBtn.visibility = View.VISIBLE
                updateBtn.visibility = View.INVISIBLE
                deleteBtn.visibility = View.INVISIBLE
                diaryContent.text = contextEditText.text
            }
            deleteBtn.setOnClickListener {
                diaryContent.visibility = View.INVISIBLE
                updateBtn.visibility = View.INVISIBLE
                deleteBtn.visibility = View.INVISIBLE
                contextEditText.setText("")
                contextEditText.visibility = View.VISIBLE
                saveBtn.visibility = View.VISIBLE
                removeDiary(fname)
            }
            if (diaryContent.text == null) {
                diaryContent.visibility = View.INVISIBLE
                updateBtn.visibility = View.INVISIBLE
                deleteBtn.visibility = View.INVISIBLE
                diaryTextView.visibility = View.VISIBLE
                saveBtn.visibility = View.VISIBLE
                contextEditText.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 달력 내용 제거
    @SuppressLint("WrongConstant")
    private fun removeDiary(readDay: String?) {
        var fileOutputStream: FileOutputStream
        try {
            fileOutputStream = requireActivity().openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS)
            val content = ""
            fileOutputStream.write(content.toByteArray())
            fileOutputStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    // 달력 내용 추가
    @SuppressLint("WrongConstant")
    private fun saveDiary(readDay: String?) {
        var fileOutputStream: FileOutputStream
        try {
            fileOutputStream = requireActivity().openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS)
            val content = contextEditText.text.toString()
            fileOutputStream.write(content.toByteArray())
            fileOutputStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    // showDiaryContent 메서드를 MainActivity에 추가합니다.
    private fun showDiaryContent(content: String) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.diray_layout)
        val diaryTitle = dialog.findViewById<TextView>(R.id.diaryTitle)
        val diaryContent = dialog.findViewById<TextView>(R.id.diaryContent)
        diaryTitle.text = "일기 내용"
        diaryContent.text = content
        dialog.show()
    }
}

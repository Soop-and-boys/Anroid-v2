package com.soop.moblieprogram.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.soop.moblieprogram.R

class MypageFragment : Fragment() {
    // Initialize variables
    private lateinit var ivImage: ImageView
    private lateinit var tvName: TextView
    private lateinit var btLogout: Button
    private lateinit var btnMyBoard: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mypage, container, false)

        // Assign variables
        ivImage = view.findViewById(R.id.iv_image)
        tvName = view.findViewById(R.id.tv_name)
        btLogout = view.findViewById(R.id.bt_logout)
        btnMyBoard = view.findViewById(R.id.myBoradButton)

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize firebase user
        val firebaseUser = firebaseAuth.currentUser

        // Check condition
        if (firebaseUser != null) {

            // if image on storage doesn't exist, will load google profile image instead
            Glide.with(this@MypageFragment)
                .load("https://firebasestorage.googleapis.com/v0/b/mobliesoop.appspot.com/o/${firebaseUser.uid}?alt=media")
                .error(Glide.with(this@MypageFragment).load(firebaseUser.photoUrl))
                .into(ivImage)

            // set name on text view
            tvName.text = firebaseUser.displayName

        }

        // Initialize sign-in client
        googleSignInClient = GoogleSignIn.getClient(requireContext(), GoogleSignInOptions.DEFAULT_SIGN_IN)
        btLogout.setOnClickListener {
            // Sign out from Google
            googleSignInClient.signOut().addOnCompleteListener { task ->
                // Check condition
                if (task.isSuccessful) {
                    // When task is successful, sign out from Firebase
                    firebaseAuth.signOut()
                    // Display Toast
                    Toast.makeText(requireContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show()
                    // Finish activity
                    requireActivity().finish()
                }
            }
        }

        // Switch to UploadActivity
        val uploadButton = view.findViewById<Button>(R.id.uploadButton)
        uploadButton.setOnClickListener {
            val intent = Intent(requireContext(), UploadActivity::class.java)
            startActivity(intent)
        }

        val myboardButton = view.findViewById<Button>(R.id.myBoradButton)
        myboardButton.setOnClickListener {
            val intent = Intent(requireContext(), MypageWriteListActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}

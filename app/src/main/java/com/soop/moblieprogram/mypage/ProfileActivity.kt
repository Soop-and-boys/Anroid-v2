package com.soop.moblieprogram.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.soop.moblieprogram.LoginActivity
import com.soop.moblieprogram.R
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context

class ProfileActivity : AppCompatActivity() {
    // Initialize variable
    lateinit var ivImage: ImageView
    lateinit var tvName: TextView
    lateinit var btLogout: Button
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        // Assign variable
        ivImage = findViewById(R.id.iv_image)
        tvName = findViewById(R.id.tv_name)
        btLogout = findViewById(R.id.bt_logout)

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize firebase user
        val firebaseUser = firebaseAuth.currentUser

        // Check condition
        if (firebaseUser != null) {

            // if image on storage doesn't exist, will load google profile image instead
            Glide.with(this@ProfileActivity)
                .load("https://firebasestorage.googleapis.com/v0/b/mobliesoop.appspot.com/o/${firebaseUser.uid}?alt=media")
                .error(Glide.with(this@ProfileActivity).load(firebaseUser.photoUrl))
                .into(ivImage)

            // set name on text view
            tvName.text = firebaseUser.displayName

        }

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(this@ProfileActivity, GoogleSignInOptions.DEFAULT_SIGN_IN)
        btLogout.setOnClickListener {
            // Sign out from google
            googleSignInClient.signOut().addOnCompleteListener { task ->
                // Check condition
                if (task.isSuccessful) {
                    // When task is successful sign out from firebase
                    firebaseAuth.signOut()
                    // Display Toast
                    Toast.makeText(applicationContext, "Logout successful", Toast.LENGTH_SHORT).show()
                    // Finish activity

                    // bugfix: logout will call LoginActivity instead of previous activity
                    val intent = Intent(this, LoginActivity::class.java)
                    finish()
                    startActivity(intent)
                }
            }
        }

//         switch activity
        val uploadButton = findViewById<Button>(R.id.uploadButton)
        uploadButton.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }

    }
}
package com.soop.moblieprogram.mypage

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.soop.moblieprogram.R
import de.hdodenhof.circleimageview.CircleImageView

class UploadActivity : AppCompatActivity() {
    // creating variable for buttons, image view and Uri for file.
    lateinit var chooseImageBtn: CircleImageView
    lateinit var uploadImageBtn: Button
    lateinit var imageView: ImageView
    var fileUri: Uri? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        // on below line initializing variables for buttons and image view.
        chooseImageBtn = findViewById(R.id.idBtnChooseImage)
        uploadImageBtn = findViewById(R.id.idBtnUploadImage)
        imageView = findViewById(R.id.idIVImage)

        var firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // When firebase user is not equal to null set image on image view
            // if image on storage doesn't exist, will load google profile image instead
            Glide.with(this@UploadActivity)
                .load("https://firebasestorage.googleapis.com/v0/b/mobliesoop.appspot.com/o/${firebaseUser.uid}?alt=media")
                .error(Glide.with(this@UploadActivity).load(firebaseUser.photoUrl))
                .into(imageView)
        }

        // on below line adding click listener for our choose image button.
        chooseImageBtn.setOnClickListener {
            // on below line calling intent to get our image from phone storage.
            val intent = Intent()
            // on below line setting type of files which we want to pick in our case we are picking images.
            intent.type = "image/*"
            // on below line we are setting action to get content
            intent.action = Intent.ACTION_GET_CONTENT
            // on below line calling start activity for result to choose image.
            startActivityForResult(
                // on below line creating chooser to choose image.
                Intent.createChooser(
                    intent,
                    "Pick your image to upload"
                ),
                22
            )
        }

        // on below line adding click listener to upload image.
        uploadImageBtn.setOnClickListener {
            // on below line calling upload image button to upload our image.
            uploadImage()
        }
    }

    // on below line adding on activity result method this method is called when user picks the image.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // on below line we are checking if the result is ok
        if (requestCode == 22 && resultCode == RESULT_OK && data != null && data.data != null) {
            // on below line initializing file uri with the data which we get from intent
            fileUri = data.data
            try {
                // on below line getting bitmap for image from file uri.
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, fileUri);
                // on below line setting bitmap for our image view.
                imageView.setImageBitmap(bitmap)
            } catch (e: Exception) {
                // handling exception on below line
                e.printStackTrace()
            }
        }
    }

    // on below line creating a function to upload our image.
    fun uploadImage() {
        // on below line checking weather our file uri is null or not.
        if (fileUri != null) {
            // on below line displaying a progress dialog when uploading an image.
            val progressDialog = ProgressDialog(this)
            // on below line setting title and message for our progress dialog and displaying our progress dialog.
            progressDialog.setTitle("변경중..")
            progressDialog.setMessage("사진 업로드중..")
            progressDialog.show()

            // on below line creating a storage reference for firebase storage and creating a child in it with
            // fix: changed random uuid to current user's uid.png

            var firebaseAuth = FirebaseAuth.getInstance()
            val firebaseUser = firebaseAuth.currentUser

            val ref: StorageReference = FirebaseStorage.getInstance().getReference()
                .child(firebaseUser!!.uid.toString())
            // on below line adding a file to our storage.
            ref.putFile(fileUri!!).addOnSuccessListener {
                // this method is called when file is uploaded.
                // in this case we are dismissing our progress dialog and displaying a toast message
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "사진 변경 완료", Toast.LENGTH_SHORT).show()

                // feat: switch to ProfileActivity
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)

            }.addOnFailureListener {
                // this method is called when there is failure in file upload.
                // in this case we are dismissing the dialog and displaying toast message
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "사진 변경 실패", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

}

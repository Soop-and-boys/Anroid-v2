package com.soop.moblieprogram

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import java.util.*

class UploadActivity : AppCompatActivity() {
    // creating variable for buttons, image view and Uri for file.
    lateinit var chooseImageBtn: Button
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

        // test: set placeholder image

        var firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // When firebase user is not equal to null set image on image view

            Glide.with(this@UploadActivity).load(firebaseUser.photoUrl).into(imageView)
//            // set name on text view
//            tvName.text = firebaseUser.displayName
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
            // random uuid
            val ref: StorageReference = FirebaseStorage.getInstance().getReference()
                .child(UUID.randomUUID().toString())
            // on below line adding a file to our storage.
            ref.putFile(fileUri!!).addOnSuccessListener {
                // this method is called when file is uploaded.
                // in this case we are dismissing our progress dialog and displaying a toast message
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "사진 변경 완료", Toast.LENGTH_SHORT).show()
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











//import android.app.Activity
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.view.View
//import android.widget.ImageView
//import androidx.appcompat.app.AppCompatActivity
//import com.bumptech.glide.Glide
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.storage.FirebaseStorage
//
//
//class UploadActivity : AppCompatActivity() {
//
//    private lateinit var imageView: ImageView
//    private lateinit var firebaseStorage: FirebaseStorage
//    lateinit var firebaseAuth: FirebaseAuth
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_upload)
//
//        imageView = findViewById(R.id.imageView)
//        firebaseStorage = FirebaseStorage.getInstance()
//
//
//        firebaseAuth = FirebaseAuth.getInstance()
////        val firebaseUser = firebaseAuth.currentUser
//
//        // Set click listener on button or UI element to trigger photo upload process
//        // Here, we assume a button with ID "uploadButton"
//        findViewById<View>(R.id.uploadButton).setOnClickListener {
//            openImagePicker()
//        }
//
//        // Load placeholder image
//        Glide.with(this)
//            .load(R.drawable.placeholder)
//            .into(imageView)
//    }
//
//    private fun openImagePicker() {
//        // Use an image picker library or built-in intent to select an image
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "image/*"
//        startActivityForResult(intent, PICK_IMAGE_REQUEST)
//    }
//
//override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    super.onActivityResult(requestCode, resultCode, data)
//
//    val firebaseUser = firebaseAuth.currentUser
//
//    if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
//        val selectedImageUri: Uri? = data?.data
//        if (selectedImageUri != null) {
//            // Upload the selected image to Firebase Storage
//            val storageReference = firebaseStorage.reference
//                .child("uploads/${firebaseUser!!.uid}/${selectedImageUri.lastPathSegment}")
//            storageReference.putFile(selectedImageUri)
//                .addOnSuccessListener { taskSnapshot ->
//                    // Image uploaded successfully
//                    // Get the download URL of the uploaded image
//                    val downloadUrl = taskSnapshot.storage.downloadUrl.toString()
//
//                    // Load and display the uploaded image using Glide or Picasso
//                    Glide.with(this)
//                        .load(downloadUrl)
//                        .into(imageView)
//                }
//                .addOnFailureListener { exception ->
//                    // Handle any errors that occurred during the upload
//                    // Display an error message to the user if necessary
//                }
//        }
//    }
//}
//
//    companion object {
//        private const val PICK_IMAGE_REQUEST = 1
//    }
//}


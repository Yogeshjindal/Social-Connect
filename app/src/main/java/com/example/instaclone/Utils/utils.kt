package com.example.instaclone.Utils

import android.app.ProgressDialog
import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import javax.security.auth.callback.Callback

fun uploadImage(uri:Uri,folderName:String,callback: (String?)->Unit){
    var imageUrl:String?=null
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString()).putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageUrl=it.toString()
                callback(imageUrl)
            }
        }


}
fun uploadReel(uri:Uri,folderName:String,progressDialog: ProgressDialog,callback: (String?)->Unit){
    var imageUrl:String?=null
    progressDialog.setTitle("Uploading. . . .")
    progressDialog.show()
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString()).putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageUrl=it.toString()
                progressDialog.dismiss()
                callback(imageUrl)
            }
        }.addOnProgressListener {
            val uploadedvalue:Long=(it.bytesTransferred/it.totalByteCount )*100
            progressDialog.setMessage("Uploaded $uploadedvalue")
        }


}
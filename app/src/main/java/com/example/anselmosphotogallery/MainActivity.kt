package com.example.anselmosphotogallery
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.GridLayoutManager
import com.example.anselmosphotogallery.adapter.GalleryImageAdapter
import com.example.anselmosphotogallery.adapter.GalleryImageClickListener
import com.example.anselmosphotogallery.adapter.Image
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.*
const val PICK_PDF_FILE = 2
//
//fun openFile(pickerInitialUri: Uri) {
//    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
//        addCategory(Intent.CATEGORY_OPENABLE)
//        type = "application/pdf"
//
//        // Optionally, specify a URI for the file that should appear in the
//        // system file picker when it loads.
//        putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
//    }
//
//    startActivityForResult(intent, PICK_PDF_FILE)
//}
class MainActivity : AppCompatActivity(), GalleryImageClickListener {
    // gallery column count
    private val SPAN_COUNT = 3
    private val imageList = ArrayList<Image>()
    private val filepath = "Downloads"
    internal var myExternalFile: File?=null
//    lateinit var galleryAdapter: GalleryImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "message")
        setContentView(R.layout.activity_main)
        // init adapter
//        galleryAdapter = GalleryImageAdapter(imageList)
//        galleryAdapter.listener = this
        // init recyclerview
//        recyclerView.layoutManager = GridLayoutManager(this, SPAN_COUNT)
//        recyclerView.adapter = galleryAdapter
//        // load images
//        loadImages()
        btnChoosePhoto.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else{
                    chooseImageGallery();

                }
            }else{
                chooseImageGallery();

            }

        }
    }
    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE)
    }

    companion object {
        private val IMAGE_CHOOSE = 1000;
        private val PERMISSION_CODE = 1001;
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("MAIN", "hahahaahaha")

        if(resultCode == Activity.RESULT_OK){
            viewImage.setImageURI(data?.data)
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chooseImageGallery()
                }else{
                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun loadImages() {

        val parent = getExternalFilesDir(filepath)
        Log.d("MAIN", parent.toString())
        val photoUri: Uri = Uri.fromFile( File(parent, "test")  )
        imageList.add(Image(photoUri.getPath().toString(), "Beach Houses"))
        imageList.add(Image("https://i.ibb.co/wBYDxLq/beach.jpg", "Beach Houses"))
//        imageList.add(Image("https://i.ibb.co/gM5NNJX/butterfly.jpg", "Butterfly"))
//        imageList.add(Image("https://i.ibb.co/10fFGkZ/car-race.jpg", "Car Racing"))
//        imageList.add(Image("https://i.ibb.co/ygqHsHV/coffee-milk.jpg", "Coffee with Milk"))
//        imageList.add(Image("https://i.ibb.co/7XqwsLw/fox.jpg", "Fox"))
//        imageList.add(Image("https://i.ibb.co/L1m1NxP/girl.jpg", "Mountain Girl"))
//        imageList.add(Image("https://i.ibb.co/wc9rSgw/desserts.jpg", "Desserts Table"))
//        imageList.add(Image("https://i.ibb.co/wdrdpKC/kitten.jpg", "Kitten"))
//        imageList.add(Image("https://i.ibb.co/dBCHzXQ/paris.jpg", "Paris Eiffel"))
//        imageList.add(Image("https://i.ibb.co/JKB0KPk/pizza.jpg", "Pizza Time"))
//        imageList.add(Image("https://i.ibb.co/VYYPZGk/salmon.jpg", "Salmon "))
//        imageList.add(Image("https://i.ibb.co/JvWpzYC/sunset.jpg", "Sunset in Beach"))
//        galleryAdapter.notifyDataSetChanged()
    }
    override fun onClick(position: Int) {
        // handle click of image
    }
}
private const val REQUEST_CODE = 13
private lateinit var filePhoto: File
private const val FILE_NAME = "photo.jpg"
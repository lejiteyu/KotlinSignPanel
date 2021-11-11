package com.wong.sign

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    private val SIGN_REQUEST: Int = 1000
    var mIVSign: ImageView? = null
    lateinit var handWrite : Button
    lateinit var mainLayout:ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainLayout = findViewById(R.id.main_layout)

        mIVSign = findViewById(R.id.iv_sign)

        handWrite  = findViewById(R.id.handwrite)
        handWrite.setOnClickListener {
            Keyboardkt().showKeyboard(mainLayout)
        }

    }

    /**
     * 方法默认是public的
     */
    fun onSign(view: View): Unit {
        val intent: Intent = Intent()
        intent.setClass(this, SignActivity().javaClass)
        /*或intent.setClass(this,SignActivity::class.java);*/
        startActivityForResult(intent, SIGN_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                SIGN_REQUEST -> {
                    val byteArray: ByteArray? = data!!.getByteArrayExtra("bitmap")
                    val bitmap: Bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
                    mIVSign!!.post {
                         mIVSign!!.setImageBitmap(BitmapUtils.createScaleBitmap(bitmap,mIVSign!!.width,mIVSign!!.height))
                    }

                }
                else -> {
                }
            }
        }
    }
}
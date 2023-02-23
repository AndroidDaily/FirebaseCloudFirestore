package com.firebase.my.firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), OnClickListener {
    private val TAG = MainActivity::class.java.simpleName

    private var btnWrite: Button? = null
    private var btnRead: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnWrite = findViewById(R.id.btnWrite)
        btnRead = findViewById(R.id.btnRead)

        btnWrite?.setOnClickListener(this)
        btnRead?.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnWrite -> {
                val db = Firebase.firestore
                val city = hashMapOf(
                    "name" to "Los Angeles",
                    "state" to "CA",
                    "country" to "USA"
                )

                db.collection("cities").document("LA")
                    .set(city)
                    .addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener {
                            exception -> Log.e(TAG, "Error writing document=>", exception)
                    }
            }
            R.id.btnRead -> {
                val db = Firebase.firestore
                db.collection("cities")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            Log.d(TAG, "${document.id} => ${document.data}")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.e(TAG, "Error getting documents=>", exception)
                    }

            }
        }
    }
}
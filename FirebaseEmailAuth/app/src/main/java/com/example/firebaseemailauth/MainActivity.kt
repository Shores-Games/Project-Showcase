package com.example.firebaseemailauth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseemailauth.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        // log out button
        val btnSignOut = findViewById<Button>(R.id.btnMainSignOut)
        btnSignOut.setOnClickListener {
            Firebase.auth.signOut()
            verifyUser()
            Toast.makeText(this, "User Sign Out", Toast.LENGTH_LONG).show()
            Intent(applicationContext,Login::class.java).also{startActivity(it)}
        }


    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            verifyUser()

        }
    }

    private fun verifyUser(){
        val user = Firebase.auth.currentUser
        user?.let {
            val email = it.email
            val emailVerified = it.isEmailVerified
            binding.txtMainEmail.text = email
            if(emailVerified){
                binding.txtMainVerification.text = "Email Verified"
            }else{
                binding.txtMainVerification.text = "Email Not Verified"
            }
        }
    }

}

package com.example.firebaseemailauth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseemailauth.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var currentUser: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        auth = Firebase.auth


        val btnLoginLogin = findViewById<Button>(R.id.btnLoginLogin)

        btnLoginLogin.setOnClickListener {
            email = binding.editLoginEmail.text.toString().trim()
            password = binding.editLoginPassword.text.toString().trim()
            if (email.isEmpty()) {
                binding.editLoginEmail.error = "Insert Valid Email Address"
                binding.editLoginEmail.requestFocus()
            } else if (password.isEmpty()) {
                binding.editLoginPassword.error = "Insert Valid Password"
                binding.editLoginPassword.requestFocus()
            } else if (password.length < 6) {
                binding.editLoginPassword.error = "Password length must be 6 characters or more."
                binding.editLoginPassword.requestFocus()
            }else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        currentUser = auth.currentUser!!
                        Intent(applicationContext,MainActivity::class.java).also{startActivity(it)}
                    }else{
                        Toast.makeText(baseContext, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        // create user button
        val txtLoginRegister = findViewById<TextView>(R.id.txtLoginRegister)
        txtLoginRegister.setOnClickListener {
            val currentUser = auth.currentUser
            if (currentUser == null) {
                Intent(applicationContext,User::class.java).also{startActivity(it)}
            }else{
                Firebase.auth.signOut()
                Intent(applicationContext,User::class.java).also{startActivity(it)}
            }
        }


    }
}

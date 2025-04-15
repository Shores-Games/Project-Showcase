package com.example.firebaseemailauth

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseemailauth.databinding.ActivityUserBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class User : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var currentUser: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val btnUserBack = findViewById<Button>(R.id.btnUserBack)
        btnUserBack.setOnClickListener {
            finish()
        }
        val btnUserRegister = findViewById<Button>(R.id.btnUserLogin)

        btnUserRegister.setOnClickListener {
            email = binding.editUserEmail.text.toString().trim()
            password = binding.editUserPassword.text.toString().trim()
            val rePassword = binding.editUserReinput.text.toString().trim()
            if (email.isEmpty()){
                binding.editUserEmail.error = "Insert Valid Email Address"
                binding.editUserEmail.requestFocus()
            }else if (password.isEmpty()){
                binding.editUserPassword.error = "Insert Valid Password"
                binding.editUserPassword.requestFocus()
            }else if (password.length < 6){
                binding.editUserPassword.error = "Password length must be 6 characters or more."
                binding.editUserPassword.requestFocus()
            }else if (password != rePassword){
                binding.editUserReinput.error = "Passwords Did Not Match"
                binding.editUserReinput.requestFocus()
            }else{
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){
                            currentUser = Firebase.auth.currentUser!!
                            currentUser.sendEmailVerification().addOnCompleteListener{
                                if(it.isSuccessful){
                                    Snackbar.make(findViewById(binding.cardView.id),
                                        "A Verification Email has been sent, please verify.", Snackbar.LENGTH_INDEFINITE)
                                        .apply {
                                            setAction("done") {
                                                verifyUser()
                                            }
                                            show()
                                        }
                                }
                            }
                        }else{
                            val ex = task.exception.toString()
                            if (ex.contains("the email address is already in use by another account.", false)){
                                Toast.makeText(baseContext, "The email address is already in use.", Toast.LENGTH_SHORT).show()
                                finish()
                            }else if (ex.contains("The email address is badly formatted.", false)){
                                Toast.makeText(baseContext, "That is not a valid email address", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(baseContext, task.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }

        }
    }

    private fun verifyUser(){
        Firebase.auth.signOut()
        Thread.sleep(2000)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    currentUser = auth.currentUser!!
                    finish()
                }
            }
    }
}

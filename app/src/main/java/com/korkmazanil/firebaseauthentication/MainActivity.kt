package com.korkmazanil.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.korkmazanil.firebaseauthentication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding : ActivityMainBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        //SignIn CardView GONE -- SignUp CardView VISIBLE
        binding.signInToSignUp.setOnClickListener {
            binding.cardViewSignIn.visibility = View.GONE
            binding.cardViewSignUp.visibility = View.VISIBLE
        }

        //SignIn CardView VISIBLE -- SignUp CardView GONE
        binding.signUpToSignIn.setOnClickListener {
            binding.cardViewSignIn.visibility = View.VISIBLE
            binding.cardViewSignUp.visibility = View.GONE
        }

        binding.kayitOlBtnSignUp.setOnClickListener {
            signUp()
        }

        binding.girisYapButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val email = binding.emailAdresEditTextSignIn.text.toString()
        val password = binding.parolaEditTextSignIn.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = auth.currentUser?.displayName.toString()
                            val inputText = resources.getString(R.string.welcome)
                            Toast.makeText(this, "$inputText $user", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, ProfilActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
        }
    }

    private fun signUp() {

        val email = binding.emailAdresEditTextSignUp.text.toString()
        val password = binding.parolaEditTextSignUp.text.toString()
        val user_name = binding.kullaniciAdiEditTextSignUp.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()){

            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful){

                        val user = auth.currentUser
                        val profileChangeRequest = UserProfileChangeRequest.Builder()
                            .setDisplayName(user_name)
                            .build()

                        user?.updateProfile(profileChangeRequest)?.addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(this,resources.getString(R.string.user_created), Toast.LENGTH_LONG).show()
                                binding.emailAdresEditTextSignIn.text = binding.emailAdresEditTextSignUp.text
                                binding.parolaEditTextSignIn.text = binding.parolaEditTextSignUp.text
                                binding.cardViewSignIn.visibility = View.VISIBLE
                                binding.cardViewSignUp.visibility = View.GONE
                            }
                        }
                    }
                }
                .addOnFailureListener{ exception ->
                    Toast.makeText(this,exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }
    }
}
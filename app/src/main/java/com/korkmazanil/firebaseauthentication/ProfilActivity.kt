package com.korkmazanil.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.korkmazanil.firebaseauthentication.databinding.ActivityProfilBinding

class ProfilActivity : AppCompatActivity() {

    private lateinit var profilBinding: ActivityProfilBinding
    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profilBinding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(profilBinding.root)

        auth = FirebaseAuth.getInstance()

        val actionBar : ActionBar? = supportActionBar
        if (actionBar != null){
            actionBar.title = resources.getString(R.string.profile)
        }
        setSupportActionBar(profilBinding.toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sign_out_profile_activity,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.sign_out){
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
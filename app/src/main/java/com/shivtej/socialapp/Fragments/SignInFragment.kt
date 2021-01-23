package com.shivtej.socialapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.shivtej.socialapp.MainActivity
import com.shivtej.socialapp.R


class SignInFragment : Fragment() {

    private lateinit var mEmail: TextInputEditText
    private lateinit var mPassword: TextInputEditText
    private lateinit var mSignIn: MaterialButton
    private lateinit var mAuth: FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        mEmail = view.findViewById(R.id.et_email)
        mPassword = view.findViewById(R.id.et_password)
        mSignIn = view.findViewById(R.id.btn_signin)
        mAuth = FirebaseAuth.getInstance()

        mPassword.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                /* Write your logic here that will be executed when user taps next button */
                    signIn()
                handled = true
            }
            handled
        })

        mSignIn.setOnClickListener {
            signIn()

        }
        // Inflate the layout for this fragment
        return view
    }

    private fun signIn() {
        val email = mEmail.text.toString().trim()
        val password = mPassword.text.toString().trim()
        if(email.isEmpty() && password.isEmpty()){
            Toast.makeText(activity, "Please Enter details", Toast.LENGTH_SHORT).show()
        }else{
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        updateUI()
                    }else{
                        Toast.makeText(activity, "User Not Found", Toast.LENGTH_SHORT).show()
                    }

                }
        }

    }

    private fun updateUI() {
           val intent = Intent(activity, MainActivity::class.java)
           startActivity(intent)

    }

}
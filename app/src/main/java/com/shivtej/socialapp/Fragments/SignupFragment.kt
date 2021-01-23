package com.shivtej.socialapp.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.shivtej.socialapp.Daos.UserDao
import com.shivtej.socialapp.MainActivity
import com.shivtej.socialapp.Models.Users
import com.shivtej.socialapp.R


class SignupFragment : Fragment() {

    private lateinit var mEmail: TextInputEditText
    private lateinit var mName: TextInputEditText
    private lateinit var mPassword: TextInputEditText
    private lateinit var mSignUp: MaterialButton
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { val view = inflater.inflate(R.layout.fragment_signup, container, false)
        mEmail = view.findViewById(R.id.et_email)
        mName = view.findViewById(R.id.et_name)

        mPassword = view.findViewById(R.id.et_password)
        mSignUp = view.findViewById(R.id.btn_signup)
        mAuth = FirebaseAuth.getInstance()

        mPassword.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                /* Write your logic here that will be executed when user taps next button */
                signUp()
                handled = true
            }
            handled
        })

        mSignUp.setOnClickListener {
            signUp()

        }


        // Inflate the layout for this fragment
        return view
    }

    private fun signUp() {
        val email = mEmail.text.toString().trim()
        val password = mPassword.text.toString().trim()
        val name = mName.text.toString().trim()
        if(email.isEmpty() && password.isEmpty() && name.isEmpty()){
            Toast.makeText(activity, "Please Enter details", Toast.LENGTH_SHORT).show()
        }else{
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {task->
                    if(task.isSuccessful){
                        val user1 = mAuth.currentUser
                        val user = Users(user1?.uid.toString(), name)
                        val userDao = UserDao()
                        userDao.addUser(user)
                        updateUI()
                    }else{
                        Toast.makeText(activity, "User Not Found", Toast.LENGTH_SHORT).show()
                    }

                }
                .addOnFailureListener{
                    Toast.makeText(activity, it.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                }
        }

    }

    private fun updateUI() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)

    }


}
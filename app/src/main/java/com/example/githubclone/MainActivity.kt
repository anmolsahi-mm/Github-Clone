package com.example.githubclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubclone.presentation.loginscreen.LoginScreen
import com.example.githubclone.ui.theme.GithubCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val firebaseAuth = FirebaseAuth.getInstance()
//        val provider = OAuthProvider.newBuilder("github.com")
//
//        val pendingResultTask = firebaseAuth.pendingAuthResult
//        if (pendingResultTask != null) {
//            // There's already some response here
//
//            pendingResultTask
//                .addOnSuccessListener { authResult ->
//
//                }
//                .addOnFailureListener { exception ->
//
//                }
//        } else {
//            // There's no pending Result. Start the Sign-in Flow
//
//            firebaseAuth
//                .startActivityForSignInWithProvider(this, provider.build())
//                .addOnSuccessListener { authResult ->
//
//
//                }
//                .addOnFailureListener { exception ->
//
//                }
//        }

        setContent {
            GithubCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GithubCloneTheme {
        LoginScreen()
    }
}
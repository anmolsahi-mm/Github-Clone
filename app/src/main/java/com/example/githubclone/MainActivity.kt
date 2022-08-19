package com.example.githubclone

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.githubclone.navigation.GithubNavigation
import com.example.githubclone.navigation.Screen
import com.example.githubclone.presentation.loginscreen.LoginScreen
import com.example.githubclone.ui.theme.GithubCloneTheme
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider


class MainActivity : ComponentActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var provider: OAuthProvider.Builder
    private var pendingResultTask: Task<AuthResult>? = null
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        provider = OAuthProvider.newBuilder("github.com")

        pendingResultTask = firebaseAuth.pendingAuthResult

        firebaseUser = firebaseAuth.currentUser

        setContent {
            GithubCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    GithubNavigation(navController = navController, onSignInClick = {
                        handleSignIn(navController)
                    })

                    if (firebaseUser != null) {
                        Toast.makeText(this, firebaseUser!!.displayName, Toast.LENGTH_LONG).show()
                        navController.navigate(Screen.Home.route)
                    }
                }
            }
        }
    }

    private fun handleSignIn(navController: NavHostController) {
        if (pendingResultTask != null) {
            // There's already some response here
            handlePendingResultTask(navController)
        } else {
            // There's no pending Result. Start the Sign-in Flow
            handleGithubSignInFlow(navController)
        }
    }

    private fun handlePendingResultTask(navController: NavHostController) {
        pendingResultTask
            ?.addOnSuccessListener { authResult ->
                val profile = authResult.additionalUserInfo?.username
                val accessToken = authResult.credential

                Toast.makeText(
                    this,
                    "username: $profile, accessToken: $accessToken",
                    Toast.LENGTH_LONG
                ).show()
                navController.navigate(Screen.Home.route)
            }
            ?.addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    "Something Went Wrong: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun handleGithubSignInFlow(navController: NavHostController) {
        firebaseAuth
            .startActivityForSignInWithProvider(this, provider.build())
            .addOnSuccessListener { authResult ->
                val profile = authResult.additionalUserInfo?.username
                val accessToken = authResult.credential

                Toast.makeText(
                    this,
                    "username: $profile, accessToken: $accessToken",
                    Toast.LENGTH_LONG
                ).show()
                navController.navigate(Screen.Home.route)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    "Something Went Wrong: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GithubCloneTheme {
        LoginScreen() {}
    }
}
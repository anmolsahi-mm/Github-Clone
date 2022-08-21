package com.example.githubclone.presentation.common

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

fun signIn(
    activity: Activity,
    navigateToHomeScreen: () -> Unit,
) {

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val provider: OAuthProvider.Builder = OAuthProvider.newBuilder("github.com")
    val pendingResultTask: Task<AuthResult>? = firebaseAuth.pendingAuthResult

    if (pendingResultTask != null) {
        // There's already some response here
        handlePendingResultTask(activity, pendingResultTask, navigateToHomeScreen)
    } else {
        // There's no pending Result. Start the Sign-in Flow
        handleSignInFlow(activity, firebaseAuth, provider, navigateToHomeScreen)
    }
}

private fun handlePendingResultTask(
    activity: Activity,
    pendingResultTask: Task<AuthResult>?,
    navigateToHomeScreen: () -> Unit,
) {
    pendingResultTask
        ?.addOnSuccessListener { authResult ->
            val username = authResult.additionalUserInfo?.username
            val oAuthCredential = authResult.credential as OAuthCredential
            val accessToken = oAuthCredential.accessToken

            Log.i("Github SignIn", "username: $username, accessToken: $accessToken")
            navigateToHomeScreen()
        }
        ?.addOnFailureListener { exception ->
            Toast.makeText(
                activity,
                "Something Went Wrong: ${exception.message}",
                Toast.LENGTH_LONG
            ).show()
        }
}

private fun handleSignInFlow(
    activity: Activity,
    firebaseAuth: FirebaseAuth,
    provider: OAuthProvider.Builder,
    navigateToHomeScreen: () -> Unit,
) {
    firebaseAuth
        .startActivityForSignInWithProvider(activity, provider.build())
        .addOnSuccessListener { authResult ->
            val username = authResult.additionalUserInfo?.username
            val oAuthCredential = authResult.credential as OAuthCredential
            val accessToken = oAuthCredential.accessToken

            Log.i("Github SignIn", "username: $username, accessToken: $accessToken")
            navigateToHomeScreen()
        }
        .addOnFailureListener { exception ->
            Toast.makeText(
                activity,
                "Something Went Wrong: ${exception.message}",
                Toast.LENGTH_LONG
            ).show()
        }
}

fun checkForExistingUser(activity: Activity): FirebaseUser? {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser: FirebaseUser? = firebaseAuth.currentUser

    if (firebaseUser != null) {
        Toast.makeText(activity, firebaseUser.displayName, Toast.LENGTH_LONG).show()
    }
    return firebaseUser
}
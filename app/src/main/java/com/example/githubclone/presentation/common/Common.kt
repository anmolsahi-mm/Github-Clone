package com.example.githubclone.presentation.common

import android.app.Activity
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider

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
        handleGithubSignInFlow(activity, firebaseAuth, provider, navigateToHomeScreen)
    }
}

private fun handlePendingResultTask(
    activity: Activity,
    pendingResultTask: Task<AuthResult>?,
    navigateToHomeScreen: () -> Unit,
) {
    pendingResultTask
        ?.addOnSuccessListener { authResult ->
            val profile = authResult.additionalUserInfo?.username
            val accessToken = authResult.credential

            Toast.makeText(
                activity,
                "username: $profile, accessToken: $accessToken",
                Toast.LENGTH_LONG
            ).show()
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

private fun handleGithubSignInFlow(
    activity: Activity,
    firebaseAuth: FirebaseAuth,
    provider: OAuthProvider.Builder,
    navigateToHomeScreen: () -> Unit,
) {
    firebaseAuth
        .startActivityForSignInWithProvider(activity, provider.build())
        .addOnSuccessListener { authResult ->
            val profile = authResult.additionalUserInfo?.username
            val accessToken = authResult.credential

            Toast.makeText(
                activity,
                "username: $profile, accessToken: $accessToken",
                Toast.LENGTH_LONG
            ).show()
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
package com.example.githubclone.presentation.common

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.example.githubclone.R
import com.example.githubclone.ui.theme.lightTextColor
import com.example.githubclone.ui.theme.linkTextColor
import com.example.githubclone.utils.showToast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider
import timber.log.Timber

fun signIn(
    activity: Activity,
    navigateToHomeScreen: () -> Unit,
) {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val provider: OAuthProvider.Builder = OAuthProvider.newBuilder("github.com")
    val pendingResultTask: Task<AuthResult>? = firebaseAuth.pendingAuthResult

    pendingResultTask?.let { pendingTask ->
        handlePendingResultTask(activity, pendingTask, navigateToHomeScreen)
    } ?: handleSignInFlow(activity, firebaseAuth, provider, navigateToHomeScreen)
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

            Timber.i("username: $username, accessToken: $accessToken")
            navigateToHomeScreen()
        }
        ?.addOnFailureListener { exception ->
            activity.showToast("Something Went Wrong: ${exception.message}", true)
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

            Timber.i("username: $username, accessToken: $accessToken")
            navigateToHomeScreen()
        }
        .addOnFailureListener { exception ->
            activity.showToast("Something Went Wrong: ${exception.message}", true)
        }
}

@Composable
fun getTermsAndConditionsString(): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(if (isSystemInDarkTheme()) lightTextColor else Color.Black)) {
            append(stringResource(R.string.by_sigining_in))
        }
        withStyle(
            style = SpanStyle(
                color = linkTextColor,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(stringResource(R.string.terms_of_use))
        }

        withStyle(style = SpanStyle(if (isSystemInDarkTheme()) lightTextColor else Color.Black)) {
            append(stringResource(R.string.and))
        }

        withStyle(
            style = SpanStyle(
                color = linkTextColor,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(stringResource(R.string.privacy_policy))
        }

        withStyle(style = SpanStyle(if (isSystemInDarkTheme()) lightTextColor else Color.Black)) {
            append(stringResource(R.string.period))
        }
    }
}
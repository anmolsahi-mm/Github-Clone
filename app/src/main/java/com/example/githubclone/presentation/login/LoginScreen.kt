package com.example.githubclone.presentation.login

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubclone.R
import com.example.githubclone.presentation.common.getTermsAndConditionsString
import com.example.githubclone.presentation.common.signIn
import com.example.githubclone.ui.theme.SignInButtonColor
import com.example.githubclone.ui.theme.darkB

@Composable
fun LoginScreen(
    navigateToHomeScreen: () -> Unit,
) {
    val activity = LocalContext.current as Activity
    val showLoader = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) darkB else Color.White),
    ) {
        Spacer(modifier = Modifier.weight(.5f))
        Icon(
            painter = painterResource(
                id = if (isSystemInDarkTheme()) R.drawable.github_logo_dark
                else R.drawable.github_logo_light
            ),
            contentDescription = stringResource(R.string.github_logo),
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterHorizontally)
                .weight(1.5f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    showLoader.value = true
                    signIn(activity = activity, navigateToHomeScreen = navigateToHomeScreen)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.SignInButtonColor)
            ) {
                if (showLoader.value) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(20.dp),
                        strokeWidth = 2.dp,
                        color = if (isSystemInDarkTheme()) Color.Black else Color.White
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(R.string.sign_in_with_github),
                        color = if (isSystemInDarkTheme()) Color.Black else Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(4.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle2,
                text = getTermsAndConditionsString()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen {}
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreviewDark() {
    LoginScreen {}
}
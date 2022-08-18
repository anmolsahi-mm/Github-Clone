package com.example.githubclone.presentation.loginscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubclone.R
import com.example.githubclone.ui.theme.EnterpriseButtonColor
import com.example.githubclone.ui.theme.SignInButtonColor

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color(0xFF050505) else Color.White),
    ) {
        Spacer(modifier = Modifier.weight(.5f))
        Icon(
            painter = painterResource(
                id = if (isSystemInDarkTheme()) R.drawable.github_logo_dark
                else R.drawable.github_logo_light
            ),
            contentDescription = stringResource(R.string.github_logo),
            modifier = Modifier
                .size(82.dp)
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
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.SignInButtonColor)
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = stringResource(R.string.sign_in_with_github),
                    color = if (isSystemInDarkTheme()) Color.Black else Color.White
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.EnterpriseButtonColor)
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = stringResource(R.string.sign_in_with_github_enterprise),
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(4.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle2,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(if (isSystemInDarkTheme()) Color(0xFFbdbfc7) else Color.Black)) {
                        append(stringResource(R.string.by_sigining_in))
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0XFF2e8fff),
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(stringResource(R.string.terms_of_use))
                    }

                    withStyle(style = SpanStyle(if (isSystemInDarkTheme()) Color(0xFFbdbfc7) else Color.Black)) {
                        append(stringResource(R.string.and))
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color(0XFF2e8fff),
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(stringResource(R.string.privacy_policy))
                    }

                    withStyle(style = SpanStyle(if (isSystemInDarkTheme()) Color(0xFFbdbfc7) else Color.Black)) {
                        append(stringResource(R.string.period))
                    }
                }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreviewDark() {
    LoginScreen()
}
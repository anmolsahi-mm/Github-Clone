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
import androidx.navigation.compose.rememberNavController
import com.example.githubclone.navigation.NavGraph
import com.example.githubclone.presentation.loginscreen.LoginScreen
import com.example.githubclone.ui.theme.GithubCloneTheme
import com.google.firebase.auth.FirebaseUser


class MainActivity : ComponentActivity() {


    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        setContent {
            GithubCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }

            }
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
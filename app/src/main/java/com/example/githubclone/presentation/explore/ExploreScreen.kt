package com.example.githubclone.presentation.explore

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.githubclone.navigation.Screen

@Composable
fun ExploreScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {

            Row(Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Discover",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ListCard(icon = null, title = "Trending Repositories"){}
            ListCard(icon = null, title = "Awesome Lists"){}
            ListCard(icon = null, title = "Scan QR"){
                navController.navigate(Screen.QRScannerScreen.route)
            }
        }
    }
}

@Composable
fun ListCard(@DrawableRes icon: Int?, title: String, onListCardClick: () -> Unit) {
    Row(modifier = Modifier
        .clickable { onListCardClick() }
        .fillMaxWidth()
        .padding(8.dp)) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Card icon")
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 16.sp)
    }
}
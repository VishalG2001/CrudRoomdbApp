package com.crudapp.ui.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.crudapp.ui.all_posts.CenteredTopAppBar

@Composable
fun EditCreatePostScreen(
    vm: EditCreatePostViewModel = hiltViewModel(),
    navController: NavController
) {
    val title by vm.title.collectAsState()
    val content by vm.content.collectAsState()
    val authorName by vm.authorName.collectAsState()
    val error by vm.errorMessage.collectAsState()
    val success by vm.success.collectAsState()

    LaunchedEffect(success) {
        if (success) {
            navController.popBackStack()
            vm.resetSuccess()
        }
    }

    Scaffold(topBar = { CenteredTopAppBar(if (vm.existingId == null) "CREATE POST" else "EDIT POST")

    }) { padding ->

        Column(modifier = Modifier.padding(padding).padding(16.dp)) {

            Text(
                text = "Post Title",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            OutlinedTextField(
                value = title,
                onValueChange = { vm.title.value = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Single post title") }
            )
            Spacer(Modifier.height(16.dp))

            Text(
                text = "Description",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            OutlinedTextField(
                value = content,
                onValueChange = { vm.content.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { Text("Write your description here...") }
            )
            Spacer(Modifier.height(16.dp))

            Text(
                text = "Author",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            OutlinedTextField(
                value = authorName,
                onValueChange = { vm.authorName.value = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Author Name") }
            )
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { vm.validateAndSave() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0177D3)
                )
            ) {
                Text(
                    text = if (vm.existingId == null) "SUBMIT" else "SAVE CHANGES",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            error?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

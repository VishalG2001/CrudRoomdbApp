package com.crudapp.ui.post_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.crudapp.R
import com.crudapp.ui.all_posts.CenteredTopAppBar
import com.crudapp.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ViewPostScreen(
    vm: PostDetailViewModel = hiltViewModel(),
    navController: NavController
) {

    Scaffold(topBar = { CenteredTopAppBar("ALL POSTS") }) { padding ->


        val post by vm.post.collectAsState()

        var showDeleteConfirm by remember { mutableStateOf(false) }

        post?.let { p ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(vertical = 16.dp, horizontal = 20.dp)
            ) {
                Text("Post Title", style = MaterialTheme.typography.titleSmall)
                Text(p.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(14.dp))
                Text("Post Content", style = MaterialTheme.typography.titleSmall)
                Text(p.content)
                Spacer(Modifier.height(16.dp))
                Text("By Author", style = MaterialTheme.typography.titleSmall)
                Text(p.authorName, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(16.dp))
                Text("Date", style = MaterialTheme.typography.titleSmall)
                Text(formatDate(p.updatedAt), style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {vm.onUpvote()}, modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ThumbUp,
                            contentDescription = "Upvote",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "${p.likes}", style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.width(16.dp))

                    IconButton(
                        onClick = {vm.onDownvote()}, modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_thumb_down),
                            contentDescription = "Downvote",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "${p.dislikes}", style = MaterialTheme.typography.bodyLarge
                    )

                }

                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { navController.navigate("edit_post/${p.id}") },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(width = 1.5.dp, color = Color(0xFF0177D3)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Edit",
                                tint = Color(0xFF0177D3)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Edit", color = Color(0xFF0177D3))
                        }
                    }
                    Spacer(Modifier.width(20.dp))
                    OutlinedButton(
                        border = BorderStroke(width = 1.5.dp, color = Red),
                        onClick = { showDeleteConfirm = true },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Delete",
                                tint = Red
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Delete", color = Red)
                        }
                    }
                }
            }
        } ?: run {
            Text("Post not found", modifier = Modifier.padding(16.dp))
        }

        if (showDeleteConfirm) {
            CommonDialog(
                onCancel ={ showDeleteConfirm = false},
                onProceed = { showDeleteConfirm = false
                    vm.deletePost(
                        onSuccess = { navController.navigateUp() },
                        onError = { /* show toast */ }
                    )
                }
            )
        }
    }
}


@Composable
fun CommonDialog(
    onCancel: () -> Unit,
    onProceed: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            tonalElevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 26.dp, bottom = 0.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Are you sure?",
                        style = Typography.headlineSmall,
                        color = Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Are you sure you want to delete the post?",
                        style = Typography.titleMedium,
                        color = DarkGray,
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                    Spacer(Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(
                            onClick = { onProceed() },
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(width = 1.5.dp, color = Color(0xFF0177D3)),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "YES",
                                    tint = Color(0xFF0177D3)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text("YES", color = Color(0xFF0177D3))
                            }
                        }
                        OutlinedButton(
                            onClick = { onCancel() },
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(width = 1.5.dp, color = Gray),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "CANCEL",
                                    tint = Gray
                                )
                                Spacer(Modifier.width(8.dp))
                                Text("CANCEL", color = Gray)
                            }
                        }
                    }
                Spacer(Modifier.height(20.dp))
                }
            }
        }
    }

}

fun formatDate(millis: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return sdf.format(Date(millis))
}
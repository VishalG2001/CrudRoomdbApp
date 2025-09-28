package com.crudapp.ui.all_posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crudapp.R
import com.crudapp.data.local.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllPostScreen(
    vm: AllPostViewModel = hiltViewModel(), onOpenPost: (Long) -> Unit, onCreate: () -> Unit
) {
    val posts by vm.posts.collectAsState()

    Scaffold(topBar = { CenteredTopAppBar("ALL POSTS") }, floatingActionButton = {
        FloatingActionButton(
            onClick = onCreate,
            containerColor = Color(0xFF0177D3),
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(Icons.Default.Add, contentDescription = "Create")
        }

    }) { padding ->


        LazyColumn(modifier = Modifier.padding(padding)) {
            items(posts) { post ->
                PostItem(
                    post = post,
                    onClick = { onOpenPost(post.id) },
                    onUpvote = { vm.onUpvote(post) },
                    onDownvote = { vm.onDownvote(post) })
            }
        }
    }
}

@Composable
fun PostItem(
    post: Post,
    onClick: () -> Unit,
    onUpvote: () -> Unit,
    onDownvote: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        colors = CardDefaults.cardColors(White),
        shape = MaterialTheme.shapes.medium, // Default rounded corners
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Subtle shadow
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp) // Inner padding for content
                .clickable(onClick = onClick) // Make the whole content area clickable
        ) {
            // 1. Post Title (Bold and larger, as in the image)
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // 2. Post Summary (from content, limited and with ellipsis)
            Text(
                text = post.content, // Use full content here; `maxLines` and `overflow` handle truncation
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2, // Limit summary to 2 lines as per the image
                overflow = TextOverflow.Ellipsis, // Add ellipsis if text is cut off
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 3. Likes/Dislikes and Read More Button (arranged with SpaceBetween)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Pushes items to ends
            ) {
                // Likes and Dislikes Group
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Upvote/Likes
                    // Using IconButton for better touch target and ripple effect
                    IconButton(
                        onClick = onUpvote, modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ThumbUp,
                            contentDescription = "Upvote",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "${post.likes}", style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.width(16.dp))

                    // Downvote/Dislikes
                    IconButton(
                        onClick = onDownvote, modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_thumb_down),
                            contentDescription = "Downvote",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "${post.dislikes}", style = MaterialTheme.typography.bodyLarge
                    )

                }

                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0177D3)
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 28.dp, vertical = 8.dp
                    ) // Adjust padding
                ) {
                    Text("READ MORE")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredTopAppBar(title: String) {
    CenterAlignedTopAppBar(
        title = {
            Text(title)
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF0177D3), titleContentColor = Color.White
        )
    )
}
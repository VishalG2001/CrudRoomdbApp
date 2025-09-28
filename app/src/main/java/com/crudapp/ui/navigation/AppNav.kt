package com.crudapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.hilt.navigation.compose.hiltViewModel
import com.crudapp.ui.all_posts.AllPostScreen
import com.crudapp.ui.all_posts.AllPostViewModel
import com.crudapp.ui.edit.EditCreatePostScreen
import com.crudapp.ui.edit.EditCreatePostViewModel
import com.crudapp.ui.post_details.PostDetailViewModel
import com.crudapp.ui.post_details.ViewPostScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = Routes.ALL) {

        composable(Routes.ALL) { backStackEntry ->
            val vm: AllPostViewModel = hiltViewModel(backStackEntry)
            AllPostScreen(
                vm = vm,
                onOpenPost = { id -> navController.navigate("view_post/$id") },
                onCreate = { navController.navigate(Routes.CREATE) }
            )
        }

        composable(
            "view_post/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.LongType })
        ) { backStackEntry ->
            val vm: PostDetailViewModel = hiltViewModel(backStackEntry)
            ViewPostScreen(vm = vm, navController = navController)
        }

        composable(Routes.CREATE) { backStackEntry ->
            val vm: EditCreatePostViewModel = hiltViewModel(backStackEntry)
            EditCreatePostScreen(vm = vm, navController = navController)
        }

        composable(
            "edit_post/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.LongType })
        ) { backStackEntry ->
            val vm: EditCreatePostViewModel = hiltViewModel(backStackEntry)
            EditCreatePostScreen(vm = vm, navController = navController)
        }
    }
}

object Routes {
    const val ALL = "all_posts"
    const val VIEW = "view_post/{postId}"
    const val VIEW_WITH_ID = "view_post/"
    const val CREATE = "create_post"
    const val EDIT = "edit_post/{postId}"
}

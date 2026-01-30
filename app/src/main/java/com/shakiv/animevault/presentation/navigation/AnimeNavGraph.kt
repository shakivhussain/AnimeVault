package com.shakiv.animevault.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shakiv.animevault.presentation.common.Screen
import com.shakiv.animevault.presentation.detail.AnimeDetailScreen
import com.shakiv.animevault.presentation.list.AnimeListScreen

@Composable
fun AnimeNavGraph() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.AnimeList.route,

            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(500, easing = EaseInOutQuart)
                ) + fadeIn(animationSpec = tween(500))
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(500, easing = EaseInOutQuart)
                ) + fadeOut(animationSpec = tween(500))
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(500, easing = EaseInOutQuart)
                ) + fadeIn(animationSpec = tween(500))
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(500, easing = EaseInOutQuart)
                ) + fadeOut(animationSpec = tween(500))
            }

        ) {
            composable(route = Screen.AnimeList.route) {
                AnimeListScreen(
                    onAnimeClick = { animeId ->
                        navController.navigate(Screen.AnimeDetail.createRoute(animeId))
                    }
                )
            }

            composable(
                route = Screen.AnimeDetail.route,
                arguments = listOf(
                    navArgument(Screen.ARG_ANIME_ID) {
                        type = NavType.IntType
                    }
                )
            ) {
                AnimeDetailScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
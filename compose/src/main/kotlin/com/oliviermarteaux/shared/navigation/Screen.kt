package com.oliviermarteaux.shared.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.oliviermarteaux.shared.compose.R

/**
 * A sealed class that represents the different screens in the application.
 *
 * @property route The route for the screen.
 * @property navArguments The navigation arguments for the screen.
 */
sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList(),
    val routeWithArgs: String = "",
    val titleRes: Int = -1,
) {
    /**
     * The splash screen.
     */
    data object Splash : Screen("splash")

    /**
     * The login screen.
     */
    data object Login : Screen("login")

    /**
     * The password screen.
     */
    data object Password : Screen(
        route = "password",
        navArguments = listOf(navArgument("email") { type = NavType.StringType }),
        routeWithArgs = "password/{email}"
    )
    /**
     * The reset screen.
     */
    data object Reset : Screen(
        route = "reset",
        navArguments = listOf(navArgument("email") { type = NavType.StringType }),
        routeWithArgs = "reset/{email}"
    )

    /**
     * The home screen.
     */
    data object Home : Screen(
        route = "home",
        titleRes = R.string.event_list
    )

    /**
     * The detail screen.
     */
    data object Detail : Screen(
        route = "detail",
        titleRes = R.string.user_profile,
        navArguments = listOf(navArgument("post_id") { type = NavType.IntType }),
        routeWithArgs = "detail/{post_id}"
    )

    /**
     * The add screen.
     */
    data object Add : Screen(
        route = "add",
        titleRes = R.string.creation_of_an_event,
        navArguments = listOf(navArgument("photo_url") { type = NavType.StringType }),
        routeWithArgs = "add/{photo_url}"
    )

    /**
     * The account screen.
     */
    data object Account : Screen(
        route = "account",
        titleRes = R.string.user_profile
    )
    /**
     * The camera screen.
     */
    data object Camera : Screen(
        route = "camera",
        titleRes = R.string.camera
    )
}
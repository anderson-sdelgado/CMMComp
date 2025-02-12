package br.com.usinasantafe.cmm.presenter

import InitialMenuScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.usinasantafe.cmm.presenter.Routes.INITIAL_MENU_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.PASSWORD_ROUTE
import br.com.usinasantafe.cmm.presenter.configuration.password.PasswordScreen

@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = INITIAL_MENU_ROUTE,
    navActions: NavigationActions = remember(navHostController) {
        NavigationActions(navHostController)
    }
) {

    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {

        ///////////////////////// Config //////////////////////////////////

        composable(INITIAL_MENU_ROUTE) {
            InitialMenuScreen(
                onNavPassword = {
                    navActions.navigateToPassword()
                }
            )
        }

        composable(PASSWORD_ROUTE) {
            PasswordScreen(
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                },
                onNavConfig = {}
            )
        }

        ////////////////////////////////////////////////////////////////////

    }

}
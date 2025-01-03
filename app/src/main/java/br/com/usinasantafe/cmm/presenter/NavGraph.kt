package br.com.usinasantafe.cmm.presenter

import InitialMenuScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.usinasantafe.cmm.presenter.Routes.INITIAL_TEST_ROUTE

@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = INITIAL_TEST_ROUTE,
    navActions: NavigationActions = remember(navHostController) {
        NavigationActions(navHostController)
    }
) {

    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(INITIAL_TEST_ROUTE) {
            InitialMenuScreen()
        }
    }

}
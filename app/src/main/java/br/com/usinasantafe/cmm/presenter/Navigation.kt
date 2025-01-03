package br.com.usinasantafe.cmm.presenter

import androidx.navigation.NavHostController
import br.com.usinasantafe.cmm.presenter.Screens.INITIAL_MENU_SCREEN

object Screens {
    const val INITIAL_MENU_SCREEN = "initialMenuScreen"
}

object Args {

}

object Routes {
    const val INITIAL_TEST_ROUTE = INITIAL_MENU_SCREEN
}

class NavigationActions(private val navController: NavHostController) {

    fun navigateToInitialMenu() {
        navController.navigate(Routes.INITIAL_TEST_ROUTE)
    }

}
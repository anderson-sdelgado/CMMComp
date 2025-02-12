package br.com.usinasantafe.cmm.presenter

import androidx.navigation.NavHostController
import br.com.usinasantafe.cmm.presenter.Screens.INITIAL_MENU_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.PASSWORD_SCREEN

object Screens {
    const val INITIAL_MENU_SCREEN = "initialMenuScreen"
    const val PASSWORD_SCREEN = "passwordScreen"
}

object Args {

}

object Routes {
    const val INITIAL_MENU_ROUTE = INITIAL_MENU_SCREEN
    const val PASSWORD_ROUTE = PASSWORD_SCREEN
}

class NavigationActions(private val navController: NavHostController) {

    ///////////////////////// Config //////////////////////////////////

    fun navigateToPassword() {
        navController.navigate(PASSWORD_SCREEN)
    }

    fun navigateToInitialMenu() {
        navController.navigate(Routes.INITIAL_MENU_ROUTE)
    }

    ////////////////////////////////////////////////////////////////////

}
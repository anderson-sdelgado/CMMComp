package br.com.usinasantafe.cmm.presenter

import androidx.navigation.NavHostController
import br.com.usinasantafe.cmm.presenter.Routes.ACTIVITY_LIST_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.EQUIP_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.OPERATOR_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.OS_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.TURN_LIST_ROUTE
import br.com.usinasantafe.cmm.presenter.Screens.ACTIVITY_LIST_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.CONFIG_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.EQUIP_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.INITIAL_MENU_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.OPERATOR_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.OS_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.PASSWORD_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.TURN_LIST_SCREEN

object Screens {
    const val INITIAL_MENU_SCREEN = "initialMenuScreen"
    const val PASSWORD_SCREEN = "passwordScreen"
    const val CONFIG_SCREEN = "configScreen"
    const val OPERATOR_SCREEN = "operatorScreen"
    const val EQUIP_SCREEN = "equipScreen"
    const val TURN_LIST_SCREEN = "turnListScreen"
    const val OS_SCREEN = "osScreen"
    const val ACTIVITY_LIST_SCREEN = "activityListScreen"
}

object Args {

}

object Routes {
    const val INITIAL_MENU_ROUTE = INITIAL_MENU_SCREEN
    const val PASSWORD_ROUTE = PASSWORD_SCREEN
    const val CONFIG_ROUTE = CONFIG_SCREEN
    const val OPERATOR_ROUTE = OPERATOR_SCREEN
    const val EQUIP_ROUTE = EQUIP_SCREEN
    const val TURN_LIST_ROUTE = TURN_LIST_SCREEN
    const val OS_ROUTE = OS_SCREEN
    const val ACTIVITY_LIST_ROUTE = ACTIVITY_LIST_SCREEN
}

class NavigationActions(private val navController: NavHostController) {

    ///////////////////////// Config //////////////////////////////////

    fun navigateToPassword() {
        navController.navigate(PASSWORD_SCREEN)
    }

    fun navigateToInitialMenu() {
        navController.navigate(Routes.INITIAL_MENU_ROUTE)
    }

    fun navigateToConfig() {
        navController.navigate(Routes.CONFIG_ROUTE)
    }

    ////////////////////////////////////////////////////////////////////

    ////////////////////////// Header //////////////////////////////////

    fun navigateToOperator() {
        navController.navigate(OPERATOR_ROUTE)
    }

    fun navigateToEquip() {
        navController.navigate(EQUIP_ROUTE)
    }

    fun navigateToTurnList() {
        navController.navigate(TURN_LIST_ROUTE)
    }

    fun navigateToOS() {
        navController.navigate(OS_ROUTE)
    }

    fun navigateToActivityList() {
        navController.navigate(ACTIVITY_LIST_ROUTE)
    }

    ////////////////////////////////////////////////////////////////////

}
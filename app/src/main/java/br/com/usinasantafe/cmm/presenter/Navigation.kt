package br.com.usinasantafe.cmm.presenter

import androidx.navigation.NavHostController
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.presenter.Screens.ACTIVITY_LIST_COMMON_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.CONFIG_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.EQUIP_HEADER_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.INITIAL_MENU_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.HOUR_METER_HEADER_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.ITEM_CHECK_LIST_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.MENU_NOTE_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.OPERATOR_HEADER_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.OS_COMMON_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.PASSWORD_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.QUESTION_UPDATE_CHECK_LIST_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.SPLASH_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.STOP_LIST_NOTE_SCREEN
import br.com.usinasantafe.cmm.presenter.Screens.TURN_LIST_HEADER_SCREEN
import br.com.usinasantafe.cmm.lib.FlowApp

object Screens {
    const val SPLASH_SCREEN = "splash"
    const val INITIAL_MENU_SCREEN = "initialMenuScreen"
    const val PASSWORD_SCREEN = "passwordScreen"
    const val CONFIG_SCREEN = "configScreen"
    const val OPERATOR_HEADER_SCREEN = "operatorHeaderScreen"
    const val EQUIP_HEADER_SCREEN = "equipHeaderScreen"
    const val TURN_LIST_HEADER_SCREEN = "turnListHeaderScreen"
    const val OS_COMMON_SCREEN = "osCommonScreen"
    const val ACTIVITY_LIST_COMMON_SCREEN = "activityListCommonScreen"
    const val HOUR_METER_HEADER_SCREEN = "hourMeterHeaderScreen"
    const val MENU_NOTE_SCREEN = "menuNoteScreen"
    const val STOP_LIST_NOTE_SCREEN = "stopListNoteScreen"
    const val QUESTION_UPDATE_CHECK_LIST_SCREEN = "questionUpdateCheckListScreen"
    const val ITEM_CHECK_LIST_SCREEN = "itemCheckListScreen"
}

object Args {
    const val FLOW_APP_ARG = "flowApp"
}

object Routes {
    const val SPLASH_ROUTE = SPLASH_SCREEN
    const val INITIAL_MENU_ROUTE = INITIAL_MENU_SCREEN
    const val PASSWORD_ROUTE = PASSWORD_SCREEN
    const val CONFIG_ROUTE = CONFIG_SCREEN
    const val OPERATOR_HEADER_ROUTE = OPERATOR_HEADER_SCREEN
    const val EQUIP_HEADER_ROUTE = EQUIP_HEADER_SCREEN
    const val TURN_LIST_HEADER_ROUTE = TURN_LIST_HEADER_SCREEN
    const val OS_COMMON_ROUTE = "$OS_COMMON_SCREEN/{$FLOW_APP_ARG}"
    const val ACTIVITY_LIST_COMMON_ROUTE = "$ACTIVITY_LIST_COMMON_SCREEN/{$FLOW_APP_ARG}"
    const val HOUR_METER_HEADER_ROUTE = "$HOUR_METER_HEADER_SCREEN/{$FLOW_APP_ARG}"
    const val MENU_NOTE_ROUTE = MENU_NOTE_SCREEN
    const val STOP_LIST_NOTE_ROUTE = STOP_LIST_NOTE_SCREEN
    const val QUESTION_UPDATE_CHECK_LIST_ROUTE = QUESTION_UPDATE_CHECK_LIST_SCREEN
    const val ITEM_CHECK_LIST_ROUTE = ITEM_CHECK_LIST_SCREEN
}

class NavigationActions(private val navController: NavHostController) {

    ///////////////////////// Splash //////////////////////////////////

    fun navigateToSplash() {
        navController.navigate(SPLASH_SCREEN)
    }

    ////////////////////////////////////////////////////////////////////

    ///////////////////////// Config //////////////////////////////////

    fun navigateToPassword() {
        navController.navigate(PASSWORD_SCREEN)
    }

    fun navigateToInitialMenu() {
        navController.navigate(INITIAL_MENU_SCREEN)
    }

    fun navigateToConfig() {
        navController.navigate(CONFIG_SCREEN)
    }

    ////////////////////////////////////////////////////////////////////

    ////////////////////////// Common //////////////////////////////////

    fun navigateToOSCommon(
        flowApp: Int = FlowApp.HEADER_INITIAL.ordinal
    ) {
        navController.navigate("${OS_COMMON_SCREEN}/${flowApp}")
    }

    fun navigateToActivityListCommon(
        flowApp: Int = FlowApp.HEADER_INITIAL.ordinal
    ) {
        navController.navigate("${ACTIVITY_LIST_COMMON_SCREEN}/${flowApp}")
    }

    ////////////////////////////////////////////////////////////////////

    ////////////////////////// Header //////////////////////////////////

    fun navigateToOperatorHeader() {
        navController.navigate(OPERATOR_HEADER_SCREEN)
    }

    fun navigateToEquipHeader() {
        navController.navigate(EQUIP_HEADER_SCREEN)
    }

    fun navigateToTurnListHeader() {
        navController.navigate(TURN_LIST_HEADER_SCREEN)
    }

    fun navigateToMeasureHeader(
        flowApp: Int = FlowApp.HEADER_INITIAL.ordinal
    ) {
        navController.navigate("${HOUR_METER_HEADER_SCREEN}/${flowApp}")
    }

    ////////////////////////////////////////////////////////////////////

    /////////////////////////// Note ///////////////////////////////////

    fun navigateToMenuNote() {
        navController.navigate(MENU_NOTE_SCREEN)
    }

    fun navigateToStopListNote() {
        navController.navigate(STOP_LIST_NOTE_SCREEN)
    }

    ////////////////////////////////////////////////////////////////////

    ///////////////////////// Check List ///////////////////////////////

    fun navigateToQuestionUpdateCheckList() {
        navController.navigate(QUESTION_UPDATE_CHECK_LIST_SCREEN)
    }

    fun navigateToItemCheckList() {
        navController.navigate(ITEM_CHECK_LIST_SCREEN)
    }

    ////////////////////////////////////////////////////////////////////

}
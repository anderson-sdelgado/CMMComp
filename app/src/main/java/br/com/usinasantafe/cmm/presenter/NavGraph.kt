package br.com.usinasantafe.cmm.presenter

import InitialMenuScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.usinasantafe.cmm.presenter.Routes.ACTIVITY_LIST_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.CONFIG_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.EQUIP_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.INITIAL_MENU_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.MEASURE_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.OPERATOR_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.OS_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.PASSWORD_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.TURN_LIST_ROUTE
import br.com.usinasantafe.cmm.presenter.configuration.config.ConfigScreen
import br.com.usinasantafe.cmm.presenter.configuration.password.PasswordScreen
import br.com.usinasantafe.cmm.presenter.header.activityList.ActivityListScreen
import br.com.usinasantafe.cmm.presenter.header.equip.EquipScreen
import br.com.usinasantafe.cmm.presenter.header.measure.MeasureScreen
import br.com.usinasantafe.cmm.presenter.header.operator.OperatorScreen
import br.com.usinasantafe.cmm.presenter.header.os.OSScreen
import br.com.usinasantafe.cmm.presenter.header.turnlist.TurnListScreen

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
                },
                onNavOperator = {
                    navActions.navigateToOperator()
                }
            )
        }

        composable(PASSWORD_ROUTE) {
            PasswordScreen(
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                },
                onNavConfig = {
                    navActions.navigateToConfig()
                }
            )
        }

        composable(CONFIG_ROUTE) {
            ConfigScreen(
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                }
            )
        }

        ////////////////////////////////////////////////////////////////////

        ////////////////////////// Header //////////////////////////////////

        composable(OPERATOR_ROUTE) {
            OperatorScreen(
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                },
                onNavEquip = {
                    navActions.navigateToEquip()
                }
            )
        }

        composable(EQUIP_ROUTE) {
            EquipScreen(
                onNavOperator = {
                    navActions.navigateToOperator()
                },
                onNavTurnList = {
                    navActions.navigateToTurnList()
                }
            )
        }

        composable(TURN_LIST_ROUTE) {
            TurnListScreen(
                onNavEquip = {
                    navActions.navigateToEquip()
                },
                onNavOS = {
                    navActions.navigateToOS()
                }
            )
        }

        composable(OS_ROUTE) {
            OSScreen(
                onNavTurn = {
                    navActions.navigateToTurnList()
                },
                onNavActivityList = {
                    navActions.navigateToActivityList()
                }
            )
        }

        composable(ACTIVITY_LIST_ROUTE) {
            ActivityListScreen(
                onNavOS = {
                    navActions.navigateToOS()
                },
                onNavMeasure = {
                    navActions.navigateToMeasure()
                }
            )
        }

        composable(MEASURE_ROUTE) {
            MeasureScreen(
                onNavActivityList = {
                    navActions.navigateToActivityList()
                }
            )
        }

        ////////////////////////////////////////////////////////////////////

    }

}
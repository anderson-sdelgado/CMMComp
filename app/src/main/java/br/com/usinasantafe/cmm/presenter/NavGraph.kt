package br.com.usinasantafe.cmm.presenter

import InitialMenuScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.cmm.presenter.Routes.ACTIVITY_LIST_COMMON_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.CONFIG_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.EQUIP_HEADER_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.INITIAL_MENU_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.MEASURE_HEADER_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.MENU_NOTE_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.OPERATOR_HEADER_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.OS_COMMON_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.PASSWORD_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.SPLASH_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.STOP_LIST_NOTE_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.TURN_LIST_HEADER_ROUTE
import br.com.usinasantafe.cmm.presenter.configuration.config.ConfigScreen
import br.com.usinasantafe.cmm.presenter.configuration.password.PasswordScreen
import br.com.usinasantafe.cmm.presenter.common.activityList.ActivityListCommonScreen
import br.com.usinasantafe.cmm.presenter.header.equip.EquipHeaderScreen
import br.com.usinasantafe.cmm.presenter.header.measure.MeasureHeaderScreen
import br.com.usinasantafe.cmm.presenter.header.operator.OperatorHeaderScreen
import br.com.usinasantafe.cmm.presenter.common.os.OSCommonScreen
import br.com.usinasantafe.cmm.presenter.header.turnlist.TurnListHeaderScreen
import br.com.usinasantafe.cmm.presenter.note.menu.MenuNoteScreen
import br.com.usinasantafe.cmm.presenter.splash.SplashScreen
import br.com.usinasantafe.cmm.utils.FlowApp

@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = SPLASH_ROUTE,
    navActions: NavigationActions = remember(navHostController) {
        NavigationActions(navHostController)
    }
) {

    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {


        ///////////////////////// Splash //////////////////////////////////

        composable(SPLASH_ROUTE) {
            SplashScreen(
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                },
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                }
            )
        }

        ////////////////////////////////////////////////////////////////////

        ///////////////////////// Config //////////////////////////////////

        composable(INITIAL_MENU_ROUTE) {
            InitialMenuScreen(
                onNavPassword = {
                    navActions.navigateToPassword()
                },
                onNavOperator = {
                    navActions.navigateToOperatorHeader()
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

        composable(OPERATOR_HEADER_ROUTE) {
            OperatorHeaderScreen(
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                },
                onNavEquip = {
                    navActions.navigateToEquipHeader()
                }
            )
        }

        composable(EQUIP_HEADER_ROUTE) {
            EquipHeaderScreen(
                onNavOperator = {
                    navActions.navigateToOperatorHeader()
                },
                onNavTurnList = {
                    navActions.navigateToTurnListHeader()
                }
            )
        }

        composable(TURN_LIST_HEADER_ROUTE) {
            TurnListHeaderScreen(
                onNavEquip = {
                    navActions.navigateToEquipHeader()
                },
                onNavOS = {
                    navActions.navigateToOSCommon()
                }
            )
        }

        composable(
            OS_COMMON_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            OSCommonScreen(
                onNavTurn = {
                    navActions.navigateToTurnListHeader()
                },
                onNavActivityList = {
                    navActions.navigateToActivityListCommon(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                    )
                },
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                }
            )
        }

        composable(
            ACTIVITY_LIST_COMMON_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            ActivityListCommonScreen(
                onNavOS = {
                    navActions.navigateToOSCommon(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!
                    )
                },
                onNavMeasure = {
                    navActions.navigateToMeasureHeader()
                },
                onNavStopList = {
                    navActions.navigateToStopListNote()
                },
                onMenuNote = {
                    navActions.navigateToMenuNote()
                }
            )
        }

        composable(MEASURE_HEADER_ROUTE) {
            MeasureHeaderScreen(
                onNavActivityList = {
                    navActions.navigateToActivityListCommon()
                },
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                }
            )
        }

        ////////////////////////////////////////////////////////////////////

        /////////////////////////// Note ///////////////////////////////////

        composable(MENU_NOTE_ROUTE) {
            MenuNoteScreen(
                onNavOS = {
                    navActions.navigateToOSCommon(
                        flowApp = FlowApp.NOTE_WORK.ordinal
                    )
                },
                onNavActivityList = {
                    navActions.navigateToActivityListCommon(
                        flowApp = FlowApp.NOTE_STOP.ordinal
                    )
                }
            )
        }

        composable(STOP_LIST_NOTE_ROUTE) {
        }

        ////////////////////////////////////////////////////////////////////

    }

}
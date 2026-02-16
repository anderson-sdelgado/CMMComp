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
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.presenter.Routes.ACTIVITY_LIST_COMMON_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.CONFIG_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.EQUIP_HEADER_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.INITIAL_MENU_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.HOUR_METER_HEADER_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.ITEM_CHECK_LIST_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.MENU_NOTE_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.OPERATOR_HEADER_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.OS_COMMON_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.PASSWORD_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.QUESTION_UPDATE_CHECK_LIST_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.SPLASH_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.STOP_LIST_NOTE_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.TURN_LIST_HEADER_ROUTE
import br.com.usinasantafe.cmm.presenter.view.checkList.itemCheckList.ItemCheckListScreen
import br.com.usinasantafe.cmm.presenter.view.checkList.questionUpdate.QuestionUpdateCheckListScreen
import br.com.usinasantafe.cmm.presenter.view.configuration.config.ConfigScreen
import br.com.usinasantafe.cmm.presenter.view.configuration.password.PasswordScreen
import br.com.usinasantafe.cmm.presenter.view.motomec.common.activityList.ActivityListCommonScreen
import br.com.usinasantafe.cmm.presenter.view.motomec.header.equip.EquipHeaderScreen
import br.com.usinasantafe.cmm.presenter.view.motomec.header.hourMeter.HourMeterHeaderScreen
import br.com.usinasantafe.cmm.presenter.view.motomec.header.operator.OperatorHeaderScreen
import br.com.usinasantafe.cmm.presenter.view.motomec.common.os.OSCommonScreen
import br.com.usinasantafe.cmm.presenter.view.motomec.header.turnList.TurnListHeaderScreen
import br.com.usinasantafe.cmm.presenter.view.motomec.note.menu.MenuNoteScreen
import br.com.usinasantafe.cmm.presenter.view.motomec.note.stopList.StopListNoteScreen
import br.com.usinasantafe.cmm.presenter.view.splash.SplashScreen
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.presenter.Args.ID_ARG
import br.com.usinasantafe.cmm.presenter.Routes.MOTOR_PUMP_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.PERFORMANCE_LIST_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.PERFORMANCE_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.TRANSHIPMENT_NOTE_ROUTE
import br.com.usinasantafe.cmm.presenter.view.fertigation.motorPump.MotorPumpScreen
import br.com.usinasantafe.cmm.presenter.view.transhipment.TranshipmentScreen
import br.com.usinasantafe.cmm.presenter.view.performance.performance.PerformanceScreen
import br.com.usinasantafe.cmm.presenter.view.performance.performancelist.PerformanceListScreen

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
                },
                onNavCheckList = {
                    navActions.navigateToQuestionUpdateCheckList()
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

        //////////////////////////////////////////////////////////////////////

        ////////////////////////// MotoMec //////////////////////////////////

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
                navArgument(FLOW_APP_ARG) { type = NavType.IntType }
            )
        ) { entry ->
            OSCommonScreen(
                onNavTurn = {
                    navActions.navigateToTurnListHeader()
                },
                onNavActivityList = {
                    navActions.navigateToActivityListCommon(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARG)!!,
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
                navArgument(FLOW_APP_ARG) { type = NavType.IntType }
            )
        ) { entry ->
            ActivityListCommonScreen(
                onNavOS = {
                    navActions.navigateToOSCommon(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARG)!!
                    )
                },
                onNavHourMeter = {
                    navActions.navigateToHourMeterHeader()
                },
                onNavStopList = {
                    navActions.navigateToStopListNote()
                },
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                },
                onNavTranshipment = {
                    navActions.navigateToTranshipment(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARG)!!
                    )
                },
                onNavNozzleList = {}
            )
        }

        composable(
            HOUR_METER_HEADER_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARG) { type = NavType.IntType }
            )
        ) {
            HourMeterHeaderScreen(
                onNavActivityList = {
                    navActions.navigateToActivityListCommon()
                },
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                },
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                },
                onNavQuestionUpdateCheckList = {
                    navActions.navigateToQuestionUpdateCheckList()
                },
                onNavMotorPump = {
                    navActions.navigateToMotorPump()
                },
                onNavListPerformance = {
                    navActions.navigatePerformanceList(
                        flowApp = FlowApp.HEADER_FINISH.ordinal
                    )
                }
            )
        }

        composable(MOTOR_PUMP_ROUTE) {
            MotorPumpScreen(
                onNavHourMeter = {
                    navActions.navigateToHourMeterHeader()
                },
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                },
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
                },
                onNavMeasure = {
                    navActions.navigateToHourMeterHeader(
                        flowApp = FlowApp.HEADER_FINISH.ordinal
                    )
                },
                onNavTranshipment = {
                    navActions.navigateToTranshipment(
                        flowApp = FlowApp.TRANSHIPMENT.ordinal
                    )
                },
                onNavPerformanceList = {
                    navActions.navigatePerformanceList(
                        flowApp = FlowApp.PERFORMANCE.ordinal
                    )
                },
                onNavListReel = {},
                onNavTrailer = {},
                onNavFertigationList = {},
                onNavMenuCertificate = {},
                onNavImplement = {},
                onNavOSMechanical = {},
                onNavEquipTire = {},
                onNavInfoLocalSugarcaneLoading = {},
                onNavUncouplingTrailer = {},
                onNavHistory = {},
                onNavProduct = {},
                onNavWill = {},
                onNavInfoLoadingCompound = {}
            )
        }

        composable(STOP_LIST_NOTE_ROUTE) {
            StopListNoteScreen(
                onNavActivityList = {
                    navActions.navigateToActivityListCommon(
                        flowApp = FlowApp.NOTE_STOP.ordinal
                    )
                },
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                }
            )
        }

        composable(
            TRANSHIPMENT_NOTE_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARG) { type = NavType.IntType }
            )
        ) { entry ->
            TranshipmentScreen(
                onNavActivityList = {
                    navActions.navigateToActivityListCommon(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARG)!!
                    )
                },
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                }
            )
        }

        ////////////////////////////////////////////////////////////////////

        //////////////////////// Performance ///////////////////////////////

        composable(
            PERFORMANCE_LIST_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARG) { type = NavType.IntType }
            )
        ){ entry ->
            PerformanceListScreen(
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                },
                onNavPerformance = {
                    navActions.navigatePerformance(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARG)!!,
                        id = it
                    )
                },
                onNavSplash = {
                    navActions.navigateToSplash()
                }
            )
        }

        composable(
            PERFORMANCE_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARG) { type = NavType.IntType },
                navArgument(ID_ARG) { type = NavType.IntType }
            )
        ){ entry ->
            PerformanceScreen(
                onNavPerformanceList = {
                    navActions.navigatePerformanceList(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARG)!!,
                    )
                }
            )
        }

        ////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////

        ///////////////////////// Check List ///////////////////////////////

        composable(QUESTION_UPDATE_CHECK_LIST_ROUTE){
            QuestionUpdateCheckListScreen(
                onNavItemCheckList = {
                    navActions.navigateToItemCheckList()
                }
            )
        }

        composable(ITEM_CHECK_LIST_ROUTE){
            ItemCheckListScreen(
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                }
            )
        }

        ////////////////////////////////////////////////////////////////////

    }

}
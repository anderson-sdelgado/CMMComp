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
import br.com.usinasantafe.cmm.presenter.view.common.activityList.ActivityListCommonScreen
import br.com.usinasantafe.cmm.presenter.view.common.equip.EquipHeaderScreen
import br.com.usinasantafe.cmm.presenter.view.motomec.header.hourMeter.HourMeterHeaderScreen
import br.com.usinasantafe.cmm.presenter.view.motomec.header.operator.OperatorHeaderScreen
import br.com.usinasantafe.cmm.presenter.view.common.os.OSCommonScreen
import br.com.usinasantafe.cmm.presenter.view.motomec.header.turnList.TurnListHeaderScreen
import br.com.usinasantafe.cmm.presenter.view.motomec.note.menu.MenuNoteScreen
import br.com.usinasantafe.cmm.presenter.view.motomec.note.stopList.StopListNoteScreen
import br.com.usinasantafe.cmm.presenter.view.splash.SplashScreen
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.presenter.Args.ID_ARG
import br.com.usinasantafe.cmm.presenter.Routes.COLLECTION_LIST_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.COLLECTION_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.IMPLEMENTATION_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.INPUT_ITEM_MECHANIC_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.ITEM_LIST_MECHANIC_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.MOTOR_PUMP_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.NOZZLE_LIST_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.OS_MECHANIC_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.PERFORMANCE_LIST_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.PERFORMANCE_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.PRESSURE_LIST_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.REEL_LIST_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.REEL_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.SPEED_LIST_ROUTE
import br.com.usinasantafe.cmm.presenter.Routes.TRANSHIPMENT_NOTE_ROUTE
import br.com.usinasantafe.cmm.presenter.view.fertigation.collection.CollectionFertigationScreen
import br.com.usinasantafe.cmm.presenter.view.fertigation.collectionList.CollectionListFertigationScreen
import br.com.usinasantafe.cmm.presenter.view.fertigation.motorPump.MotorPumpFertigationScreen
import br.com.usinasantafe.cmm.presenter.view.fertigation.nozzleList.NozzleListFertigationScreen
import br.com.usinasantafe.cmm.presenter.view.fertigation.pressureList.PressureListFertigationScreen
import br.com.usinasantafe.cmm.presenter.view.fertigation.reel.ReelFertigationScreen
import br.com.usinasantafe.cmm.presenter.view.fertigation.reelList.ReelListFertigationScreen
import br.com.usinasantafe.cmm.presenter.view.fertigation.speedList.SpeedListFertigationScreen
import br.com.usinasantafe.cmm.presenter.view.implement.ImplementScreen
import br.com.usinasantafe.cmm.presenter.view.mechanic.inputItem.InputItemMechanicScreen
import br.com.usinasantafe.cmm.presenter.view.mechanic.itemList.ItemListMechanicScreen
import br.com.usinasantafe.cmm.presenter.view.mechanic.os.OSMechanicScreen
import br.com.usinasantafe.cmm.presenter.view.transhipment.TranshipmentScreen
import br.com.usinasantafe.cmm.presenter.view.performance.performance.PerformanceScreen
import br.com.usinasantafe.cmm.presenter.view.performance.performanceList.PerformanceListScreen

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
                onNavNozzleList = {
                    navActions.navigateToNozzleList()
                },
                onNavReel = {
                    navActions.navigateToReel()
                },
                onNavReelList = {
                    navActions.navigateToReelList()
                }
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
                    navActions.navigateToPerformanceList(
                        flowApp = FlowApp.HEADER_FINISH.ordinal
                    )
                }
            )
        }

        composable(MOTOR_PUMP_ROUTE) {
            MotorPumpFertigationScreen(
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
                onNavHourMeter = {
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
                    navActions.navigateToPerformanceList(
                        flowApp = FlowApp.PERFORMANCE.ordinal
                    )
                },
                onNavListReel = {
                    navActions.navigateToReelList()
                },
                onNavCollectionList = {
                    navActions.navigateToCollectionList(
                        flowApp = FlowApp.COLLECTION.ordinal
                    )
                },
                onNavImplement = {
                    navActions.navigateToImplement()
                },
                onNavOSMechanical = {
                    navActions.navigateToOSMechanic()
                },
                onNavEquipTire = {},
                onNavMenuCertificate = {},
                onNavTrailer = {},
                onNavInfoLocalSugarcaneLoading = {},
                onNavUncouplingTrailer = {},
                onNavProduct = {},
                onNavWill = {},
                onNavInfoLoadingCompound = {},
                onNavHistory = {}
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
                    navActions.navigateToPerformance(
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
                    navActions.navigateToPerformanceList(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARG)!!,
                    )
                }
            )
        }

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

        /////////////////////// Reel Fertigation ///////////////////////////

        composable(REEL_LIST_ROUTE){
            ReelListFertigationScreen(
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                },
                onNavReel = {
                    navActions.navigateToReel()
                }
            )
        }

        composable(REEL_ROUTE){
            ReelFertigationScreen(
                onNavReelList = {
                    navActions.navigateToReelList()
                },
                onNavActivityList = {
                    navActions.navigateToActivityListCommon(
                        flowApp = FlowApp.HEADER_INITIAL_REEL_FERT.ordinal
                    )
                }
            )
        }

        composable(
            COLLECTION_LIST_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARG) { type = NavType.IntType }
            )
        ){ entry ->
            CollectionListFertigationScreen(
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                },
                onNavCollection = {
                    navActions.navigateToCollection(
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
            COLLECTION_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARG) { type = NavType.IntType },
                navArgument(ID_ARG) { type = NavType.IntType }
            )
        ){ entry ->
            CollectionFertigationScreen(
                onNavCollectionList = {
                    navActions.navigateToCollectionList(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARG)!!,
                    )
                }
            )
        }

        composable(NOZZLE_LIST_ROUTE){
            NozzleListFertigationScreen(
                onNavActivityList = {
                    navActions.navigateToActivityListCommon(
                        flowApp = FlowApp.NOTE_REEL_FERT.ordinal
                    )
                },
                onNavPressureList = {
                    navActions.navigateToPressureList()
                },
            )
        }

        composable(PRESSURE_LIST_ROUTE){
            PressureListFertigationScreen(
                onNavNozzleList = {
                    navActions.navigateToNozzleList()
                },
                onNavSpeedList = {
                    navActions.navigateToSpeedList()
                }
            )
        }

        composable(SPEED_LIST_ROUTE){
            SpeedListFertigationScreen(
                onNavPressureList = {
                    navActions.navigateToPressureList()
                },
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                }
            )
        }

        ///////////////////////////////////////////////////////////////////

        ////////////////////////// Implement //////////////////////////////

        composable(IMPLEMENTATION_ROUTE){
            ImplementScreen(
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                },
                onNavHourMeter = {
                    navActions.navigateToHourMeterHeader()
                }
            )
        }

        ///////////////////////////////////////////////////////////////////

        /////////////////////////// Mechanic //////////////////////////////

        composable(OS_MECHANIC_ROUTE){
            OSMechanicScreen(
                onNavMenuNote = {
                    navActions.navigateToMenuNote()
                },
                onNavItemList = {
                    navActions.navigateToItemListMechanic()
                },
                onNavInputItem = {
                    navActions.navigateToInputItemMechanic()
                }
            )
        }

        composable(ITEM_LIST_MECHANIC_ROUTE){
            ItemListMechanicScreen(
                onNavMenu = {
                    navActions.navigateToMenuNote()
                },
                onNavOS = {
                    navActions.navigateToOSMechanic()
                }
            )
        }

        composable(INPUT_ITEM_MECHANIC_ROUTE){
            InputItemMechanicScreen(
                onNavMenu = {
                    navActions.navigateToMenuNote()
                },
                onNavOS = {
                    navActions.navigateToOSMechanic()
                }
            )
        }

        ///////////////////////////////////////////////////////////////////

    }

}
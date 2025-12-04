package br.com.usinasantafe.cmm.lib

val appList = listOf(
    1 to PMM,
    2 to ECM,
    3 to PCOMP_INPUT,
    4 to PCOMP_COMPOUND
)

val functionListPMM = listOf(
    1 to WORK,
    2 to STOP,
    3 to PERFORMANCE,
    4 to TRANSHIPMENT,
    5 to IMPLEMENT,
    6 to HOSE_COLLECTION,
    7 to NOTE_MECHANICAL,
    8 to FINISH_MECHANICAL,
    9 to TIRE_INFLATION,
    10 to TIRE_CHANGE,
    11 to REEL
)

val typeListPMM = listOf(
    1 to ITEM_NORMAL,
    2 to PERFORMANCE,
    3 to TRANSHIPMENT,
    4 to IMPLEMENT,
    5 to FERTIGATION,
    6 to MECHANICAL,
    7 to TIRE,
    8 to REEL,
)

val functionListECM = listOf(
    1 to EXIT_FIELD,
    2 to RETURN_WORK,
    3 to EXIT_MILL,
    4 to FIELD_ARRIVAL,
    5 to UNCOUPLING_FIELD,
    6 to COUPLING_FIELD,
    7 to WAITING_LOADING,
    8 to LOADING,
    9 to CERTIFICATE,
    10 to WEIGHING_SCALE,
    11 to UNCOUPLING_COURTYARD,
    12 to COUPLING_COURTYARD,
    13 to STOP_COURTYARD,
    14 to UNLOADING_HILO,
    15 to FINISH_DISCHARGE,
    16 to WAITING_ALLOCATION,
    17 to RETURN_MILL
)

val typeListECM = listOf(
    1 to ITEM_NORMAL,
    2 to EXIT_MILL,
    3 to FIELD_ARRIVAL,
    4 to CERTIFICATE,
    5 to RETURN_MILL,
    6 to WEIGHING,
    7 to UNCOUPLING_TRAILER,
    8 to COUPLING_TRAILER
)

val functionListPCOMPInput = listOf(
    1 to RETURN_WORK,
    2 to WEIGHING_TARE,
    3 to EXIT_SCALE,
    4 to WAITING_LOADING,
    5 to LOADING_INPUT,
    6 to EXIT_LOADING,
    7 to WEIGHING_LOADED,
    8 to EXIT_TO_DEPOSIT,
    9 to WAITING_UNLOADING,
    10 to UNLOADING_INPUT,
    11 to EXIT_UNLOADING,
    12 to CHECK_WILL
)

val typeListPCOMPInput = listOf(
    1 to ITEM_NORMAL,
    2 to WEIGHING_TARE,
    3 to LOADING_INPUT,
    4 to WEIGHING_LOADED,
    5 to CHECK_WILL,
    6 to EXIT_SCALE,
    7 to EXIT_TO_DEPOSIT,
    8 to WORK,
    9 to WAITING_UNLOADING,
    10 to UNLOADING_INPUT
)

val functionListPCOMPCompound = listOf(
    1 to RETURN_WORK,
    2 to WEIGHING_TARE,
    3 to EXIT_MILL,
    4 to WAITING_LOADING,
    5 to LOADING_COMPOUND,
    6 to EXIT_LOADING,
    7 to WEIGHING_LOADED,
    8 to EXIT_TO_FIELD,
    9 to WAITING_UNLOADING,
    10 to UNLOADING_COMPOUND,
    11 to EXIT_UNLOADING,
    12 to CHECK_WILL
)

val typeListPCOMPCompound = listOf(
    1 to ITEM_NORMAL,
    2 to WEIGHING_TARE,
    3 to LOADING_COMPOUND,
    4 to WEIGHING_LOADED,
    5 to CHECK_WILL,
    6 to EXIT_MILL,
    7 to EXIT_TO_FIELD,
    8 to WORK
)

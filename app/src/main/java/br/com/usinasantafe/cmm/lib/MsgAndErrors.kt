package br.com.usinasantafe.cmm.lib

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.usinasantafe.cmm.R

@Composable
fun msg(levelUpdate: LevelUpdate?, failure: String, tableUpdate: String): String {
    return when(levelUpdate){
        LevelUpdate.CHECK_CONNECTION -> stringResource(id = R.string.text_msg_check_os)
        LevelUpdate.RECOVERY -> stringResource(id = R.string.text_msg_recovery, tableUpdate)
        LevelUpdate.CLEAN -> stringResource(id = R.string.text_msg_clean, tableUpdate)
        LevelUpdate.SAVE -> stringResource(id = R.string.text_msg_save, tableUpdate)
        LevelUpdate.GET_TOKEN -> stringResource(id = R.string.text_msg_get_token)
        LevelUpdate.SAVE_TOKEN -> stringResource(id = R.string.text_msg_save_token)
        LevelUpdate.FINISH_UPDATE_INITIAL -> stringResource(id = R.string.text_msg_finish_update_initial)
        LevelUpdate.FINISH_UPDATE_COMPLETED -> stringResource(id = R.string.text_msg_finish_update_completed)
        else -> failure
    }
}

@Composable
fun errors(errors: Errors, failure: String, value: String = "", hourMeter: String = "", hourMeterOld: String = ""): String {
    return when (errors) {
        Errors.FIELD_EMPTY -> {
            if(!value.isEmpty()) return stringResource(
                id = R.string.text_field_empty,
                value
            )
            stringResource(id = R.string.text_field_empty_config)
        }
        Errors.UPDATE -> stringResource(
            id = R.string.text_update_failure,
            failure
        )
        Errors.INVALID -> {
            stringResource(
                id = R.string.text_input_data_invalid,
                value
            )
        }
        Errors.INVALID_HOUR_METER -> {
            stringResource(
                id = R.string.text_input_hour_meter_invalid,
                hourMeter,
                hourMeterOld
            )
        }
        Errors.INVALID_VALUE -> {
            stringResource(id = R.string.text_msg_value_invalid)
        }
        Errors.INVALID_CLOSE_PERFORMANCE -> {
            stringResource(id = R.string.text_close_performance_invalid)
        }
        Errors.INVALID_CLOSE_COLLECTION -> {
            stringResource(id = R.string.text_close_collection_invalid)
        }
        else -> stringResource(
            id = R.string.text_failure,
            failure
        )
    }
}
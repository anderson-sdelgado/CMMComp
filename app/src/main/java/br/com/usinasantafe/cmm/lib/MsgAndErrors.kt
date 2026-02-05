package br.com.usinasantafe.cmm.lib

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.usinasantafe.cmm.R

@Composable
fun msg(levelUpdate: LevelUpdate?, failure: String, tableUpdate: String): String {
    return when(levelUpdate){
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
fun errors(errors: Errors, failure: String, value: String = "", hourMeter: String = "", hourMeterOld: String = "", typePerformance: Boolean = false): String {
    return when (errors) {
        Errors.FIELD_EMPTY -> {
            if(value.isEmpty()) return stringResource(
                id = R.string.text_field_empty,
                value
            )
            return stringResource(id = R.string.text_field_empty_config)
        }
        Errors.UPDATE -> stringResource(
            id = R.string.text_update_failure,
            failure
        )
        Errors.INVALID -> {

            if (!hourMeter.isEmpty()) {
                return stringResource(
                    id = R.string.text_input_hour_meter_invalid,
                    hourMeter,
                    hourMeterOld
                )
            }

            if(typePerformance) {
                return stringResource(id = R.string.text_msg_performance_invalid)
            }

            return stringResource(
                id = R.string.text_input_data_invalid,
                value
            )
        }
        else -> stringResource(
            id = R.string.text_failure,
            failure
        )
    }
}
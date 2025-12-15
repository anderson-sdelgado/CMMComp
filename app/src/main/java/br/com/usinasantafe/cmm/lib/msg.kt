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
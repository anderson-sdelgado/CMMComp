package br.com.usinasantafe.cmm.utils

enum class StatusSend { STARTED, SEND, SENDING, SENT }
enum class Errors { FIELDEMPTY, TOKEN, UPDATE, EXCEPTION, INVALID }
enum class TypeButton { NUMERIC, CLEAN, OK, UPDATE }
enum class FlagUpdate { OUTDATED, UPDATED }

enum class StatusLeira { LIBERADA, DESCARGA, CARGA }
enum class TypeOper { ATIVIDADE, PARADA }
enum class FuncAtividade { RENDIMENTO, TRANSBORDO, IMPLEMENTO, CARRETEL, LEIRA, TRANP_CANA }
enum class FuncParada { CHECKLIST, IMPLEMENTO, CALIBRAGEM }

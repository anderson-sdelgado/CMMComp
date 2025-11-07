package br.com.usinasantafe.cmm.utils

const val dataMenu = """
[
    {
        "id": 1,
        "descr": "TRABALHANDO",
        "idType": 1,
        "pos": 1,
        "idFunction": 1,
        "idApp": 1
    },
    {
        "id": 2,
        "descr": "PARADO",
        "idType": 1,
        "pos": 2,
        "idFunction": 2,
        "idApp": 1
    },
    {
        "id": 3,
        "descr": "RENDIMENTO",
        "idType": 4,
        "pos": 3,
        "idFunction": 3,
        "idApp": 1
    },
    {
        "id": 4,
        "descr": "NOVO TRANSBORDO",
        "idType": 5,
        "pos": 4,
        "idFunction": 4,
        "idApp": 1
    },
    {
        "id": 5,
        "descr": "TROCAR IMPLEMENTO",
        "idType": 6,
        "pos": 5,
        "idFunction": 5,
        "idApp": 1
    },
    {
        "id": 6,
        "descr": "RECOLHIMENTO MANGUEIRA",
        "idType": 7,
        "pos": 6,
        "idFunction": 6,
        "idApp": 1
    },
    {
        "id": 7,
        "descr": "APONTAR MANUTENÇÃO",
        "idType": 8,
        "pos": 7,
        "idFunction": 7,
        "idApp": 1
    },
    {
        "id": 8,
        "descr": "FINALIZAR MANUTENÇÃO",
        "idType": 8,
        "pos": 8,
        "idFunction": 8,
        "idApp": 1
    },
    {
        "id": 9,
        "descr": "CALIBRAGEM DE PNEU",
        "idType": 9,
        "pos": 9,
        "idFunction": 9,
        "idApp": 1
    },
    {
        "id": 10,
        "descr": "TROCA DE PNEU",
        "idType": 9,
        "pos": 10,
        "idFunction": 10,
        "idApp": 1
    },
    {
        "id": 11,
        "descr": "APONTAR CARRETEL",
        "idType": 10,
        "pos": 11,
        "idFunction": 11,
        "idApp": 1
    },
    {
        "id": 14,
        "descr": "SAIDA PARA CAMPO",
        "idType": 13,
        "pos": 0,
        "idFunction": 1,
        "idApp": 2
    },
    {
        "id": 15,
        "descr": "VOLTAR AO TRABALHO",
        "idType": 14,
        "pos": 0,
        "idFunction": 2,
        "idApp": 2
    },
    {
        "id": 16,
        "descr": "SAIDA DA USINA",
        "idType": 2,
        "pos": 1,
        "idFunction": 3,
        "idApp": 2
    },
    {
        "id": 17,
        "descr": "CHEGADA AO CAMPO",
        "idType": 3,
        "pos": 2,
        "idFunction": 4,
        "idApp": 2
    },
    {
        "id": 18,
        "descr": "DESENGATE CAMPO",
        "idType": 8,
        "pos": 3,
        "idFunction": 5,
        "idApp": 2
    },
    {
        "id": 19,
        "descr": "ENGATE CAMPO",
        "idType": 9,
        "pos": 4,
        "idFunction": 6,
        "idApp": 2
    },
    {
        "id": 20,
        "descr": "AGUARDANDO CARREGAMENTO",
        "idType": 1,
        "pos": 5,
        "idFunction": 7,
        "idApp": 2
    },
    {
        "id": 21,
        "descr": "CARREGAMENTO",
        "idType": 1,
        "pos": 6,
        "idFunction": 8,
        "idApp": 2
    },
    {
        "id": 22,
        "descr": "CERTIFICADO",
        "idType": 4,
        "pos": 7,
        "idFunction": 9,
        "idApp": 2
    },
    {
        "id": 23,
        "descr": "PESAGEM NA BALANCA",
        "idType": 6,
        "pos": 9,
        "idFunction": 10,
        "idApp": 2
    },
    {
        "id": 24,
        "descr": "DESENGATE PATIO",
        "idType": 19,
        "pos": 10,
        "idFunction": 11,
        "idApp": 2
    },
    {
        "id": 25,
        "descr": "ENGATE PATIO",
        "idType": 20,
        "pos": 11,
        "idFunction": 12,
        "idApp": 2
    },
    {
        "id": 26,
        "descr": "PARADA NO PATIO",
        "idType": 11,
        "pos": 12,
        "idFunction": 13,
        "idApp": 2
    },
    {
        "id": 27,
        "descr": "DESCARREGAMENTO HILO",
        "idType": 1,
        "pos": 13,
        "idFunction": 14,
        "idApp": 2
    },
    {
        "id": 28,
        "descr": "FIM DE DESCARGA",
        "idType": 12,
        "pos": 14,
        "idFunction": 15,
        "idApp": 2
    },
    {
        "id": 29,
        "descr": "AGUARDANDO ALOCACAO",
        "idType": 1,
        "pos": 15,
        "idFunction": 16,
        "idApp": 2
    },
    {
        "id": 30,
        "descr": "RETORNO PRA USINA",
        "idType": 5,
        "pos": 16,
        "idFunction": 17,
        "idApp": 2
    },
    {
        "id": 31,
        "descr": "VOLTAR AO TRABALHO",
        "idType": 8,
        "pos": 0,
        "idFunction": 1,
        "idApp": 3
    },
    {
        "id": 32,
        "descr": "PESAGEM TARA",
        "idType": 2,
        "pos": 1,
        "idFunction": 2,
        "idApp": 3
    },
    {
        "id": 33,
        "descr": "SAIDA DA BALANCA",
        "idType": 6,
        "pos": 2,
        "idFunction": 3,
        "idApp": 3
    },
    {
        "id": 34,
        "descr": "AGUARDANDO CARREGAMENTO",
        "idType": 1,
        "pos": 3,
        "idFunction": 4,
        "idApp": 3
    },
    {
        "id": 35,
        "descr": "CARREGAMENTO DE INSUMO",
        "idType": 3,
        "pos": 4,
        "idFunction": 5,
        "idApp": 3
    },
    {
        "id": 36,
        "descr": "SAIDA DO CARREGAMENTO",
        "idType": 1,
        "pos": 5,
        "idFunction": 6,
        "idApp": 3
    },
    {
        "id": 37,
        "descr": "PESAGEM CARREGADO",
        "idType": 4,
        "pos": 6,
        "idFunction": 7,
        "idApp": 3
    },
    {
        "id": 38,
        "descr": "SAIDA PARA DEPOSITO",
        "idType": 7,
        "pos": 7,
        "idFunction": 8,
        "idApp": 3
    },
    {
        "id": 39,
        "descr": "AGUARDANDO DESCARREG.",
        "idType": 11,
        "pos": 8,
        "idFunction": 9,
        "idApp": 3
    },
    {
        "id": 40,
        "descr": "DESCARREG. DE INSUMO",
        "idType": 10,
        "pos": 9,
        "idFunction": 10,
        "idApp": 3
    },
    {
        "id": 41,
        "descr": "SAIDA DO DESCARREGAMENTO",
        "idType": 1,
        "pos": 10,
        "idFunction": 11,
        "idApp": 3
    },
    {
        "id": 42,
        "descr": "VERIFICAR LEIRA",
        "idType": 5,
        "pos": 999,
        "idFunction": 12,
        "idApp": 3
    },
    {
        "id": 44,
        "descr": "VOLTAR AO TRABALHO",
        "idType": 8,
        "pos": 0,
        "idFunction": 1,
        "idApp": 4
    },
    {
        "id": 45,
        "descr": "PESAGEM TARA",
        "idType": 2,
        "pos": 1,
        "idFunction": 2,
        "idApp": 4
    },
    {
        "id": 46,
        "descr": "SAIDA DA USINA",
        "idType": 6,
        "pos": 2,
        "idFunction": 3,
        "idApp": 4
    },
    {
        "id": 47,
        "descr": "AGUARDANDO CARREGAMENTO",
        "idType": 1,
        "pos": 3,
        "idFunction": 4,
        "idApp": 4
    },
    {
        "id": 48,
        "descr": "CARREGAMENTO DE COMPOSTO",
        "idType": 3,
        "pos": 4,
        "idFunction": 5,
        "idApp": 4
    },
    {
        "id": 49,
        "descr": "SAIDA DO CARREGAMENTO",
        "idType": 1,
        "pos": 5,
        "idFunction": 6,
        "idApp": 4
    },
    {
        "id": 50,
        "descr": "PESAGEM CARREGADO",
        "idType": 4,
        "pos": 6,
        "idFunction": 7,
        "idApp": 4
    },
    {
        "id": 51,
        "descr": "SAIDA PARA CAMPO",
        "idType": 7,
        "pos": 7,
        "idFunction": 8,
        "idApp": 4
    },
    {
        "id": 52,
        "descr": "AGUARDANDO DESCARREG.",
        "idType": 1,
        "pos": 8,
        "idFunction": 9,
        "idApp": 4
    },
    {
        "id": 53,
        "descr": "DESCARREG. DE COMPOSTO",
        "idType": 1,
        "pos": 9,
        "idFunction": 10,
        "idApp": 4
    },
    {
        "id": 54,
        "descr": "SAIDA DO DESCARREGAMENTO",
        "idType": 1,
        "pos": 10,
        "idFunction": 11,
        "idApp": 4
    },
    {
        "id": 55,
        "descr": "VERIFICAR LEIRA",
        "idType": 5,
        "pos": 999,
        "idFunction": 12,
        "idApp": 4
    }
]
"""
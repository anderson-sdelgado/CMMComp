package br.com.usinasantafe.cmm.external.internal

import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class IItemMenuInternalDatasourceTest {

    private val datasource = IItemMenuInternalDatasource()

    @Test
    fun `listAll - Check return correct if function execute successfully`() =
        runTest {
            val result = datasource.listAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                15
            )
            val item = list[0]
            assertEquals(
                item.id,
                1
            )
            assertEquals(
                item.title,
                "TRABALHANDO"
            )
            assertEquals(
                item.type,
                1
            )
        }

}
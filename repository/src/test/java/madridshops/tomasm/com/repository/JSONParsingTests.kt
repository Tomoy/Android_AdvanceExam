package madridshops.tomasm.com.repository

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import madridshops.tomasm.com.repository.db.model.ShopEntity
import madridshops.tomasm.com.repository.db.model.ShopsResponseEntity
import madridshops.tomasm.com.repository.network.json.JSONEntitiesParser
import madridshops.tomasm.com.repository.util.ReadJsonFile
import org.junit.Assert.*
import org.junit.Test

class JSONParsingTests {
    @Test
    @Throws(Exception::class)
    fun given_valid_string_containing_json_it_parses_correctly() {
        val shopsJson = ReadJsonFile().loadJSONFromAsset("shop.json")
        assertTrue(shopsJson.isNotEmpty())

        //parsing
        val parser = JSONEntitiesParser()
        val shop = parser.parse<ShopEntity>(shopsJson)

        assertNotNull(shop)
        assertEquals("Cortefiel - Preciados", shop.name)
    }

    @Test
    @Throws(Exception::class)
    fun given_invalid_string_containing_json_with_wrong_latitude_it_parses_correctly() {
        val shopsJson = ReadJsonFile().loadJSONFromAsset("shopWrongLatitude.json")
        assertTrue(shopsJson.isNotEmpty())

        //parsing
        val parser = JSONEntitiesParser()
        var shop: ShopEntity

        try {
            shop = parser.parse<ShopEntity>(shopsJson)
        } catch (e: InvalidFormatException) {
            shop = ShopEntity(1, 1, "Parsing Failed", "", 10f, 5f, "", "", "", "")
        }

        assertNotNull(shop)
        assertEquals("Parsing Failed", shop.name)
    }

    @Test
    @Throws(Exception::class)
    fun given_valid_string_containing_json_shops_it_parses_correctly_all() {
        val shopsJson = ReadJsonFile().loadJSONFromAsset("shops.json")
        assertTrue(shopsJson.isNotEmpty())

        //parsing
        val parser = JSONEntitiesParser()
        val responseEntity = parser.parse<ShopsResponseEntity>(shopsJson)

        assertNotNull(responseEntity)
        assertEquals(6, responseEntity.result.count())
        assertEquals("Cortefiel - Preciados", responseEntity.result[0].name)
    }
}
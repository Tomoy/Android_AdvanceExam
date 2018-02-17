package madridshops.tomasm.com.repository

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import madridshops.tomasm.com.repository.db.build
import madridshops.tomasm.com.repository.db.dao.ShopDAO
import madridshops.tomasm.com.repository.db.model.ShopEntity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
internal class ExampleInstrumentedTest {

    val appContext = InstrumentationRegistry.getTargetContext()
    val dbHelper = build( appContext, "mydb.sqlite", 1)

    @Test
    @Throws(Exception::class)
    fun given_valid_shopentity_it_gets_inserted_correctly() {
        // Context of the app under test.
        val shopEntityDao = ShopDAO(dbHelper)

        val shop = ShopEntity(1, 1, "My Shop 1", "My beautiful shop 1 in Springfield",
                1.0f, 2.0f, "", "", "", "Avenida Siempre Viva 17")

        val id = shopEntityDao.insert(shop)

        assertTrue(id > 0)  //Porque si da 0 o negativo, hubo algún problema
    }
}

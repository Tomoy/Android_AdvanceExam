package madridshops.tomasm.com.repository

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import madridshops.tomasm.com.repository.db.build
import madridshops.tomasm.com.repository.db.dao.ShopDAO
import madridshops.tomasm.com.repository.db.model.ShopEntity
import madridshops.tomasm.com.repository.network.NetworkingInterface
import madridshops.tomasm.com.repository.network.NetworkingVolley
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class NetworkingVolleyTests {

    val appContext = InstrumentationRegistry.getTargetContext()

    @Test
    @Throws(Exception::class)
    fun given_valid_url_we_get_non_null_json_as_string() {
        val url = "http://madrid-shops.com/json_new/getShops.php"

        val networkingManager: NetworkingInterface = NetworkingVolley(appContext)

        networkingManager.execute(url, object: SuccessCompletion<String> {
            override fun successCompletion(e: String) {
                assertTrue(e.isNotEmpty())
                Log.d("TEST", "Json: " + e)
            }

        }, object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                assertTrue(false) //aseguro que está mal asi que lo hago fallar
            }
        })
    }
}
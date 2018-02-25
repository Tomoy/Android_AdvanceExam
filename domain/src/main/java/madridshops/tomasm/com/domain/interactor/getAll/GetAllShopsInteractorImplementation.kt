package madridshops.tomasm.com.domain.interactor.getAll

import android.content.Context
import android.util.Log
import madridshops.tomasm.com.domain.interactor.ErrorCompletion
import madridshops.tomasm.com.domain.interactor.SuccessCompletion
import madridshops.tomasm.com.domain.model.Shop
import madridshops.tomasm.com.domain.model.Shops
import madridshops.tomasm.com.repository.Repository
import madridshops.tomasm.com.repository.RepositoryShopsImplementation
import madridshops.tomasm.com.repository.db.model.ShopEntity
import java.lang.ref.WeakReference

class GetAllShopsInteractorImplementation(context: Context) : GetAllInteractor<Shops> {

    private val weakContext = WeakReference<Context>(context)
    private val repository: Repository<ShopEntity> = RepositoryShopsImplementation(weakContext.get()!!)

    override fun execute(success: SuccessCompletion<Shops>, error: ErrorCompletion) {
        repository.getAll(success = {
            //Mapear los shopsEntity que recibimos del repositorio a Shops
            val shops: Shops = mapEntityToShops(it)
            success.successCompletion(shops)
        }, error = {
            error(it)
        })
    }

    private fun mapEntityToShops(list: List<ShopEntity>): Shops {

        val tempList = ArrayList<Shop>()
        list.forEach {

            var shopLat:Double
            var shopLong:Double

            try {

                shopLat = it.latitude.toDouble()
                shopLong = it.longitude.toDouble()

                val shop = Shop(
                        it.id.toInt(),
                        it.name,
                        it.address,
                        it.description,
                        shopLat,
                        shopLong,
                        it.img,
                        it.logo_img,
                        it.opening_hours_en
                )
                tempList.add(shop)

            }catch(e:NumberFormatException) {
                Log.d("MapError", "There was an error mapping shop long or lat, ignoring it")
            }

        }

        return Shops(tempList)
    }
}



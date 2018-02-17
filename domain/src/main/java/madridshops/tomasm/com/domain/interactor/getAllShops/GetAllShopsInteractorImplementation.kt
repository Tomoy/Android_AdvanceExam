package madridshops.tomasm.com.domain.interactor.getAllShops

import android.content.Context
import madridshops.tomasm.com.domain.interactor.ErrorCompletion
import madridshops.tomasm.com.domain.interactor.SuccessCompletion
import madridshops.tomasm.com.domain.model.Shop
import madridshops.tomasm.com.domain.model.Shops
import madridshops.tomasm.com.repository.Repository
import madridshops.tomasm.com.repository.RepositoryImplementation
import madridshops.tomasm.com.repository.db.model.ShopEntity
import java.lang.ref.WeakReference

class GetAllShopsInteractorImplementation(context: Context) : GetAllShopsInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val repository: Repository = RepositoryImplementation(weakContext.get()!!)

    override fun execute(success: SuccessCompletion<Shops>, error: ErrorCompletion) {
        repository.getAllShops(success = {
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
            val shop = Shop(it.id.toInt(), it.name, it.address)
            tempList.add(shop)
        }

        val shops = Shops(tempList)
        return shops
    }
}



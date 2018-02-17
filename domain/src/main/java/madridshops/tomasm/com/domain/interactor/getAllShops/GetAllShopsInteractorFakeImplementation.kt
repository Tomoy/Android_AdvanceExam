package madridshops.tomasm.com.domain.interactor.getAllShops

import madridshops.tomasm.com.domain.interactor.ErrorCompletion
import madridshops.tomasm.com.domain.interactor.SuccessCompletion
import madridshops.tomasm.com.domain.model.Shop
import madridshops.tomasm.com.domain.model.Shops

class GetAllShopsInteractorFakeImplementation: GetAllShopsInteractor {

    override fun execute(success: SuccessCompletion<Shops>, error: ErrorCompletion) {
        var allOk = true

        //Connect to the repository and blabla

        if (allOk) {

            val shops = createFakeListOfShops()

            success.successCompletion(shops)
        } else {
            error.errorCompletion("Error while accessing the Repository")
        }
    }

    fun createFakeListOfShops(): Shops {
        val list = ArrayList<Shop>()

        for (i in 0..100) {
            val shop = Shop(i, "Shop " + i, "Address " + i)
            list.add(shop)
        }

        val shops = Shops(list)
        return shops
    }
}
package madridshops.tomasm.com.domain.interactor.deleteAll

import android.content.Context
import madridshops.tomasm.com.domain.interactor.CodeClosure
import madridshops.tomasm.com.domain.interactor.ErrorClosure
import madridshops.tomasm.com.domain.model.Shops
import madridshops.tomasm.com.repository.RepositoryShopsImplementation
import java.lang.ref.WeakReference

class DeleteAllShopsImplementation(context: Context) : DeleteAll<Shops> {

    val context = WeakReference<Context>(context)

    override fun execute(success: CodeClosure, error: ErrorClosure) {
        val repository = RepositoryShopsImplementation(context.get()!!)

        repository.deleteAll(success, error)
    }
}
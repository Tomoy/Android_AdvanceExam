package madridshops.tomasm.com.domain.interactor.deleteAllShops

import android.content.Context
import madridshops.tomasm.com.domain.interactor.CodeClosure
import madridshops.tomasm.com.domain.interactor.ErrorClosure
import madridshops.tomasm.com.repository.RepositoryImplementation
import java.lang.ref.WeakReference

class DeleteAllShopsImplementation(context: Context) :DeleteAllShops {

    val context = WeakReference<Context>(context)

    override fun execute(success: CodeClosure, error: ErrorClosure) {
        val repository = RepositoryImplementation(context.get()!!)

        repository.deleteAllShops(success, error)
    }
}
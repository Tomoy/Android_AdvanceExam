package madridshops.tomasm.com.domain.interactor.getAll

import madridshops.tomasm.com.domain.interactor.ErrorCompletion
import madridshops.tomasm.com.domain.interactor.SuccessCompletion

interface GetAllInteractor<T> {
    fun execute(success: SuccessCompletion<T>, error: ErrorCompletion)
}
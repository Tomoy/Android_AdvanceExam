package madridshops.tomasm.com.domain.interactor.InternetStatus

import madridshops.tomasm.com.domain.interactor.CodeClosure
import madridshops.tomasm.com.domain.interactor.ErrorClosure


interface InternetStatusInteractor {
    fun execute(success: CodeClosure, error: ErrorClosure)
}
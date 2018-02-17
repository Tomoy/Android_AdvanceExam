package madridshops.tomasm.com.domain.interactor

import madridshops.tomasm.com.domain.interactor.InternetStatus.InternetStatusInteractor

class InternetStatusInteractorImplementation: InternetStatusInteractor {

    override fun execute(success: CodeClosure, error: ErrorClosure) {
        //TODO: Implementar
        success()
    }
}
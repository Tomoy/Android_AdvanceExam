package madridshops.tomasm.com.domain.interactor.deleteAllShops

import madridshops.tomasm.com.domain.interactor.CodeClosure
import madridshops.tomasm.com.domain.interactor.ErrorClosure

//Iteractor es un caso de uso, siempre con interface genérica y después la implementación
interface DeleteAllShops {
    fun execute(success: CodeClosure, error: ErrorClosure)
}

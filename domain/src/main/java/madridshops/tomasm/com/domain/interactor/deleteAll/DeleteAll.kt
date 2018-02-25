package madridshops.tomasm.com.domain.interactor.deleteAll

import madridshops.tomasm.com.domain.interactor.CodeClosure
import madridshops.tomasm.com.domain.interactor.ErrorClosure

//Iteractor es un caso de uso, siempre con interface genérica y después la implementación
interface DeleteAll<T> {
    fun execute(success: CodeClosure, error: ErrorClosure)
}

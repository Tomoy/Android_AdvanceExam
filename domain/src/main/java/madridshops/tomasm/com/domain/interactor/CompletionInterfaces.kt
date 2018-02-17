package madridshops.tomasm.com.domain.interactor

//Clausuras de completion
interface SuccessCompletion<T> {
    fun successCompletion(e: T)
}

interface ErrorCompletion {
    fun errorCompletion(errorMessage: String)
}
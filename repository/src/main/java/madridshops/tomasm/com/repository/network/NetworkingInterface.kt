package madridshops.tomasm.com.repository.network

import madridshops.tomasm.com.repository.ErrorCompletion
import madridshops.tomasm.com.repository.SuccessCompletion

internal interface NetworkingInterface {
    fun execute(url: String, success: SuccessCompletion<String>, error: ErrorCompletion)
}
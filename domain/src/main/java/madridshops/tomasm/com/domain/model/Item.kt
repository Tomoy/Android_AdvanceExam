package madridshops.tomasm.com.domain.model

import java.io.Serializable


interface Item: Serializable {

    val name: String
    val lat: Double
    val lon: Double
    val description: String
    val address: String
}
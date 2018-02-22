package madridshops.tomasm.com.domain.model

import java.io.Serializable

data class Section (var name: String) : Serializable {

    override fun toString() = name
}
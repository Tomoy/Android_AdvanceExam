package madridshops.tomasm.com.repository.db.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

//Clase para representar el primer nivel del json para poder acceder al resto de los objetos después (Asi puedo anidar muchos niveles de json)
@JsonIgnoreProperties(ignoreUnknown = true)
internal class ShopsResponseEntity (
        val result: List<ShopEntity>
)
package madridshops.tomasm.com.repository.db.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true) //Ignora las propiedades extras que pueda haber en el json que no necesitas sin que pinche
data class ShopEntity(

        val id: Long,
        val databaseId: Long,
        val name: String,
        @JsonProperty( "description_en") val description: String,   //Puedo asignarle valores customs en el entity e independizarlo de como viene en el JSON
        @JsonProperty( "gps_lat") val latitude: String,
        @JsonProperty( "gps_lon") val longitude: String,
        val img: String,
        val logo_img: String,
        val opening_hours_en: String,
        val address: String
)
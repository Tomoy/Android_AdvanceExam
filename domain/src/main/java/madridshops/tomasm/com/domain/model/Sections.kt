package madridshops.tomasm.com.domain.model

import android.content.res.Resources
import madridshops.tomasm.com.domain.R
import java.io.Serializable

object Sections : Serializable {


    private var sections: List<Section> = listOf(
            Section("Shops"),
            Section("Activities")
    )

    val count
        get() = sections.size

    //    fun getCity(index: Int) = cities(index)
    //Mismo pero mas corto y te autocompleta al usarlo
    operator fun get(i:Int) = sections[i]

    fun toArray() = sections.toTypedArray()
}
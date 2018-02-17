package madridshops.tomasm.com.repository.network.json

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.introspect.VisibilityChecker
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue


internal class JSONEntitiesParser {

    val mapper = jacksonObjectMapper()

    inline fun <reified T: Any>parse(json: String): T {
        return this.mapper.readValue<T>(json)
    }
}
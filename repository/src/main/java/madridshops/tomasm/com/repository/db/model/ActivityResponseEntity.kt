package madridshops.tomasm.com.repository.db.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)

internal class ActivityResponseEntity (
        val result: List<ActivityEntity>
)

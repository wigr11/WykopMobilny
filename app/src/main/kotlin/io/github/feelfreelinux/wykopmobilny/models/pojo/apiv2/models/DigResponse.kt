package io.github.feelfreelinux.wykopmobilny.models.pojo.apiv2.models

import com.fasterxml.jackson.annotation.JsonProperty import com.fasterxml.jackson.annotation.JsonIgnoreProperties @JsonIgnoreProperties(ignoreUnknown = true)

data class DigResponse (
    @JsonProperty("digs")
    val diggs : Int,

    @JsonProperty("buries")
    val buries : Int
)
package io.github.feelfreelinux.wykopmobilny.models.pojo.apiv2.embed

import com.fasterxml.jackson.annotation.JsonProperty import com.fasterxml.jackson.annotation.JsonIgnoreProperties @JsonIgnoreProperties(ignoreUnknown = true)

data class StreamableFile(
        @JsonProperty("url")
        val url : String
)
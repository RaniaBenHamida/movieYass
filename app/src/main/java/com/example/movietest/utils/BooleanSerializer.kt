package com.example.movietest.utils

import com.google.gson.*
import java.lang.reflect.Type

class BooleanSerializer : JsonSerializer<Boolean>, JsonDeserializer<Boolean> {


    override fun serialize(
        src: Boolean?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonPrimitive(if (src == true) 1 else 0)

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Boolean = if (typeOfT == Int::class.java) json?.asInt == 1  else  json?.asBoolean == true
}
package com.bnyro.translate.api.lv

import com.bnyro.translate.api.lv.obj.LVTranslation
import com.bnyro.translate.api.lv.obj.LvLanguage
import retrofit2.http.GET
import retrofit2.http.Path

interface LingvaTranslate {
    @GET("api/v1/languages")
    suspend fun getLanguages(): LvLanguage

    @GET("api/v1/{source}/{target}/{query}")
    suspend fun translate(
        @Path("source") source: String,
        @Path("target") target: String,
        @Path("query") query: String
    ): LVTranslation
}

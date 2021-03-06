package io.github.feelfreelinux.wykopmobilny.api.tag

import io.github.feelfreelinux.wykopmobilny.api.UserTokenRefresher
import io.github.feelfreelinux.wykopmobilny.api.errorhandler.ErrorHandler
import io.github.feelfreelinux.wykopmobilny.api.errorhandler.ErrorHandlerTransformer
import io.github.feelfreelinux.wykopmobilny.models.mapper.apiv2.TagEntriesMapper
import io.github.feelfreelinux.wykopmobilny.models.mapper.apiv2.TagLinksMapper
import io.github.feelfreelinux.wykopmobilny.models.pojo.apiv2.models.ObservedTagResponse
import io.github.feelfreelinux.wykopmobilny.models.pojo.apiv2.models.ObserveStateResponse
import io.github.feelfreelinux.wykopmobilny.models.pojo.apiv2.responses.TagEntriesResponse
import io.github.feelfreelinux.wykopmobilny.models.pojo.apiv2.responses.TagLinksResponse
import io.github.feelfreelinux.wykopmobilny.utils.preferences.BlacklistPreferences
import io.github.feelfreelinux.wykopmobilny.utils.preferences.BlacklistPreferencesApi
import io.github.feelfreelinux.wykopmobilny.utils.preferences.LinksPreferencesApi
import io.github.feelfreelinux.wykopmobilny.utils.preferences.SettingsPreferencesApi
import io.reactivex.Single
import retrofit2.Retrofit

class TagRepository(retrofit: Retrofit, val userTokenRefresher: UserTokenRefresher, val linksPreferencesApi: LinksPreferencesApi, val blacklistPreferencesApi: BlacklistPreferencesApi, val settingsPreferencesApi: SettingsPreferencesApi) : TagApi {
    private val tagApi by lazy { retrofit.create(TagRetrofitApi::class.java) }

    override fun getTagEntries(tag : String, page : Int) = tagApi.getTagEntries(tag, page)
            .retryWhen(userTokenRefresher)
            .flatMap(ErrorHandler<TagEntriesResponse>())
            .map { TagEntriesMapper.map(it, blacklistPreferencesApi, settingsPreferencesApi)}

    override fun getTagLinks(tag : String, page : Int) = tagApi.getTagLinks(tag, page)
            .retryWhen(userTokenRefresher)
            .flatMap(ErrorHandler<TagLinksResponse>())
            .map { TagLinksMapper.map(it, linksPreferencesApi, blacklistPreferencesApi, settingsPreferencesApi)}

    override fun getObservedTags() : Single<List<ObservedTagResponse>> =
            tagApi.getObservedTags()
            .retryWhen(userTokenRefresher)
            .compose<List<ObservedTagResponse>>(ErrorHandlerTransformer())

    override fun observe(tag : String) = tagApi.observe(tag)
            .retryWhen(userTokenRefresher)
            .compose<ObserveStateResponse>(ErrorHandlerTransformer())

    override fun unobserve(tag : String) = tagApi.unobserve(tag)
            .retryWhen(userTokenRefresher)
            .compose<ObserveStateResponse>(ErrorHandlerTransformer())

    override fun block(tag : String) = tagApi.block(tag)
            .retryWhen(userTokenRefresher)
            .compose<ObserveStateResponse>(ErrorHandlerTransformer())
            .doOnSuccess {
                if (!blacklistPreferencesApi.blockedTags.contains(tag.removePrefix("#")))
                    blacklistPreferencesApi.blockedTags = blacklistPreferencesApi.blockedTags.plus(tag.removePrefix("#"))
            }

    override fun unblock(tag : String) = tagApi.unblock(tag)
            .retryWhen(userTokenRefresher)
            .compose<ObserveStateResponse>(ErrorHandlerTransformer())
            .doOnSuccess {
                if (blacklistPreferencesApi.blockedTags.contains(tag.removePrefix("#")))
                    blacklistPreferencesApi.blockedTags = blacklistPreferencesApi.blockedTags.minus(tag.removePrefix("#"))
            }
}
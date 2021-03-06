package io.github.feelfreelinux.wykopmobilny.ui.modules.addlink.fragments.confirmdetails

import io.github.feelfreelinux.wykopmobilny.api.addlink.AddlinkApi
import io.github.feelfreelinux.wykopmobilny.base.BasePresenter
import io.github.feelfreelinux.wykopmobilny.base.Schedulers

class AddLinkDetailsFragmentPresenter(val schedulers: Schedulers, val addlinkApi: AddlinkApi) : BasePresenter<AddLinkDetailsFragmentView>() {
    fun getImages(key: String) {
        view?.showImagesLoading(true)
        compositeObservable.add(
                addlinkApi.getImages(key)
                        .subscribeOn(schedulers.backgroundThread())
                        .observeOn(schedulers.mainThread())
                        .subscribe({
                            view?.showImages(it)
                            view?.showImagesLoading(false)
                        }, {
                            view?.showErrorDialog(it)
                            view?.showImagesLoading(false)
                        })
        )
    }

    fun publishLink(key : String, title : String, sourceUrl : String, description : String, tags : String, plus18 : Boolean, imageKey : String) {
        view?.showLinkUploading(true)
        compositeObservable.add(
                addlinkApi.publishLink(key, title, description, tags, imageKey, sourceUrl, plus18)
                        .subscribeOn(schedulers.backgroundThread())
                        .observeOn(schedulers.mainThread())
                        .subscribe({
                            view?.openLinkScreen(it) }, { view?.showErrorDialog(it) })
        )
    }
}
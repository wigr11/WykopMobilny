package io.github.feelfreelinux.wykopmobilny.ui.modules.profile.links.burried

import io.github.feelfreelinux.wykopmobilny.base.BaseView
import io.github.feelfreelinux.wykopmobilny.models.dataclass.Link

interface BurriedLinksView : BaseView {
    fun addDataToAdapter(entryList : List<Link>, shouldClearAdapter : Boolean)
    fun disableLoading()
}
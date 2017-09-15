package io.github.feelfreelinux.wykopmobilny.ui.mainnavigation

import android.net.Uri
import android.support.v4.app.Fragment
import io.github.feelfreelinux.wykopmobilny.base.BasePresenter
import io.github.feelfreelinux.wykopmobilny.base.BaseView

interface MainNavigationView : BaseView {
    var notificationCount : Int
    var hashTagNotificationCount : Int
    var avatarUrl : String
    var actionUrl : Uri?

    fun openFragment(fragment : Fragment)
    fun closeDrawer()
}
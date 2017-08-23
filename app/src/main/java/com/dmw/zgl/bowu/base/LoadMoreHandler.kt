package com.dmw.zgl.bowu.base

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/11 14:20
 * Update:          2017/8/11 14:20
 * Description:     LoadMoreHandler
 */

interface LoadMoreHandler {

    val isCanLoadMore: Boolean

    fun loadMore()
}

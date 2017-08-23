package com.dmw.zgl.bowu.model

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 11:05
 * Update:          2017/8/8 11:05
 * Description:     ArticleCoverData
 */

class ArticleCoverData {
    var cover: ImageData? = null
    var name: String? = null
    var desc: String? = null
    var is_free: Boolean = false
    var author: String? = null
    var time: String? = null
    @Transient
    var left: Boolean = false
    @Transient
    var right: Boolean = false
}

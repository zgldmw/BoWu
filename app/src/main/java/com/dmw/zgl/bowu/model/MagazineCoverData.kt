package com.dmw.zgl.bowu.model

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 11:08
 * Update:          2017/8/8 11:08
 * Description:     MagazineCoverData
 */

class MagazineCoverData {
    var cover: ImageData? = null
    var name: String? = null
    var time: String? = null
    var order: String? = null
    var link_url: String? = null
    @Transient
    var left: Boolean = false
    @Transient
    var right: Boolean = false
}

package com.kuanquan.glidelibrary.config

import android.widget.ImageView

open class ImageConfig {
    /**
     * 网络图片
     */
    var url: String? = null
        protected set

    /**
     * 本地资源
     */
    var drawableId = 0
        protected set


    var imageView: ImageView? = null
        protected set

    /**
     * 占位图
     */
    var placeholder = 0
        protected set

    /**
     * 加载错误图
     */
    var errorPic = 0
        protected set
}
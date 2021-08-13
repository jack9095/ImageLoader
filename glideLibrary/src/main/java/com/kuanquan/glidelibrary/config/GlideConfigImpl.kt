package com.kuanquan.glidelibrary.config

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestListener

/**
 * Glide配置类
 */
class GlideConfigImpl private constructor(builder: Builder) : ImageConfig() {
    /**
     * 0 对应DiskCacheStrategy.all
     * 1 对应DiskCacheStrategy.NONE
     * 2 对应DiskCacheStrategy.SOURCE
     * 3 对应DiskCacheStrategy.RESULT
     */
    val cacheStrategy: Int

    val transformation: Array<out BitmapTransformation>?
    private val imageViews: Array<out ImageView>?
    private val isClearMemory: Boolean
    private val isClearDiskCache: Boolean
    val placeHolderDrawable: Drawable?
    val resizeX: Int
    val isCropCenter: Boolean
    val isCropCircle: Boolean
    val isFitCenter: Boolean
    val formatType: DecodeFormat?
    val resizeY: Int
    val imageRadius: Int
    val blurValue: Int
    val isCrossFade: Boolean
    var requestListener: RequestListener<Drawable?>?

    init {
        url = builder.url
        drawableId = builder.drawableId
        imageView = builder.imageView
        placeholder = builder.placeholder
        placeHolderDrawable = builder.placeholderDrawable
        errorPic = builder.errorPic
        cacheStrategy = builder.cacheStrategy
        transformation = builder.transformation
        imageViews = builder.imageViews
        isClearMemory = builder.isClearMemory
        isClearDiskCache = builder.isClearDiskCache
        resizeX = builder.resizeX
        resizeY = builder.resizeY
        isCropCenter = builder.isCropCenter
        isCropCircle = builder.isCropCircle
        formatType = builder.formatType
        isFitCenter = builder.isFitCenter
        isCrossFade = builder.isCrossFade
        imageRadius = builder.imageRadius
        blurValue = builder.blurValue
        requestListener = builder.requestListener
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    val isBlurImage: Boolean
        get() = blurValue > 0

    fun isImageRadius(): Boolean {
        return imageRadius > 0
    }

    class Builder {
        var resizeX = 0
        var url: String? = null
        var drawableId = 0
        var imageView: ImageView? = null
        var placeholder = 0
        var placeholderDrawable: Drawable? = null
        var errorPic = 0
        var cacheStrategy = 0
        var imageRadius = 0
        var blurValue = 0
        var transformation: Array<out BitmapTransformation>? = null
        var imageViews: Array<out ImageView>? = null
        var isClearMemory = false
        var isClearDiskCache = false
        var isCropCenter = false
        var isCropCircle = false
        var isCrossFade = false
        var formatType: DecodeFormat? = null
        var isFitCenter = false
        var resizeY = 0
        var requestListener: RequestListener<Drawable?>? = null
        fun url(url: String?): Builder {
            this.url = url
            return this
        }

        fun drawableId(drawableId: Int): Builder {
            this.drawableId = drawableId
            return this
        }

        fun placeholder(placeholder: Int): Builder {
            this.placeholder = placeholder
            return this
        }

        fun errorPic(errorPic: Int): Builder {
            this.errorPic = errorPic
            return this
        }

        fun imageView(imageView: ImageView?): Builder {
            this.imageView = imageView
            return this
        }

        fun cacheStrategy(cacheStrategy: Int): Builder {
            this.cacheStrategy = cacheStrategy
            return this
        }

        fun imageRadius(imageRadius: Int): Builder {
            this.imageRadius = imageRadius
            return this
        }

        fun blurValue(blurValue: Int): Builder { // blurValue 建议设置为 15
            this.blurValue = blurValue
            return this
        }

        fun isCrossFade(isCrossFade: Boolean): Builder {
            this.isCrossFade = isCrossFade
            return this
        }

        fun transformation(vararg transformation: BitmapTransformation): Builder {
            this.transformation = transformation
            return this
        }

        fun imageViews(vararg imageViews: ImageView): Builder {
            this.imageViews = imageViews
            return this
        }

        fun isClearMemory(isClearMemory: Boolean): Builder {
            this.isClearMemory = isClearMemory
            return this
        }

        fun isClearDiskCache(isClearDiskCache: Boolean): Builder {
            this.isClearDiskCache = isClearDiskCache
            return this
        }

        fun placeholderDrawable(placeholderDrawble: Drawable?): Builder {
            placeholderDrawable = placeholderDrawble
            return this
        }

        fun resize(resizeX: Int, resizeY: Int): Builder {
            this.resizeX = resizeX
            this.resizeY = resizeY
            return this
        }

        fun isCropCenter(isCropCenter: Boolean): Builder {
            this.isCropCenter = isCropCenter
            return this
        }

        fun isCropCircle(isCropCircle: Boolean): Builder {
            this.isCropCircle = isCropCircle
            return this
        }

        fun setDecodeFormate(decodeFormat: DecodeFormat?): Builder {
            formatType = decodeFormat
            return this
        }

        fun isFitCenter(isFitCenter: Boolean): Builder {
            this.isFitCenter = isFitCenter
            return this
        }

        fun requestListener(requestListener: RequestListener<Drawable?>?): Builder {
            this.requestListener = requestListener
            return this
        }

        fun build(): GlideConfigImpl {
            return GlideConfigImpl(this)
        }
    }

}
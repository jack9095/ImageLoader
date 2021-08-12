package com.kuanquan.glidelibrary.core

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.kuanquan.glidelibrary.network.OkHttpUrlLoader
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.InputStream


@GlideModule(glideName = "CommonGlideApp")
class CommonGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDiskCache(
            InternalCacheDiskCacheFactory(context, GLIDE_CACHE_DIR, IMAGE_DISK_CACHE_MAX_SIZE.toLong())
//            ExternalPreferredCacheDiskCacheFactory(context, GLIDE_CACHE_DIR, IMAGE_DISK_CACHE_MAX_SIZE.toLong())
        )
        val calculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize
        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toInt()
        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toInt()
        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize.toLong()))

        // 这里要谨慎，静图可以设置为 PREFER_RGB_565， Gif图 必须要设置成 PREFER_ARGB_8888，否则会掉帧
//        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))
    }

    private var okHttpClient: Call.Factory = OkHttpClient.Builder().addNetworkInterceptor { chain: Interceptor.Chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        response.newBuilder().run {
            val body = response.body()
            if (body != null) {
                this.body(body)
            }
            this.build()
        }
    }.build()

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    companion object {
        /**
         * 图片缓存文件最大值为500Mb， 根据自己的需求进行修改
         */
        private const val IMAGE_DISK_CACHE_MAX_SIZE = 500 * 1024 * 1024

        /**
         * 图片缓存子目录，可随意设置
         */
        private const val GLIDE_CACHE_DIR = "image-cache"
    }
}
package com.kuanquan.glidelibrary.network

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoader.LoadData
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * 使用OkHttp通过http/https获取媒体的简单模型加载器
 */
class OkHttpUrlLoader(private val client: Call.Factory) : ModelLoader<GlideUrl, InputStream> {

    override fun handles(url: GlideUrl): Boolean {
        return true
    }

    override fun buildLoadData(
        model: GlideUrl, width: Int, height: Int,
        options: Options
    ): LoadData<InputStream> {
        return LoadData(model, OkHttpStreamFetcher(client, model))
    }

    class Factory @JvmOverloads constructor(private val client: Call.Factory = internalClient!!) :
        ModelLoaderFactory<GlideUrl, InputStream> {


        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideUrl, InputStream> {
            return OkHttpUrlLoader(client)
        }

        override fun teardown() {
            // 不执行任何操作
        }

        companion object {
            @Volatile
            private var internalClient: Call.Factory? = null
                get() {
                    if (field == null) {
                        synchronized(Factory::class.java) {
                            if (field == null) {
                                field = OkHttpClient()
                            }
                        }
                    }
                    return field
                }
        }
    }

}
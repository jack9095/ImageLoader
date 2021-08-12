package com.kuanquan.glidelibrary.network

import android.util.Log
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.data.DataFetcher.DataCallback
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.util.ContentLengthInputStream
import com.bumptech.glide.util.Preconditions
import okhttp3.*
import java.io.IOException
import java.io.InputStream

/**
 * 使用okhttp库获取[InputStream]
 */
class OkHttpStreamFetcher(private val client: Call.Factory, private val url: GlideUrl) : DataFetcher<InputStream>, Callback {
    private var stream: InputStream? = null
    private var responseBody: ResponseBody? = null
    private var callback: DataCallback<in InputStream>? = null

    @Volatile
    private var call: Call? = null
    override fun loadData(priority: Priority,
                          callback: DataCallback<in InputStream>) {
        val requestBuilder = Request.Builder().url(url.toStringUrl())
        for ((key, value) in url.headers) {
            requestBuilder.addHeader(key, value)
        }
        val request = requestBuilder.build()
        this.callback = callback
        call = client.newCall(request)
        call?.enqueue(this)
    }

    override fun onFailure(call: Call, e: IOException) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "OkHttp failed to obtain result", e)
        }
        callback?.onLoadFailed(e)
    }

    override fun onResponse(call: Call, response: Response) {
        responseBody = response.body()
        if (response.isSuccessful) {
            val contentLength = Preconditions.checkNotNull(responseBody).contentLength()
            stream = responseBody?.byteStream()?.let { ContentLengthInputStream.obtain(it, contentLength) }
            callback?.onDataReady(stream)
        } else {
            callback?.onLoadFailed(HttpException(response.message(), response.code()))
        }
    }

    override fun cleanup() {
        try {
            stream?.close()
        } catch (e: IOException) {
            // Ignored
        }
        responseBody?.close()
        callback = null
    }

    override fun cancel() {
        call?.cancel()
    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.REMOTE
    }

    companion object {
        private const val TAG = "OkHttpFetcher"
    }

}
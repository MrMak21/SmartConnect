package gr.makris.smartconnect.extensions.network

import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import retrofit2.HttpException
import java.io.IOException

fun Throwable?.getResponseString(): String? {
    val httpException = this as? HttpException ?: return null
    val responseErrorBody = httpException.response()?.errorBody() ?: return null
    return responseErrorBody.peekBody()
}

@Throws(IOException::class)
private fun ResponseBody.peekBody(byteCount: Long = Long.MAX_VALUE): String {
    val peeked: BufferedSource = source().peek()
    val buffer = Buffer()
    peeked.request(byteCount)
    buffer.write(peeked, Math.min(byteCount, peeked.buffer.size))
    return ResponseBody.create(contentType(), buffer.size, buffer).string()
}
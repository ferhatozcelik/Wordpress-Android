package org.ferhatozcelik.util

import android.content.Context
import java.io.InputStream

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-02
 */

object HtmlHelper {

    fun String.htmlInject(context: Context): String {

        val inputStreamStart: InputStream = context.assets.open("template_start.html")
        val inputStreamEnd: InputStream = context.assets.open("template_end.html")

        val sizeStart: Int = inputStreamStart.available()
        val sizeEnd: Int = inputStreamEnd.available()

        val bufferStart = ByteArray(sizeStart)
        val bufferEnd = ByteArray(sizeEnd)

        inputStreamStart.read(bufferStart)
        inputStreamStart.read(bufferEnd)

        inputStreamEnd.close()
        inputStreamEnd.close()

        var html = (String(bufferStart) + this + String(bufferEnd))

        if (html.contains("<a href=")) {
            html = html.replace("<a.*?>".toRegex(), "").replace("</a>", "");
        }

        return html
    }

}
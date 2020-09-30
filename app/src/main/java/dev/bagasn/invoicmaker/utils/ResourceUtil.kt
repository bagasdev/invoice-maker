package dev.bagasn.invoicmaker.utils

import android.content.Context
import android.os.Build
import androidx.annotation.ColorRes

class ResourceUtil {

    companion object {

        fun getColor(context: Context, @ColorRes color: Int): Int {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                return context.getColor(color)
            return context.resources.getColor(color)
        }

    }

}
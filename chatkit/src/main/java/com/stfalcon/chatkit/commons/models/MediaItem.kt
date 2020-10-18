package com.stfalcon.chatkit.commons.models

import android.graphics.Rect
import android.graphics.drawable.Drawable
import java.net.URL

interface MediaItem {

    val url: URL

    val image: Drawable?

    val placeholderImage: Drawable

    val size: Rect
}

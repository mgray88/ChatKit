package com.stfalcon.chatkit.utils

import android.content.res.Resources
import android.util.TypedValue

val Int.dp get() = this.toFloat().dp.toInt()
val Float.dp get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)
val Double.dp get() = this.toFloat().dp.toDouble()

val Int.sp get() = this.toFloat().sp.toInt()
val Float.sp get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)
val Double.sp get() = this.toFloat().sp.toDouble()

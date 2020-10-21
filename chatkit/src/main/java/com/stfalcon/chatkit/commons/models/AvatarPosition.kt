package com.stfalcon.chatkit.commons.models

data class AvatarPosition(
    val horizontal: Horizontal,
    val vertical: Vertical,
) {
    constructor(vertical: Vertical) : this(Horizontal.Natural, vertical)

    sealed class Horizontal {
        object CellLeading: Horizontal()
        object CellTrailing: Horizontal()
        object Natural: Horizontal()
    }

    sealed class Vertical {
        object CellTop: Vertical()
        object MessageLabelTop: Vertical()
        object MessageTop: Vertical()
        object MessageCenter: Vertical()
        object MessageBottom: Vertical()
        object MessageLabelBottom: Vertical()
        object CellBottom: Vertical()
    }
}

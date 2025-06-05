package com.drygin.popcornplan.common.domain.model

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
enum class ImageType(val typeName: String) {
    FANART("fanart"),
    POSTER("poster"),
    LOGO("logo"),
    CLEARART("clearart"),
    BANNER("banner"),
    THUMB("thumb");

    companion object {
        fun from(typeName: String): ImageType? = entries.find { it.typeName == typeName }
    }
}
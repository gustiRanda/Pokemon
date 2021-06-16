package com.gmind.pokemon.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    var id: String,
    var candy: String,
    var img: String,
    var name: String,
    var weight: String,
    var height: String,
    val isFavorite: Boolean
) : Parcelable
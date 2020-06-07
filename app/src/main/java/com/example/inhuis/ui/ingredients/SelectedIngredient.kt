package com.example.inhuis.ui.ingredients

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class SelectedIngredient(
    var id: Int,
    var ingredient: @RawValue Ingredient
) : Parcelable {

}
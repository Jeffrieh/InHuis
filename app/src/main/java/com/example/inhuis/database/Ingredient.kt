package com.example.inhuis.database

import android.graphics.drawable.Drawable
import androidx.room.*

@Entity(tableName = "ingredient")
class Ingredient(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "amount_type") var amountType: amountTypes
) {
    constructor(
    ) : this("", 0, "", amountTypes.LITER)

    @Ignore
    var checked: Boolean = false

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun seChecked(value: Boolean) {
        this.checked = value;
    }
}

class AmountTypeConverter {

    @TypeConverter
    fun fromType(value: amountTypes): String {
        return value.value
    }

    @TypeConverter
    fun toType(value: String): amountTypes {
        return when (value) {
            "G" -> amountTypes.GRAM
            "pcs" -> amountTypes.PCS
            "L" -> amountTypes.LITER
            "KG" -> amountTypes.KILO
            "ml" -> amountTypes.ML
            "cloves" -> amountTypes.CLOVES
            else -> amountTypes.GRAM
        }
    }

}

enum class amountTypes(val value: String) {
    GRAM("g"),
    PCS(" pcs"),
    LITER("L"),
    KILO("KG"),
    ML("ml"),
    CLOVES("cloves"),
}
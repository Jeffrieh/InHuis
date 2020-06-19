package com.example.inhuis.database

import android.graphics.drawable.Drawable
import androidx.room.*

@Entity(tableName = "ingredient")
class Ingredient(
    @PrimaryKey @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "amount_type") var amountType: amountTypes = amountTypes.GRAM
) {
    constructor(
    ) : this("", 0, "", amountTypes.GRAM)

    @Ignore
    var checked: Boolean = false


    fun seChecked(value: Boolean) {
        this.checked = value;
    }
}

class AmountTypeConverter {

    @TypeConverter
    fun fromType(value: amountTypes): String {
        return value.name
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

enum class amountTypes(val value: Int) {
    GRAM(0),
    PCS(1),
    LITER(2),
    KILO(3),
    ML(4),
    CLOVES(5),
}
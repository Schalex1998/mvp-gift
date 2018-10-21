package io.alexanderschaefer.u2764.model.database;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public int giftStateToInt(Gift.GiftState giftState) {
        return giftState.ordinal();
    }

    @TypeConverter
    public Gift.GiftState intToGiftState(int i) {
        return Gift.GiftState.values()[i];
    }
}

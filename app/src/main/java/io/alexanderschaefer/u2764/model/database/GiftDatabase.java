package io.alexanderschaefer.u2764.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {
        Gift.class,
        Challenge.class
}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class GiftDatabase extends RoomDatabase {

    public abstract GiftDao giftDao();
}

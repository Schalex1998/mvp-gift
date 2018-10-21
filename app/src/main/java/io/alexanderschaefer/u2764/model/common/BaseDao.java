package io.alexanderschaefer.u2764.model.common;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

public interface BaseDao<T> {

    @Insert(onConflict = REPLACE)
    void insert(T data);

    @Insert(onConflict = REPLACE)
    void insertAll(T... data);

    @Update
    void update(T data);

    @Delete
    void delete(T data);
}

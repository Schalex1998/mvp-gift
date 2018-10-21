package io.alexanderschaefer.u2764.model.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import io.alexanderschaefer.u2764.model.common.BaseDao;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface GiftDao extends BaseDao<Gift> {
    
    @Insert(onConflict = REPLACE)
    void insert(Challenge challenge);

    @Insert(onConflict = REPLACE)
    void insertAll(Challenge... challenges);

    @Transaction
    @Query("SELECT * FROM gifts ORDER BY id ASC")
    LiveData<List<GiftWithChallenges>> loadAll();

    @Transaction
    @Query("SELECT * FROM gifts WHERE id = :id LIMIT 1")
    LiveData<GiftWithChallenges> load(String id);
}

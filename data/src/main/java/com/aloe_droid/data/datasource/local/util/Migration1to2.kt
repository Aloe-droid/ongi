package com.aloe_droid.data.datasource.local.util

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migration1to2 : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // requestRoute 컬럼을 제거하기 위해 새 테이블을 생성하고 데이터를 복사
        db.execSQL("""
                    CREATE TABLE store_queries_new (
                        id TEXT NOT NULL PRIMARY KEY,
                        latitude REAL NOT NULL,
                        longitude REAL NOT NULL,
                        searchQuery TEXT NOT NULL,
                        category TEXT NOT NULL,
                        sortType TEXT NOT NULL,
                        distanceRange TEXT NOT NULL,
                        onlyFavorites INTEGER NOT NULL,
                        queryTime INTEGER NOT NULL
                    )
                """.trimIndent())

        // 기존 데이터를 새 테이블로 복사 (requestRoute 컬럼 제외)
        db.execSQL("""
                    INSERT INTO store_queries_new (
                        id, latitude, longitude, searchQuery, category, 
                        sortType, distanceRange, onlyFavorites, queryTime
                    )
                    SELECT 
                        id, latitude, longitude, searchQuery, category,
                        sortType, distanceRange, onlyFavorites, queryTime
                    FROM store_queries
                """.trimIndent())

        // 기존 테이블 삭제
        db.execSQL("DROP TABLE store_queries")

        // 새 테이블을 원래 이름으로 변경
        db.execSQL("ALTER TABLE store_queries_new RENAME TO store_queries")
    }
}

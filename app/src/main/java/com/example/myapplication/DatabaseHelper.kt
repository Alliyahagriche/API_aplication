package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.placeholder.PostRespoon

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "posts_db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_POSTS = "posts"
        private const val KEY_ID = "id"
        private const val KEY_USER_ID = "userId"
        private const val KEY_TITLE = "title"
        private const val KEY_BODY = "body"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_POSTS($KEY_ID INTEGER PRIMARY KEY, $KEY_USER_ID INTEGER, $KEY_TITLE TEXT, $KEY_BODY TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_POSTS")
        onCreate(db)
    }

    fun insertPost(post: PostRespoon) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_ID, post.id)
        values.put(KEY_USER_ID, post.userId)
        values.put(KEY_TITLE, post.title)
        values.put(KEY_BODY, post.body)
        db.insertWithOnConflict(TABLE_POSTS, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    fun getAllPosts(): List<PostRespoon> {
        val posts = mutableListOf<PostRespoon>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_POSTS", null)
        if (cursor.moveToFirst()) {
            do {
                val post = PostRespoon(
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_USER_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_BODY))
                )
                posts.add(post)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return posts
    }
}

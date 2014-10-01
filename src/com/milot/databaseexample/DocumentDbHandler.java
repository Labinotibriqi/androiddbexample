package com.milot.databaseexample;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DocumentDbHandler extends SQLiteOpenHelper {
	 
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "documentsManager";
    private static final String TABLE_DOCUMENTS = "documents";
     
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PATH = "path";
 
    public DocumentDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DOCUMENTS_TABLE = "CREATE TABLE " + TABLE_DOCUMENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PATH + " TEXT" + ")";
        db.execSQL(CREATE_DOCUMENTS_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	
    	// Duhet me pase shume kujdes! Duhet me u shkru 1 algoritem ma i mire i migrimit.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENTS);

        // I krijon prej fillimit
        onCreate(db);
    }
 
    void addDocument(Document document) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, document.getName());
        values.put(KEY_PATH, document.getPath());
 
        db.insert(TABLE_DOCUMENTS, null, values);
        db.close();
    }
 
    Document getDocument(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_DOCUMENTS, new String[] { KEY_ID,
                KEY_NAME, KEY_PATH }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Document document = new Document(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        
        return document;
    }
     
    public List<Document> getAllDocuments() {
        List<Document> documentsList = new ArrayList<Document>();
        // Select Query
        String selectQuery = "SELECT  * FROM " + TABLE_DOCUMENTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
            	Document document = new Document();
                document.setID(Integer.parseInt(cursor.getString(0)));
                document.setName(cursor.getString(1));
                document.setPath(cursor.getString(2));
                
                documentsList.add(document);
            } while (cursor.moveToNext());
        }
 
        return documentsList;
    }
 
    public int updateDocument(Document document) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, document.getName());
        values.put(KEY_PATH, document.getPath());
 
        // updating row
        return db.update(TABLE_DOCUMENTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(document.getID()) });
    }
 
    public void deleteDocument(Document document) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOCUMENTS, KEY_ID + " = ?",
                new String[] { String.valueOf(document.getID()) });
        db.close();
    }
 
 
    public int getDocumentsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DOCUMENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        return cursor.getCount();
    }
 
}

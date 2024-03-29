package com.example.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    private static final int VERSION= 1;
    private static final String DATABASE_NAME="Contact_Manager";

    public MyDatabase(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String script="Create table contact(id INTEGER primary key,name TEXT, phone TEXT, fav INTEGER, image INTEGER, email TEXT)";
        db.execSQL(script);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    public void addContact(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("name",contact.getName());
        values.put("phone",contact.getPhone());
        values.put("fav",contact.getFav());
        values.put("image",contact.getImage());
        values.put("email",contact.getEmail());
        db.insert("contact",null,values);
        db.close();
    }

    public void deleteContact(Contact contact){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete("contact","id=?",new String[]{String.valueOf(contact.getId())});
    }
    public int updateContact(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("name",contact.getName());
        values.put("phone",contact.getPhone());
        values.put("fav",contact.getFav());
        values.put("image",contact.getImage());
        values.put("email",contact.getEmail());
        return db.update("contact", values,"id=?",new String[]{String.valueOf(contact.getId())});
    }
    public ArrayList<Contact> getAllContact(){
        ArrayList<Contact> contacts= new ArrayList<Contact>();
        String script="select * from contact";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor= db.rawQuery(script,null);
        while(cursor.moveToNext()){
            Contact contact=new Contact();
            contact.setId(cursor.getInt(0));
            contact.setName(cursor.getString(1));
            contact.setPhone(cursor.getString(2));
            contact.setFav(cursor.getInt(3));
            contact.setImage(cursor.getInt(4));
            contact.setEmail(cursor.getString(5));
            contacts.add(contact);
        }
        return contacts;
    }
}

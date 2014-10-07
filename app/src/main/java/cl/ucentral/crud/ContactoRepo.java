package cl.ucentral.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;

public class ContactoRepo {
    private DBHelper dbHelper;

    public ContactoRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Contacto student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contacto.KEY_fono, student.fono);
        values.put(Contacto.KEY_apellido,student.apellido);
        values.put(Contacto.KEY_nombre, student.nombre);

        // Inserting Row
        long contacto_Id = db.insert(Contacto.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) contacto_Id;
    }

    public void delete(int student_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Contacto.TABLE, Contacto.KEY_ID + "= ?", new String[] { String.valueOf(student_Id) });
        db.close(); // Closing database connection
    }

    public void update(Contacto student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contacto.KEY_fono, student.fono);
        values.put(Contacto.KEY_apellido,student.apellido);
        values.put(Contacto.KEY_nombre, student.nombre);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Contacto.TABLE, values, Contacto.KEY_ID + "= ?", new String[] { String.valueOf(student.contacto_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>>  getListaContactos() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Contacto.KEY_ID + "," +
                Contacto.KEY_nombre + "," +
                Contacto.KEY_apellido + "," +
                Contacto.KEY_fono +
                " FROM " + Contacto.TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> listaContactos = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("id", cursor.getString(cursor.getColumnIndex(Contacto.KEY_ID)));
                student.put("nombre", cursor.getString(cursor.getColumnIndex(Contacto.KEY_nombre)));
                listaContactos.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaContactos;

    }

    public Contacto getContactoPorID(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Contacto.KEY_ID + "," +
                Contacto.KEY_nombre + "," +
                Contacto.KEY_apellido + "," +
                Contacto.KEY_fono +
                " FROM " + Contacto.TABLE
                + " WHERE " +
                Contacto.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Contacto contacto = new Contacto();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                contacto.contacto_ID =cursor.getInt(cursor.getColumnIndex(Contacto.KEY_ID));
                contacto.nombre =cursor.getString(cursor.getColumnIndex(Contacto.KEY_nombre));
                contacto.apellido  =cursor.getString(cursor.getColumnIndex(Contacto.KEY_apellido));
                contacto.fono =cursor.getInt(cursor.getColumnIndex(Contacto.KEY_fono));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contacto;
    }

}

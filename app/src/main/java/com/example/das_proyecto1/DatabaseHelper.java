package com.example.das_proyecto1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper myDatabaseHelper = null;

    // Nombre de la base de datos
    private static final String DATABASE_NAME = "habitos_db";

    // Versión de la base de datos (cambia si modificas la estructura)
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas
    private static final String TABLE_HABITOS = "habitos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_IMAGEN = "imagen";
    private static final String COLUMN_TEXTO = "texto";
    private static final String COLUMN_FECHA_HORA = "fecha_hora";
    // Consulta SQL para crear la tabla
    private static final String CREATE_TABLE_HABITOS = "CREATE TABLE " + TABLE_HABITOS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            //+ COLUMN_IMAGEN + " TEXT,"
            + COLUMN_TEXTO + " TEXT,"
            + COLUMN_FECHA_HORA + " DATE"
            + ")";

    public static DatabaseHelper getMyDatabaseHelper(Context context) {
        if (myDatabaseHelper == null) {
            myDatabaseHelper = new DatabaseHelper(context);
        }
        return myDatabaseHelper;
    }
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Ejecutar la consulta SQL para crear la tabla
        db.execSQL(CREATE_TABLE_HABITOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes implementar la lógica para manejar la actualización de la base de datos
        // Por simplicidad, en este ejemplo simplemente eliminamos la tabla y la volvemos a crear
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITOS);
        onCreate(db);
    }

    // Método para obtener todos los hábitos almacenados en la base de datos
    public List<Habit> getAllHabits() {
        List<Habit> habitList = new ArrayList<>();

        // Seleccionar toda la información de la tabla habits
        String selectQuery = "SELECT * FROM " + TABLE_HABITOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Verificar si el cursor contiene columnas válidas
        if (cursor != null && cursor.getCount() > 0) {
            // Recorrer el cursor y agregar cada hábito a la lista
            while (cursor.moveToNext()) {
                Habit habit = new Habit();
                habit.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                //habit.setImagen(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGEN)));
                habit.setTexto(cursor.getString(cursor.getColumnIndex(COLUMN_TEXTO)));
                habit.setFechaHora(cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_HORA)));

                habitList.add(habit);
            }
            // Cerrar el cursor
            cursor.close();
        }
        // Cerrar la base de datos
        db.close();
        return habitList;
    }

    public boolean addNewHabit(String habitText, Date datetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXTO, habitText);
        // Convert the Date object to a suitable string format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datetimeString = dateFormat.format(datetime);
        values.put(COLUMN_FECHA_HORA, datetimeString);

        // Insert the new habit into the database
        long result = db.insert(TABLE_HABITOS, null, values);

        // Close the database connection
        db.close();

        // Check if the habit was inserted successfully
        return result != -1;
    }

    public boolean deleteHabit(int habitId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Define the WHERE clause to delete the habit with the given ID
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(habitId)};

        // Execute the delete query
        int deletedRows = db.delete(TABLE_HABITOS, selection, selectionArgs);

        // Close the database connection
        db.close();

        // Check if any rows were deleted
        return deletedRows > 0;
    }

    public boolean editHabit(int habitId, String text, Date date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXTO, text);
        // Convert the Date object to a suitable string format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        values.put(COLUMN_FECHA_HORA, dateString);

        // Define the WHERE clause to update the habit with the given ID
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(habitId)};

        // Execute the update query
        int updatedRows = db.update(TABLE_HABITOS, values, selection, selectionArgs);

        // Close the database connection
        db.close();

        // Check if any rows were updated
        return updatedRows > 0;
    }



}
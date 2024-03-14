package com.example.das_proyecto1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.das_proyecto1.Utils;

import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private List<Habit> habitList;
    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter;
    private DatabaseHelper databaseHelper;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLanguage(this);
        // Theme
        Utils.loadTheme(this);
        setContentView(R.layout.activity_home);

        // +---------+
        // | Toolbar |
        // +---------+
        // Referencia al Toolbar en el layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Configurar el Toolbar como la barra de acción de la actividad
        Utils.configToolbar(this, toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewHabits);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Inicializar el DatabaseHelper
        databaseHelper = DatabaseHelper.getMyDatabaseHelper(this);

        // Obtener hábitos de la base de datos y establecer el adaptador
        habitList = databaseHelper.getAllHabits();
        habitAdapter = new HabitAdapter(this, habitList);
        recyclerView.setAdapter(habitAdapter);

        Button buttonAddHabit = findViewById(R.id.button_add_habit);
        buttonAddHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.dialogNewHabit(HomeActivity.this, habitList, habitAdapter);
            }
        });
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLanguage(this);
        // Theme
        Utils.loadTheme(this);
        setContentView(R.layout.activity_home);

        // Referencia al Toolbar en el layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Configurar el Toolbar como la barra de acción de la actividad
        Utils.configToolbar(this, toolbar);

        recyclerView = findViewById(R.id.recyclerViewHabits);
        // Determina la cantidad de columnas basado en la orientación de la pantalla
        int columnas = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, columnas));

        // Inicializar el DatabaseHelper
        databaseHelper = DatabaseHelper.getMyDatabaseHelper(this);

        // Obtener hábitos de la base de datos y establecer el adaptador
        habitList = databaseHelper.getAllHabits();
        habitAdapter = new HabitAdapter(this, habitList);
        recyclerView.setAdapter(habitAdapter);

        Button buttonAddHabit = findViewById(R.id.button_add_habit);
        buttonAddHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.dialogNewHabit(HomeActivity.this, habitList, habitAdapter);
            }
        });

        Button openCalendarButton = findViewById(R.id.button_open_calendar);
        openCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para abrir el calendario
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_CALENDAR);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Load menu.xml into toolbar
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return Utils.toolbarItemClick(item, this) || super.onOptionsItemSelected(item);
    }

}

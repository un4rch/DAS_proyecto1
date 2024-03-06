package com.example.das_proyecto1;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.example.das_proyecto1.Utils;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // +---------+
        // | Toolbar |
        // +---------+
        // Referencia al Toolbar en el layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Configurar el Toolbar como la barra de acción de la actividad
        Utils.configToolbar(this, toolbar);

        // +----------+
        // | Language |
        // +----------+
        Utils.loadLanguage(this);
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

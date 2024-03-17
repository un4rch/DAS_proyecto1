package com.example.das_proyecto1;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.icu.util.Calendar;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {
    public static void configToolbar(AppCompatActivity activity, Toolbar toolbar) {
        // Set toolbar as Action Bar
        activity.setSupportActionBar(toolbar);
        // Disable title
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public static boolean toolbarItemClick(MenuItem item, AppCompatActivity activity) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_habits) {
            if (!(activity instanceof HomeActivity)) {
                activity.startActivity(new Intent(activity, HomeActivity.class));
                return true;
            }
        } else if (itemId == R.id.menu_settings) {
            if (!(activity instanceof SettingsActivity)) {
                activity.startActivity(new Intent(activity, SettingsActivity.class));
                return true;
            }
        }
        return false;
    }

    public static void loadLanguage(AppCompatActivity activity) {
        // Obtener las preferencias
        SharedPreferences sharedPreferences = activity.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        // Obtener el idioma guardado en preferencias, y sino, por defecto.
        String langPref = sharedPreferences.getString("lang", "eu");
        Locale nuevaloc = new Locale(langPref);
        Locale.setDefault(nuevaloc);
        Configuration configuration = activity.getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);
        Context context = activity.getBaseContext().createConfigurationContext(configuration);
        activity.getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }

    public static void loadTheme(AppCompatActivity activity) {
        // Obtener las preferencias
        SharedPreferences sharedPreferences = activity.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        // Obtener el idioma guardado en preferencias, y sino, por defecto.
        String themePref = sharedPreferences.getString("theme", "BrightMode");
        if (themePref.equals("Bright Mode") || themePref.equals("Modo Claro") || themePref.equals("Itxura Argia")) {
            activity.setTheme(R.style.BrightMode);
        } else {
            activity.setTheme(R.style.DarkMode);
        }
    }

    public static void sendNotification(AppCompatActivity context, String title, String message) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED) {
            // PEDIR EL PERMISO
            ActivityCompat.requestPermissions(context, new
                    String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 11);
        }

        NotificationManager elManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(context, "channelId");

        // Crear el canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel elCanal = new NotificationChannel("channelId", "ChannelName",
                    NotificationManager.IMPORTANCE_DEFAULT);
            elManager.createNotificationChannel(elCanal);
        }

        // Configurar el NotificationCompat.Builder
        elBuilder.setSmallIcon(android.R.drawable.stat_sys_warning)
                .setContentTitle(title)
                .setContentText(message)
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setAutoCancel(true);

        // Acción para ver el carrito
        Intent verIntent = new Intent(context, HomeActivity.class);
        verIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        verIntent.setAction(context.getString(R.string.notification_show_app));

        // Usar FLAG_IMMUTABLE o FLAG_MUTABLE aquí
        PendingIntent verPendingIntent = PendingIntent.getActivity(context, 0, verIntent, PendingIntent.FLAG_IMMUTABLE);

        // Agregar acciones a la notificación
        elBuilder.addAction(android.R.drawable.ic_menu_revert, context.getString(R.string.notification_open_app), verPendingIntent);

        elManager.notify(1, elBuilder.build());
    }

    public static void dialogNewHabit(AppCompatActivity context, final List<Habit> habitList, final RecyclerView.Adapter adapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_new_habit, null);
        builder.setView(dialogView);

        final EditText editTextHabitName = dialogView.findViewById(R.id.editText_habit_name);
        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        String positiveButtonText = context.getString(R.string.positive_button_text);
        String negativeButtonText = context.getString(R.string.negative_button_text);
        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String habitName = editTextHabitName.getText().toString();

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                Date habitDate = calendar.getTime();

                // Save the habit into the database
                DatabaseHelper databaseHelper = DatabaseHelper.getMyDatabaseHelper(context);
                databaseHelper.addNewHabit(habitName, habitDate);

                // Refresh the habit list
                habitList.clear();
                habitList.addAll(databaseHelper.getAllHabits());
                adapter.notifyDataSetChanged();

                String messageSaveHabit = context.getString(R.string.message_save_habit);
                String titleSaveHabit = context.getString(R.string.title_save_habit);

                // Show a toast message
                Toast.makeText(context, messageSaveHabit, Toast.LENGTH_SHORT).show();

                // Show notification
                sendNotification(context, titleSaveHabit, messageSaveHabit);
            }
        });

        builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void dialogEditHabit(AppCompatActivity context, final List<Habit> habitList, final RecyclerView.Adapter adapter, int habitId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_new_habit, null);
        builder.setView(dialogView);

        final EditText editTextHabitName = dialogView.findViewById(R.id.editText_habit_name);
        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        String positiveButtonText = context.getString(R.string.positive_button_text);
        String negativeButtonText = context.getString(R.string.negative_button_text);

        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String habitName = editTextHabitName.getText().toString();

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                Date habitDate = calendar.getTime();

                // Save the habit into the database
                DatabaseHelper databaseHelper = DatabaseHelper.getMyDatabaseHelper(context);
                databaseHelper.editHabit(habitId,habitName, habitDate);

                // Refresh the habit list
                habitList.clear();
                habitList.addAll(databaseHelper.getAllHabits());
                adapter.notifyDataSetChanged();

                String messageEditHabit = context.getString(R.string.message_edit_habit);
                String titleEditHabit = context.getString(R.string.title_edit_habit);

                // Show a toast message
                Toast.makeText(context, messageEditHabit, Toast.LENGTH_SHORT).show();

                // Show notification
                sendNotification(context, titleEditHabit, messageEditHabit);
            }
        });

        builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

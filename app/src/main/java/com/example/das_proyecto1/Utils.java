package com.example.das_proyecto1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

        /*switch (itemId) {
            case R.id.menu_habits: {
                if (!(activity instanceof HomeActivity)) {
                    activity.startActivity(new Intent(activity, HomeActivity.class));
                    return true;
                }
            }
            case R.id.menu_settings: {
                if (!(activity instanceof SettingsActivity)) {
                    activity.startActivity(new Intent(activity, SettingsActivity.class));
                    return true;
                }
            }
        }
        return false;*/
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

    }

    public static void changeLanguage(AppCompatActivity activity, String lang) {
        Locale nuevaloc = new Locale(lang);
        Locale.setDefault(nuevaloc);
        Configuration configuration = activity.getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);
        Context context = activity.getBaseContext().createConfigurationContext(configuration);
        activity.getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        activity.finish();
        activity.startActivity(activity.getIntent());
    }
}

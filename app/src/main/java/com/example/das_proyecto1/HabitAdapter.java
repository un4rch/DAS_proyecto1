package com.example.das_proyecto1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.das_proyecto1.Habit;
import com.example.das_proyecto1.R;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {
    private List<Habit> habitList;
    private AppCompatActivity context;

    public HabitAdapter(AppCompatActivity context, List<Habit> habitList) {
        this.context = context;
        this.habitList = habitList;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_layout, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = habitList.get(position);
        holder.bind(habit);

        // Set click listeners for buttons
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Access the ID of the current habit
                int habitId = habit.getId();
                Utils.dialogEditHabit(context, habitList, HabitAdapter.this, habitId);
                // Handle button edit click
                // You can perform any action you want here
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Access the ID of the current habit
                int habitId = habit.getId();
                DatabaseHelper.getMyDatabaseHelper(context).deleteHabit(habitId);

                String messageDeleteHabit = context.getString(R.string.message_delete_habit);
                String titleDeleteHabit = context.getString(R.string.title_delete_habit);

                Utils.sendNotification(context, titleDeleteHabit, messageDeleteHabit);
                // Show a toast message
                Toast.makeText(context, messageDeleteHabit, Toast.LENGTH_SHORT).show();
                context.finish();
                context.startActivity(context.getIntent());
                // Handle button delete click
                // You can perform any action you want here
            }
        });
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    public class HabitViewHolder extends RecyclerView.ViewHolder {
        private TextView habitText;
        private TextView habitDateTime;
        private Button buttonEdit;
        private Button buttonDelete;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            habitText = itemView.findViewById(R.id.habit_text);
            habitDateTime = itemView.findViewById(R.id.habit_datetime);
            buttonEdit = itemView.findViewById(R.id.button_edit_habit);
            buttonDelete = itemView.findViewById(R.id.button_delete_habit);
        }

        public void bind(Habit habit) {
            // Bind data to views
            habitText.setText(habit.getTexto());
            habitDateTime.setText(habit.getFechaHora());
        }
    }
}

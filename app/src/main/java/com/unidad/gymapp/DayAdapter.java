package com.unidad.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private final List<Day> days;
    private final Context context;

    public DayAdapter(Context context, List<Day> days) {
        this.context = context;
        this.days = days;
    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayViewHolder holder, int position) {
        Day day = days.get(position);
        holder.tvDayName.setText(day.name);

        // ✅ Verificar si el día fue completado
        String key = day.name.toLowerCase() + "_completado"; // ej: "lunes_completado"
        SharedPreferences prefs = context.getSharedPreferences("ProgressPrefs", Context.MODE_PRIVATE);
        boolean completado = prefs.getBoolean(key, false);

        // ✅ Cambiar color o ícono si está completado
        if (completado) {
            holder.tvDayName.setTextColor(ContextCompat.getColor(context, R.color.md_primary));
            holder.ivDayIcon.setImageResource(R.drawable.ic_check_circle); // usa un ícono de check
        } else {
            holder.tvDayName.setTextColor(Color.GRAY);
            holder.ivDayIcon.setImageResource(R.drawable.ic_circle_outline); // ícono neutral
        }

        // Navegación al día
        holder.itemView.setOnClickListener(v ->
                context.startActivity(new Intent(context, day.activityClass))
        );
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayName;
        ImageView ivDayIcon;

        DayViewHolder(View itemView) {
            super(itemView);
            tvDayName = itemView.findViewById(R.id.tvDayName);
            ivDayIcon = itemView.findViewById(R.id.ivDayIcon);
        }
    }
}

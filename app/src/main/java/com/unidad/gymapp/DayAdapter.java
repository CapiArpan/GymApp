package com.unidad.gymapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

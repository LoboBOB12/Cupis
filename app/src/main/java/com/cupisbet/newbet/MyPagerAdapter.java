package com.cupisbet.newbet;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyPagerAdapter extends RecyclerView.Adapter<MyPagerAdapter.MyViewHolder> {

    private List<Integer> layoutIds;

    public MyPagerAdapter(List<Integer> layoutIds) {
        this.layoutIds = layoutIds;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutIds.get(viewType), parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Do nothing - all binding is done in the MyViewHolder constructor
    }

    @Override
    public int getItemCount() {
        return layoutIds.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Do all view binding in the constructor
            if (itemView.getId() == R.layout.layout_1) {
                TextView textView = itemView.findViewById(R.id.text_view);
                textView.setText("Red");
                textView.setBackgroundColor(Color.RED);
            } else if (itemView.getId() == R.layout.layout_2) {
                TextView textView = itemView.findViewById(R.id.text_view);
                textView.setText("Green");
                textView.setBackgroundColor(Color.GREEN);
            }
        }
    }
}

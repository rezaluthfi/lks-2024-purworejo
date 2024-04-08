package com.example.movieapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainModel.Result> results;
    private onAdapterListener listener;
    public MainAdapter(List<MainModel.Result> results, onAdapterListener listener) {
        this.results = results;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder (
            LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        MainModel.Result result = results.get(position);
        holder.title.setText(result.getTitle());
        Picasso.get()
                .load(result.getImage())
                .fit().centerCrop()
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(result);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgMovie);
            title = itemView.findViewById(R.id.tvTitle);
        }
    }

    public void setData(List<MainModel.Result> data) {
        results.clear();
        results.addAll(data);
        notifyDataSetChanged();
    }

    //ketika diklik, kita dapet data2nya
    interface onAdapterListener {
        void onClick(MainModel.Result result);
    }


}

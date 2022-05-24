package com.example.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "RecyclerAdapter";

    List<Movie> movies;
    List<Movie> moviesAll;



    public RecyclerAdapter(List<Movie> movies) {


        this.movies = movies;
        moviesAll = new ArrayList<>();
        moviesAll.addAll(movies);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.movieDateTextView.setText(movies.get(position).getDate() );
        holder.textView.setText(movies.get(position).getTitle()  );

        if(movies.get(position).getImageId() == "batman") {
            holder.imageView.setImageResource(R.drawable.batman);
        }
        if(movies.get(position).getImageId() == "superman") {
            holder.imageView.setImageResource(R.drawable.superman);
        }
        if(movies.get(position).getImageId() == "strange") {
            holder.imageView.setImageResource(R.drawable.strange);
        }
        if(movies.get(position).getImageId() == "thor") {
            holder.imageView.setImageResource(R.drawable.thor);
        }
        if(movies.get(position).getImageId() == "inception") {
            holder.imageView.setImageResource(R.drawable.inception);
        }
        if(movies.get(position).getImageId() == "tenet") {
            holder.imageView.setImageResource(R.drawable.tenet);
        }
        if(movies.get(position).getImageId() == "interstellar") {
            holder.imageView.setImageResource(R.drawable.interstellar);
        }
        if(movies.get(position).getImageId() == "up") {
            holder.imageView.setImageResource(R.drawable.up);
        }
        //holder.imageView.setImageResource(R.drawable.batman);

      //  holder.imageView.setImageURI(Uri.parse("android.resource://"  + "drawable/" + "superman.png"));


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }
    Filter myFilter = new Filter() {

        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Movie> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(moviesAll);
            } else {
                for (Movie movie: moviesAll) {
                    if (movie.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(movie);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            System.out.println("REZ" + filterResults.values);
            return filterResults;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            movies.clear();
            movies.addAll((Collection<? extends Movie>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView, movieDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            movieDateTextView = itemView.findViewById(R.id.movie_date);

            itemView.setOnClickListener(this);

        }
/**
        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), movies.get(getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
        } */

        @Override
        public void onClick(View view) {
            if(movies.get(getAdapterPosition()).getReserved() == false){
                movies.get(getAdapterPosition()).setReserved(true);



                Toast.makeText(view.getContext(), movies.get(getAdapterPosition()).getTitle() + " was reserved", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(view.getContext(), movies.get(getAdapterPosition()).getTitle() , Toast.LENGTH_SHORT).show();
            }

//            System.out.println("MOV" +   movies.get(getAdapterPosition()));
            Log.d("MOV", String.valueOf(movies.get(getAdapterPosition())));

            Button button1 = view.findViewById(R.id.share);
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, movies.get(getAdapterPosition()).getTitle());
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    v.getContext().startActivity(shareIntent);
                }
            });

        }




    }
}

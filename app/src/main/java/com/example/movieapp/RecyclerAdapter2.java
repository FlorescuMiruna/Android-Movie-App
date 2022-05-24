package com.example.movieapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.ViewHolder> implements Filterable {

    private static final String TAG = "RecyclerAdapter2";

    List<Movie> movies;
    List<Movie> moviesAll;



    public RecyclerAdapter2(List<Movie> movies) {


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

//            System.out.println("MOV" +   movies.get(getAdapterPosition()));
            Log.d("MOV", String.valueOf(movies.get(getAdapterPosition())));
            Toast.makeText(view.getContext(), movies.get(getAdapterPosition()).getTitle() , Toast.LENGTH_SHORT).show();
        }


    }
}

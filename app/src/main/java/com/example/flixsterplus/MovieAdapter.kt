package com.example.flixsterplus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MovieAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPoster: ImageView = itemView.findViewById(R.id.ivPoster)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvOverview: TextView = itemView.findViewById(R.id.tvOverview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.tvTitle.text = movie.title
        holder.tvOverview.text = movie.overview

        // If posterPath is empty, hide the ImageView (or you can show a placeholder)
        if (movie.posterPath.isBlank()) {
            holder.ivPoster.visibility = View.INVISIBLE
            holder.ivPoster.setImageDrawable(null)
        } else {
            holder.ivPoster.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(movie.posterPath) // should already include https://image.tmdb.org/t/p/w500/...
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .into(holder.ivPoster)
        }
    }

    override fun getItemCount(): Int = movies.size
}

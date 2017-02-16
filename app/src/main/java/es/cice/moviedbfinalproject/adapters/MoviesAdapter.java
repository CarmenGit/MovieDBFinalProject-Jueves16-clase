package es.cice.moviedbfinalproject.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import es.cice.moviedbfinalproject.R;

import es.cice.moviedbfinalproject.model.Movie;


/**
 * Created by cice on 20/1/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> implements Filterable {
    private List<Movie> movieList;
    private Context ctx;


    public MoviesAdapter(Context ctx, List<Movie> list){
        movieList=list;
        this.ctx=ctx;
    }
    /*
    Este metodo se llama cada vez que sea necesario construir una nueva fila
     */
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Obtenemos el infater necesario para construir un fila definida en xml
        LayoutInflater inflater=LayoutInflater.from(ctx);
        View row=inflater.inflate(R.layout.movie_row,parent,false);
        ViewHolder holder=new ViewHolder(row);


        return holder;
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.ViewHolder holder, int position) {

        holder.tituloTV.setText(movieList.get(position).getTitle());
        Picasso
                .with(ctx)
                .load(Uri.parse("https://image.tmdb.org/t/p/w500"+movieList.get(position).getPosterPath()))
                .resize(350,350)
                .centerInside()
                .into(holder.movieImageIV);


        holder.vElipsisTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Elipsis","onclick()...");
                PopupMenu popup=new PopupMenu(ctx,holder.vElipsisTV);
                popup.inflate(R.menu.movie_popup);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.creditsItem:
                                /*Log.d("Elipsis","delete...");
                                filmList.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();*/
                                break;
                            case R.id.opinionsItem:
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public Filter getFilter() {
        return new MovieFilter();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView movieImageIV;
        private TextView tituloTV;
        private TextView vElipsisTV;

        public ViewHolder(View itemView) {
            super(itemView);
            movieImageIV= (ImageView) itemView.findViewById(R.id.movieImageIV);
            tituloTV= (TextView) itemView.findViewById(R.id.tituloTV);
            vElipsisTV= (TextView) itemView.findViewById(R.id.vElipsis);

            movieImageIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("CarViewHolder","old position: " + getOldPosition());
                    Log.d("CarViewHolder","layout position: " + getLayoutPosition());
                    Log.d("CarViewHolder","adapter position: " + getAdapterPosition());
                    /*Intent intent=new Intent(ctx,CarDetailActivity.class);
                    intent.putExtra(CarDetailActivity.IMAGEN_EXTRA,
                            carList.get(getAdapterPosition()).getImagen());
                    intent.putExtra(CarDetailActivity.DESCRIPCION_EXTRA,
                            carList.get(getAdapterPosition()).getDescripcion());
                    intent.putExtra(CarDetailActivity.FABRICANTE_EXTRA,
                            carList.get(getAdapterPosition()).getFabricante());
                    intent.putExtra(CarDetailActivity.MODELO_EXTRA,
               Log.d("Elipsis","onclick()...");             carList.get(getAdapterPosition()).getModelo());
                    ctx.startActivity(intent);*/
                }
            });
        }
    }

    public class MovieFilter extends Filter{
        public static final String TAG="MovieFilter";
        private List<Movie> originalList;
        private List<Movie> filteredList;

        public MovieFilter(){
            originalList=new LinkedList<>(movieList);

        }

        @Override
        protected FilterResults performFiltering(CharSequence filterData) {
            Log.d(TAG,"performFiltering()...");
            String filterStr=filterData.toString();
            FilterResults results=new FilterResults();
            filteredList=new ArrayList<>();
            for(Movie f:originalList){
                //if(f.getFabricante().equalsIgnoreCase(filterStr) ||
                //        f.getModelo().equalsIgnoreCase(filterStr)){
                //    filteredList.add(f);

                //}
            }
            results.values=filteredList;
            results.count=filteredList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            Log.d(TAG,"publishResults()...");
            List<Movie>list=(List<Movie>) filterResults.values;
            if(list.size()>0)
                movieList=list;
            notifyDataSetChanged();
        }
    }
}


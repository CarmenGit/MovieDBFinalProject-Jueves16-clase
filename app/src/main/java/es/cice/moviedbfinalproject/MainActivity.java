package es.cice.moviedbfinalproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import es.cice.moviedbfinalproject.asynctasks.TheMovieDBMoviesAsyncTask;
import es.cice.moviedbfinalproject.asynctasks.TheMovieDBGetGenresAsynTask;
import es.cice.moviedbfinalproject.asynctasks.TheMovieDBUrlBaseAsynTask;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();
    private Context ctx;
    private ActionBar aBar;
    private EditText searchET;
    public String urlBaseImage;
    public static final String api_key = "857ef84cbaec1f89f981c0ac344c4630";
    private static final String URL_CONFIG="https://api.themoviedb.org/3/configuration?api_key=" + api_key;
   // private String SizeImage=
    private static final String URL_GENRES ="https://api.themoviedb.org/3/genre/movie/list?api_key="+ api_key;
    private Spinner spGenre;
    private RecyclerView moviesRV;
    private ImageView imageMovieIV;
    private final static String URL_POPULAR_MOVIES="https://api.themoviedb.org/3/movie/popular?api_key=857ef84cbaec1f89f981c0ac344c4630";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.includedToolbar);

        setSupportActionBar(toolbar);
        aBar = getSupportActionBar();
        aBar.setTitle("PELÍCULAS");

        //Obtener url base para imágenes
        getBaseUrlImage();

        spGenre= (Spinner) findViewById(R.id.genreSPIN);
        //spGenre= (Spinner) findViewById(R.id.genreSP);
        Log.d("Pero...qué de qué....", spGenre.toString());

        getGenres();
        moviesRV = (RecyclerView) findViewById(R.id.moviesRV);
        imageMovieIV=(ImageView) findViewById(R.id.movieImageIV);
        getMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu()...");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    public void getGenres(){
        TheMovieDBGetGenresAsynTask at = new TheMovieDBGetGenresAsynTask();

        at.setSpGenre(spGenre);
        at.setContext(this);
        at.execute(URL_GENRES);
    }

    public void getMovies() {
        TheMovieDBMoviesAsyncTask at = new TheMovieDBMoviesAsyncTask();
        at.setMoviesRV(moviesRV);
        at.setContext(this);
        at.setImageView(imageMovieIV);
        at.execute(URL_POPULAR_MOVIES);  }

    public void getBaseUrlImage(){
        TheMovieDBUrlBaseAsynTask at = new TheMovieDBUrlBaseAsynTask();
        at.execute(URL_CONFIG); }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.generoIT:
                Log.d(TAG, "Género item...");
                aBar.setDisplayShowCustomEnabled(true);
                spGenre.setVisibility(View.VISIBLE);
                //aBar.setCustomView(R.layout.genre_layout);
                //aBar.setDisplayShowTitleEnabled(false);

                return true;
           /* case R.id.buscarIT:
                Log.d(TAG, "Search item...");
                aBar.setDisplayShowCustomEnabled(true);
                aBar.setCustomView(R.layout.search_layout);
                aBar.setDisplayShowTitleEnabled(false);

                searchET = (EditText) aBar.getCustomView().findViewById(R.id.searchET);
                searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                        if (action == EditorInfo.IME_ACTION_SEARCH) {
                            CharSequence searchText = searchET.getText();
                            Log.d(TAG, "search: " + searchText);
                            InputMethodManager imn =
                                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imn.hideSoftInputFromWindow(searchET.getWindowToken(), 0);
                            aBar.setDisplayShowCustomEnabled(false);
                            aBar.setDisplayShowTitleEnabled(true);
                            //empezar la busqueda
                            adapter.getFilter().filter(searchText);
                            return true;
                        }
                        return false;
                    }
                });

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchET, InputMethodManager.SHOW_IMPLICIT);
                searchET.requestFocus();
                break;*/
        }


                return super.onOptionsItemSelected(item);
        }



    }





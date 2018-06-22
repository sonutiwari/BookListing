package in.co.ikai.booklisting.utilities;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

import in.co.ikai.booklisting.dataModel.Book;

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link BookLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public ArrayList<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of books.
        return Utils.fetchBookData(mUrl);
    }
}

package in.co.ikai.booklisting.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import in.co.ikai.booklisting.activities.MainActivity;
import in.co.ikai.booklisting.R;
import in.co.ikai.booklisting.dataModel.Book;

public class BookAdapter extends ArrayAdapter<Book> {

    private static final String LOG_TAG = BookAdapter.class.getSimpleName();

    public BookAdapter(Activity context, ArrayList<Book> Books) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, Books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        MainActivity.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
            holder = new MainActivity.ViewHolder();
            // Find the TextView in the list_item.xml (mapping)
            holder.titleBookTextView = convertView.findViewById(R.id.book_title);
            holder.authorBookTextView = convertView.findViewById(R.id.author);
            holder.coverImageView = convertView.findViewById(R.id.cover_image);
            holder.priceBookTextView = convertView.findViewById(R.id.book_price);
            holder.languageCode = convertView.findViewById(R.id.country_code);
            holder.currencyCode = convertView.findViewById(R.id.currency_code);
            convertView.setTag(holder);
        } else {
            holder = (MainActivity.ViewHolder) convertView.getTag();
        }

        // Get the current position of Book
        final Book currentBook = getItem(position);

        // Set proper value in each fields
        assert currentBook != null;
        holder.titleBookTextView.setText(currentBook.getTitle());
        holder.authorBookTextView.setText(currentBook.getAuthor());
        Glide.with(getContext()).load(currentBook.getImageUrl()).into(holder.coverImageView);
        holder.priceBookTextView.setText(String.valueOf(formatPrice(currentBook.getPrice())));
        holder.languageCode.setText(currentBook.getLanguage());
        holder.currencyCode.setText(currentBook.getCurrency());

        Log.i(LOG_TAG, "ListView has been returned");
        return convertView;

    }

    // Format with two decimal places for price value
    private String formatPrice(double price) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.00");
        return magnitudeFormat.format(price);
    }


}

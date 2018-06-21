package in.co.ikai.booklisting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /* This String will contain base URL for Google Books API search */
    public static final String GOOGLE_BOOK_API_BASE_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.txt_sample);

        if (isAirplaneModeOn(this)){
            textView.setText(R.string.airplane_mode_message);
        }
        if (!isNetworkAvailable()){
            textView.setText(R.string.internet_service_disabled);
        }

        final EditText searchText = findViewById(R.id.auto_search);
        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    // Hide the keyboard.
                    InputMethodManager imm =
                            (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    // Perform action on key press
                    // TODO handle empty string here...
                    performSearch(searchText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    // Performed search action here.
    // TODO call buildURL, make connection, use loadermanager to get data, parse json and update UI
    private void performSearch(String query) {
        query = query.replace(" ", "+");
        String uri = GOOGLE_BOOK_API_BASE_URL + query;
        Toast.makeText(this, uri, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets the state of Airplane Mode.
     *
     * @param context context of the activity.
     * @return true if enabled.
     */
    public static boolean isAirplaneModeOn(Context context) {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    //
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

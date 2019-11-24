package teamsandman.sleepasasymptom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    public void onClick(View view) {
        Intent intent = new Intent(this, InputDataActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        View recyclerView = findViewById(R.id.entry_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new
                MainActivity.SimpleCourseRecyclerViewAdapter());
    }

    public class SimpleCourseRecyclerViewAdapter
            extends RecyclerView.Adapter<MainActivity.
            SimpleCourseRecyclerViewAdapter.ViewHolder> {

        /**
         * Collection of the items to be shown in this list.
         */
        private final List<Entry> mEntries;

        /**
         * set the items to be used by the adapter
         */
        SimpleCourseRecyclerViewAdapter() {
            mEntries = TempDB.entry_list;
        }

        @NonNull
        @Override
        public MainActivity.SimpleCourseRecyclerViewAdapter.
                ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            /*

              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            LayoutInflater lI = LayoutInflater.from(parent.getContext());
            View view = lI.inflate(R.layout.entry_list_content, parent, false);
            return new MainActivity.SimpleCourseRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MainActivity.
                SimpleCourseRecyclerViewAdapter.ViewHolder holder, int position) {

            holder.mEntry = mEntries.get(position);
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a course in the other.
             */
            Entry c = mEntries.get(position);
            Long time_elapsed = System.currentTimeMillis() - c.time;
            String time = "Data Logged:          ";
            if (time_elapsed > 60000) {
                long num = Math.round(Math.floor(time_elapsed / 60000));
                if (num < 10) {
                    time += " ";
                }
                String min = Long.toString(num);
                time += min + "m";
            } else {
                long num = time_elapsed/1000;
                if (num < 10) {
                    time += "  ";
                }
                time += Long.toString(num) + "s";
            };
            time += " ago";
            holder.mEntryView.setText(time);
        }

        @Override
        public int getItemCount() {
            return mEntries.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a Course) and the widgets in
         * the list view (in this case the two TextView)
         */

        public class ViewHolder extends RecyclerView.ViewHolder {
            final View mView;
            final TextView mEntryView;
            Entry mEntry;

            /**
             * ViewHolder constructor
             * @param view current view
             */
            ViewHolder(View view) {
                super(view);
                mView = view;
                mEntryView = view.findViewById(R.id.entry);
            }

            @NonNull
            @Override
            public String toString() {
                return super.toString() + " '" + mEntryView.getText() + "'";
            }
        }
    }

}

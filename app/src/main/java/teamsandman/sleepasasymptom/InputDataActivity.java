package teamsandman.sleepasasymptom;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InputDataActivity extends AppCompatActivity {

    private TextView mTextMessage;
    DatePickerDialog picker;
    EditText dateSelect;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        dateSelect = (EditText) findViewById(R.id.editText);
        dateSelect.setInputType(InputType.TYPE_NULL);
        dateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(InputDataActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateSelect.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    public void onClick(View view) {
        Entry entry = new Entry();
        TempDB.entry_list.addFirst(entry);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        Toast.makeText(this, "Your data has been successfully submitted!", Toast.LENGTH_LONG).show();
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickCheck(View view) {
        TextView bp = findViewById(R.id.bp);
        EditText bp_edit = findViewById(R.id.bp_edit);
        TextView rem = findViewById(R.id.rem);
        EditText rem_edit = findViewById(R.id.rem_edit);
        TextView hr = findViewById(R.id.hr);
        TextView hr_edit = findViewById(R.id.hr_edit);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        if (checkBox.isChecked()) {
            bp.setVisibility(View.VISIBLE);
            bp_edit.setVisibility(View.VISIBLE);
            rem.setVisibility(View.VISIBLE);
            rem_edit.setVisibility(View.VISIBLE);
            hr.setVisibility(View.VISIBLE);
            hr_edit.setVisibility(View.VISIBLE);
        } else {
            bp.setVisibility(View.GONE);
            bp_edit.setVisibility(View.GONE);
            rem.setVisibility(View.GONE);
            rem_edit.setVisibility(View.GONE);
            hr.setVisibility(View.GONE);
            hr_edit.setVisibility(View.GONE);
        }
    }

}

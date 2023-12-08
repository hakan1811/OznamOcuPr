package sk.kusnierr.pushnotifyprochot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class Detail extends MainActivity {
    private Toolbar tb;
    TextView Title,Message,Date,Header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Title = (TextView)findViewById(R.id.textViewTitle);
        Message = (TextView)findViewById(R.id.textViewBody);
        Date = (TextView)findViewById(R.id.textViewDate);
        Header = (TextView)findViewById(R.id.textViewHeader);
        Date.setGravity(Gravity.CENTER);
        Header.setText("Detail:");
        Date.setText(getIntent().getStringExtra("date"));
        Title.setText(getIntent().getStringExtra("title"));
        Message.setText(getIntent().getStringExtra("body"));

    }
}
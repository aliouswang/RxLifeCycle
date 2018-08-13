package com.example.aliouswang.rxlifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_goto_mock_http;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_goto_mock_http = findViewById(R.id.btn_goto_mock_http);
        btn_goto_mock_http.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMockHttp();
            }
        });
    }

    private void gotoMockHttp() {
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
    }
}

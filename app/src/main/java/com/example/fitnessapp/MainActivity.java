package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String name, str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectionClass = new ConnectionClass();
        connect();
    }

    public void connect() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.CONN();
                if (con == null) {
                    str = "Error in connection with MYSQL server";
                } else{
                    str = "Connected with MYSQL server";

                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
            runOnUiThread(() ->{
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, str,Toast.LENGTH_SHORT).show();
            });
        });
    }

    public void btnClick(View view) {
    }
}
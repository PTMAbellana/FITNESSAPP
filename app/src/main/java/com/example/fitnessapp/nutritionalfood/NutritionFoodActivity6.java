package com.example.fitnessapp.nutritionalfood;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessapp.crud.InsertData;
import com.example.fitnessapp.R;
import com.example.fitnessapp.crud.UpdateData;

public class NutritionFoodActivity6 extends AppCompatActivity {
//    private PieChart pieChart;

    private EditText editText;
    private Button btnCalculateRatio, btnOrder, btnCancel;
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;
    private TextView totalCaloriesTextView;

    private Double bmi;
    Dialog dialog;
    Dialog dialog2;
    Button btnOk;
    Button btnOk2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nutrition_food6);
//        pieChart = findViewById(R.id.pieChart);

        bmi = getIntent().getDoubleExtra("BMI", 0);

        editText = findViewById(R.id.editTextText);
        btnCalculateRatio = findViewById(R.id.btnCalculateRatio);
        btnOrder = findViewById(R.id.btnOrder);
        checkBox1 = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        totalCaloriesTextView = findViewById(R.id.textView26);

        dialog = new Dialog(NutritionFoodActivity6.this);
        dialog.setContentView(R.layout.pop_up_message);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.pop_up_message_bg));

        dialog2 = new Dialog(NutritionFoodActivity6.this);
        dialog2.setContentView(R.layout.pop_up_message2);
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.pop_up_message_bg));

        btnOk = dialog.findViewById(R.id.btnOks);
        btnOrder = findViewById(R.id.btnOrder);
        btnOk2 = dialog2.findViewById(R.id.btnOks2);
        btnOk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCalculateRatio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndDisplayRatio();
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnOrder.getText().toString().equals("Add Order")) {
                    dialog.show();
                    placeOrder();
                    btnOrder.setText("Cancel Order");
                } else if (btnOrder.getText().toString().equals("Cancel Order")) {
                    dialog2.show();
                    cancelOrder();
                    btnOrder.setText("Add Order");
                }
            }
        });
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog2.show();
//                cancelOrder();
//            }
//        });
    }

    public void cancelOrder() {
        UpdateData.deleteDietPlan("Teriyaki Chicken With Brown Rice");
    }

    private void calculateAndDisplayRatio() {
        int multiplier = Integer.parseInt(editText.getText().toString());

        float totalCalories = 0;
        float[] calories = new float[6];

        if (checkBox1.isChecked()) {
            calories[0] = 69.1f * multiplier;
            totalCalories += calories[0];
        }
        if (checkBox2.isChecked()) {
            calories[1] = 56.8f * multiplier;
            totalCalories += calories[1];
        }
        if (checkBox3.isChecked()) {
            calories[2] = 71.5f * multiplier;
            totalCalories += calories[2];
        }
        if (checkBox4.isChecked()) {
            calories[3] = 9.9f * multiplier;
            totalCalories += calories[3];
        }
        if (checkBox5.isChecked()) {
            calories[4] = 0.0f * multiplier;
            totalCalories += calories[4];
        }
        if (checkBox6.isChecked()) {
            calories[5] = 0.3f * multiplier;
            totalCalories += calories[5];
        }

        totalCaloriesTextView.setText(String.valueOf(totalCalories));

        float remainingCalories = 2600 - totalCalories;

//        ArrayList<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(totalCalories, "Consumed"));
//        entries.add(new PieEntry(remainingCalories, "Remaining"));
//
//        PieDataSet dataSet = new PieDataSet(entries, "Calorie Distribution");
//        PieData pieData = new PieData(dataSet);
//
//        pieChart.setData(pieData);
//        pieChart.invalidate(); // refresh
    }

    public void placeOrder(){
        String bmi_cat;
        if (bmi >= 30) bmi_cat = "Obesity";
        else if(bmi >= 25.0 && bmi< 30.0) bmi_cat = "Overweight";
        else if (bmi >= 18.5 && bmi < 25.0) bmi_cat = "Normal";
        else bmi_cat = "Underweight";

        InsertData.insertDiet(bmi_cat, "Teriyaki Chicken With Brown Rice");
    }

    public void onBackClicked(View view) {
//        Intent intent = new Intent(this, HomeView.class);
//        startActivity(intent);
        finish();
    }
}
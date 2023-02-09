package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button see_history;
    HistoryActivity historyActivity;

    private static final String KEY_MY_DATA = "key_my_data";
    private String[] my_data = new String[2];
    private TextView result_space, display_space;
    private MaterialButton button_C, button_BracketOpen, button_BracketClose, button_divide;
    private MaterialButton button_7, button_8, button_9, button_multiply;
    private MaterialButton button_4, button_5, button_6, button_subtract;
    private MaterialButton button_1, button_2, button_3, button_plus;
    private MaterialButton button_AC, button_0, button_separator, button_equals;

    private void GetButton (MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            my_data = savedInstanceState.getStringArray(KEY_MY_DATA);
        } else {
            my_data = new String[] {"", "0"};
        }

        result_space = findViewById(R.id.result_space);
        display_space = findViewById(R.id.display_space);
        see_history = findViewById(R.id.see_history);

        /*When click "See history" go see history*/
        see_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
        historyActivity = new HistoryActivity();

        GetButton (button_C, R.id.button_C);
        GetButton (button_BracketOpen, R.id.button_BracketOpen);
        GetButton (button_BracketClose, R.id.button_BracketClose);
        GetButton (button_divide, R.id.button_divide);
        GetButton (button_7, R.id.button_7);
        GetButton (button_8, R.id.button_8);
        GetButton (button_9, R.id.button_9);
        GetButton (button_multiply, R.id.button_multiply);
        GetButton (button_4, R.id.button_4);
        GetButton (button_5, R.id.button_5);
        GetButton (button_6, R.id.button_6);
        GetButton (button_subtract, R.id.button_subtract);
        GetButton (button_1, R.id.button_1);
        GetButton (button_2, R.id.button_2);
        GetButton (button_3, R.id.button_3);
        GetButton (button_plus, R.id.button_plus);
        GetButton (button_0, R.id.button_0);
        GetButton (button_AC, R.id.button_AC);
        GetButton (button_separator, R.id.button_separator);
        GetButton (button_equals, R.id.button_equals);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        my_data[0] = display_space.getText().toString();
        my_data[1] = result_space.getText().toString();
        outState.putStringArray(KEY_MY_DATA, my_data);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        my_data = savedInstanceState.getStringArray(KEY_MY_DATA);
        display_space.setText(my_data[0]);
        result_space.setText(my_data[1]);
    }

    @Override
    public void onClick(View v) {
        MaterialButton btn =  (MaterialButton)v;
        String btn_text = btn.getText().toString();
        if (btn_text.equals("x")) btn_text = "*";
        /*display_space.setText(btn_text)*/
        String calcString = display_space.getText().toString();

        if (btn_text.equals("=") && !display_space.getText().toString().equals("")){
            String calc = Calculate(calcString);
            result_space.setText(calc);
            AddHistory();
            return;
        }

        /*If the button pressed is an operator and before it exists another operator or is blank, do nothing*/
        if (btn_text.equals("/") || btn_text.equals("*") || btn_text.equals("-") || btn_text.equals("+")) {
            if (calcString.endsWith("/") || calcString.endsWith("*") || calcString.endsWith("-") || calcString.endsWith("+") || display_space.getText().toString().equals("")) {
                return;
            }
        }
        if (btn_text.equals("AC")) {
            display_space.setText("");
            result_space.setText("0");
            return;
        }
        else if (btn_text.equals("C") && !display_space.getText().toString().equals("")) {
            calcString = calcString.substring(0, calcString.length() - 1);
        }
        else if (btn_text.equals("C") && display_space.getText().toString().equals("")){
            return;
        }
        else if (!btn_text.equals("=")){
            calcString = calcString + btn_text;
        }

        display_space.setText(calcString);
    }

    private String Calculate(String calcString) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            double result = (double)context.evaluateString(scriptable, calcString, "Javascript", 1, null);
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
            String calc = df.format(result);
            return calc;
        }
        catch (Exception e) {
            return "Error!";
        }
    }

    private void AddHistory() {
        try {
            String historyEntry = display_space.getText().toString() + " = " + result_space.getText().toString();
            addCalculationToHistory(historyEntry);
        }
        catch (Exception e) {

        }
    }

    private void addCalculationToHistory(String calculation) {
        historyActivity = new HistoryActivity();
        historyActivity.addCalculationToHistory(calculation);
    }
}
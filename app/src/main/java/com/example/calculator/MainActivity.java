//Kelsee Carmichael Android Calculator App
//This class defines the basic functionality of a calculator
package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements onButtonClickedListener {

    private Button button;
    private TextView resultView;
    private String result = "";
    private String num2 = "";
    private String firstOperator = "";
    private String nextOperator = "";
    private boolean operatorSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView = findViewById(R.id.result_view);
    }

    //onClick method for digits, decimal, and sign buttons
    public void clickDigit(View view){
        button = (Button) view;

        //Defines "+/-" button, it allows the user to enter a negative number
        //or to change the sign of an existing number
         if (button.getText().equals("+/-")) {
            if (num2.isEmpty() && !result.isEmpty()) {
                result = changeSign(result);
                resultView.setText(formatResult());
            } else if (!result.isEmpty()){
                resultView.setText(num2 = changeSign(num2));
            }
        }
        //Checking to see if an operator has been selected.
        //If not, the digit or decimal entered are appended to result
        //Then displayed on the TextView
        else if (!operatorSelected) {
            //This if statement ensures the user doesn't enter multiple decimals
            if (!button.getText().equals(".") || !result.contains(".")) {
                result += button.getText().toString();
                Log.d(result, "num 1 is assigned");
            }
            resultView.setText(result);
        }
        //If an operator has been selected, the digit or decimal entered are appended to num2
        //Then displayed on the TextView
        else {
            //This if statement ensures the user doesn't enter multiple decimals
            if (!button.getText().equals(".") || !num2.contains(".")) {
                num2 += button.getText().toString();
                Log.d(num2, "num 2 is assigned");
            }
            resultView.setText(num2);
        }
    }

    //onClick method for operators, equal button, clear button and percent button
     public void clickOperator(View view){
        button = (Button)view;
        operatorSelected = true;

         //assigns clicked operator to proper operator variable
         //the firstOperator and nextOperator variables are used
         // in conjunction to support running calculations
        if (firstOperator.equals("")) {
            firstOperator = button.getText().toString();
            Log.d(firstOperator, "first operator is assigned");
        }

        else{
            nextOperator = button.getText().toString();
            Log.d(nextOperator, "next operator is assigned");
        }

        //deals with clear button, clears all operators and operands entered to start fresh
         if (button.getText().equals("C")) {
             reset();
             resultView.setText(result);
         }
         //deals with percent button
         else if (button.getText().equals("%")){
             double num = Double.parseDouble(result);
             result = String.valueOf(num / 100);
             resultView.setText(formatResult());
             firstOperator = "";
             nextOperator = "";
         }
         //calls operation method when the equal button is pressed then clears the operation
         //variables to avoid crashing
         else if (button.getText().equals("=")){
             operation();
             firstOperator = "";
             nextOperator = "";
         }
         //calls operation method when there are two operands and an operator to work with
         else if (!num2.equals("")) {
             operation();
         }

         //Checks if the result variable is empty, if it is, it will reset all variables
         //to prevent crash as a number should be entered before an operation
         if (result.isEmpty()){
             reset();
         }
        }

        //performs arithmetic calculations then sets TextView to result using the
        // suppressZero method
        public void operation(){
            double total = 0;
            switch (firstOperator) {
                case "+":
                    total = Double.parseDouble(result) + Double.parseDouble(num2);
                    break;
                case "-":
                    total = Double.parseDouble(result) - Double.parseDouble(num2);
                    break;
                case "/":
                    if (result.equals("0") || num2.equals("0")){
                        //total will remain zero as previously defined to avoid divide by 0 error
                        // Also the operator variables will be cleared
                        nextOperator = "";
                    }
                    else {
                        total = Double.parseDouble(result) / Double.parseDouble(num2);
                        break;
                    }
                case "x":
                    total = Double.parseDouble(result) * Double.parseDouble(num2);
                    break;
            }
            num2 = "";
            firstOperator = nextOperator;
            nextOperator = "";
            result = (String.valueOf(total));
            resultView.setText(formatResult());
        }

    //suppresses unnecessary zeros, limits the length of accepted numbers and
    // returns the formatted the output
    public String formatResult(){
        DecimalFormat fmt = new DecimalFormat("#######.####");
        double number = Double.parseDouble(result);
        result = fmt.format(number);

        //Error will appear in TextView if the number is 12 or more digits
        if (result.length() >= 12){
            reset();
            Log.d("Error", "Length too long");
            return "Error";
        }
        else {
            return result;
        }
    }

    //Reset all the variables of the calculator to start fresh
    public void reset(){
        Log.d("Reset", "Resetting all variables");
        result = "";
        num2 = "";
        firstOperator = "";
        nextOperator = "";
        operatorSelected = false;
    }

    //Allows the user to enter a negative number or change the sign of
    //an existing number
    public String changeSign(String string){
        DecimalFormat fmt = new DecimalFormat("#######.####");
        double number = Double.parseDouble(string);

        //resets all variables to avoid crashing if the current result is 0
        if (number == 0){
            reset();
            string = "";
        }
        //changes the sign of the argument entered
        else{
            string = fmt.format(number * -1);
        }
        return string;
    }
}
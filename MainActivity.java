package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    //global variables
    int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //increment method
    public void increment(View view){
        if(quantity == 100) {
            //making pop up toast to message user (context: this method, message, length of time on screen)
            Toast.makeText(this,"You may not submit an order for more than 100 cups of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        displayQuantity(quantity);
    }//end of increment method
    //decrement method
    public void decrement(View view){
        if(quantity == 1 ) {
            //making pop up toast to message user (context: this method, message, length of time on screen)
            Toast.makeText(this,"You may not submit an order for less than 1 cup of coffee",Toast.LENGTH_LONG).show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }//end of decrement method
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //retrieving name for order summary. And saving it as a String with .toString method
        EditText getName = (EditText) findViewById(R.id.name_edit_text);
        String name = getName.getText().toString();
        //to check what toppings where selected
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        //calculate price and put together order summary
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String summary = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
        //send summary order email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses); //this is only to specify the address
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(summary);
    }//end of submitOrder method
    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;
        if(hasWhippedCream){
            price += 1;
        }
        if(hasChocolate){
            price += 2;
        }
        price = price * quantity;
        return price;
    }//end of calculatePrice method
    /**
     * Creates summary of order
     */
    private String createOrderSummary(String name,int price,boolean hasWhippedCream, boolean hasChocolate){
        String addChocolate;
        String addWhippedCream;
        if(hasWhippedCream == true){
           addWhippedCream = "Add Whipped Cream";
        }else{
            addWhippedCream = "No Whipped Cream";
        }
        if(hasChocolate == true){
            addChocolate = "Add Chocolate";
        }else{
           addChocolate = "No Chocolate";
        }
        String summary = "Name: " + name +
                        "\nQuantity: " + quantity +
                        "\n" + addWhippedCream +
                        "\n" + addChocolate +
                        "\nTotal: $" + price +
                        "\nThank you!";
        return summary;
    }//end of createOderSummary

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}

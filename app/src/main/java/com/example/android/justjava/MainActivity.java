package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;
    String answerWhippedCream = "";
    String answerChocolate = "";

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        if (hasWhippedCream == true){
            answerWhippedCream = "Yes";
        } else {
            answerWhippedCream = "No";
        }

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        if (hasChocolate == true) {
            answerChocolate = "Yes";
        } else {
            answerChocolate = "No";
        }

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        EditText customerName = (EditText) findViewById(R.id.customer_name);
        String name = customerName.getText().toString();
        String orderMessage = createOrderSummary(price, answerWhippedCream, answerChocolate, name);
        //displayMessage(createOrderSummary(price, answerWhippedCream, answerChocolate, name));

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }


//    /**
//     * This method displays the given text on the screen.
//     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream == true) {
            basePrice += 1;
        }
        if (addChocolate == true) {
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    /**
     * Creates an order summary when the Order button is pressed
     */
    private String createOrderSummary(int price, String answerWhippedCream, String answerChocolate, String name){
        String summaryMessage = "Name: " + name;
        summaryMessage += "\nAdd whipped cream? " + answerWhippedCream;
        summaryMessage += "\nAdd Chocolate? " + answerChocolate;
        summaryMessage += "\nQuantity: " + quantity;
        summaryMessage += "\nTotal: $" + price;
        summaryMessage += "\nThank You!";
        return summaryMessage;
    }

    /**
     * This method subtracts 1 from Quantity
     */

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "Must Order 1 Coffee", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }
    /**
     * This method adds 1 from Quantity
     */
    public void increment(View view) {
        if (quantity == 10) {
            Toast.makeText(this, "Too Many Coffees!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }
}
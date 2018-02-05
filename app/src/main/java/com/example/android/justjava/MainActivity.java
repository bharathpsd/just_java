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

        import java.text.NumberFormat;
        import java.util.Locale;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    int price = 0;
    boolean whip,chocolate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
//        displayQuantity(quantity);
         EditText edit = (EditText) findViewById(R.id.customer_name);
         String name =  edit.getText().toString();
         int price = calculatePrice();
        String priceMessage = createOrderSummary(price,whip,chocolate,name);
//        displayMessage(priceMessage);
//        quantity = 1;
//        displayQuantity(quantity);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava Order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    private String createOrderSummary(int price, boolean whip, boolean chocolate, String name){
        String print = getString(R.string.order_summary_name,name) + "\n" + getString(R.string.whip) + "? " + whip +"\n" + getString(R.string.chocolate) + "? " + chocolate;
        print += "\n" + getString(R.string.quantity_button) + ": " + quantity + "\n"+getString(R.string.price) + price + "\n" + getString(R.string.thank_you);
        return print;
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public void increment(View view){

        quantity++;
        displayQuantity(quantity);
        displayMessage("$" + calculatePrice());
    }

    public void decrement(View view){

        if(quantity<=1){
            Toast.makeText(this, "You cannot have less than 1 Coffee", Toast.LENGTH_SHORT).show();
            quantity = 1;
            return;
        }
        else {
        quantity--;
        displayQuantity(quantity);
        displayMessage("$" + calculatePrice());
        }
    }

    public int calculatePrice(){
        CheckBox addWhip = (CheckBox) findViewById(R.id.whip_cream);
        whip = addWhip.isChecked();
        CheckBox addChocolate = (CheckBox) findViewById(R.id.add_chocolate);
        chocolate = addChocolate.isChecked();
        price = quantity*5;
        if(whip && chocolate)
            price += quantity + quantity*2;
        else if(whip)
            price += quantity;
        else if(chocolate)
            price += quantity*2;

        return price;
    }

    public void onTop(View view){
        displayMessage("$"+calculatePrice());
    }
}

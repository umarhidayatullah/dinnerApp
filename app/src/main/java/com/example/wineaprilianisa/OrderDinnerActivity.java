package com.example.wineaprilianisa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;

public class OrderDinnerActivity extends Activity {
    String selectedDinnerExtraKey = String.valueOf(R.string.selected_dinner);
    String thisDinner;
    String thisDinnerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_dinner);
    }

    protected void onStart() {
        super.onStart();
        String dinner = getIntent().getStringExtra(selectedDinnerExtraKey);
        String dinnerId = Utility.getDinnerId(dinner);

        thisDinner = dinner;
        thisDinnerId = dinnerId;
        sendViewProductHit(dinner, dinnerId);
        TextView heading = findViewById(R.id.textView_info_heading);
        heading.setText(getResources().getText(R.string.order_online_heading));

        TextView info = findViewById(R.id.textView_info);
        info.setText("This is where you will order the selected dinner: \n\n" +
                dinner);
    }

    private void sendViewProductHit(String dinner, String dinnerId) {
        Product product = new Product()
                .setName("dinner")
                .setPrice(5)
                .setVariant(dinner)
                .setId(dinnerId)
                .setQuantity(1);

        ProductAction productAction = new ProductAction(ProductAction.ACTION_DETAIL);
        Tracker tracker = ((MyApplication) getApplication()).getTracker();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Shopping steps")
                .setAction("View Order Dinner screen")
                .setLabel(dinner)
                .addProduct(product)
                .setProductAction(productAction)
                .build());
    }

    public void addDinnerToCart(View view) {
        Utility.showMyToast("Ï will add the dinner " + thisDinner + "to the cart", this);

        sendAddToCartHit();

        Button button = (Button) findViewById(R.id.start_chekout_btn);
        button.setVisibility(View.VISIBLE);

        button = (Button) findViewById(R.id.add_to_cart_btn);
        button.setVisibility(View.INVISIBLE);
    }

    private void sendAddToCartHit() {
        Product product = new Product()
                .setName("dinner")
                .setPrice(5)
                .setVariant(thisDinner)
                .setId(thisDinnerId)
                .setQuantity(1);

        ProductAction productAction = new ProductAction(ProductAction.ACTION_ADD);
        Tracker tracker = ((MyApplication) getApplication()).getTracker();

        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Shopping steps")
                .setAction("Add dinner to cart")
                .setLabel(thisDinner)
                .addProduct(product)
                .setProductAction(productAction).build()
        );
    }

    public void startCheckout(View view) {
        Utility.showMyToast("You have started the checkout process", this);
        sendStartCheckoutHit();

        Button button = findViewById(R.id.start_chekout_btn);
        button.setVisibility(View.INVISIBLE);

        button = findViewById(R.id.checkout_step_2_btn);
        button.setVisibility(View.VISIBLE);
    }

    public void sendStartCheckoutHit() {
        Product product = new Product()
                .setName("dinner")
                .setPrice(5)
                .setVariant(thisDinner)
                .setId(thisDinnerId)
                .setQuantity(1);

        ProductAction productAction = new ProductAction(ProductAction.ACTION_CHECKOUT);
        Tracker tracker = ((MyApplication) getApplication()).getTracker();

        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Shopping steps")
                .setAction("Start checkout")
                .setLabel(thisDinner)
                .addProduct(product)
                .setProductAction(productAction)
                .build());
    }

    public void getPaymentInfo (View view) {
        Utility.showMyToast("Give me your payment info", this);
        sendPaymentInfoHit();

        Button button = (Button) findViewById(R.id.checkout_step_2_btn);
        button.setVisibility(View.INVISIBLE);

        button = (Button) findViewById(R.id.purchase_btn);
        button.setVisibility(View.VISIBLE);

    }

    private void sendPaymentInfoHit() {
        Product product = new Product()
                .setName("dinner")
                .setPrice(5)
                .setVariant(thisDinner)
                .setId(thisDinnerId)
                .setQuantity(1);

        ProductAction productAction =
                new ProductAction(ProductAction.ACTION_CHECKOUT_OPTION)
                        .setCheckoutStep(2);

        Tracker tracker = ((MyApplication) getApplication()).getTracker();

        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Shopping steps")
                .setAction("Get payment")
                .setLabel(thisDinner)
                .addProduct(product)
                .setProductAction(productAction)
                .build());
    }
    
    public void purchaseCart (View view) {
        Utility.showMyToast("Purchasing now!", this);
        sendPurchaseHit();
        
    }

    private void sendPurchaseHit() {
        Product product = new Product()
                .setName("dinner")
                .setPrice(5)
                .setVariant(thisDinner)
                .setId(thisDinnerId)
                .setQuantity(1);

        String tID = Utility.getUniqueTransactionId(thisDinnerId);
        ProductAction productAction = new ProductAction(ProductAction.ACTION_PURCHASE)
                .setTransactionId(tID);

        Tracker tracker = ((MyApplication) getApplication()).getTracker();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Shopping steps")
                .setAction("Purchase")
                .setLabel(thisDinner)
                .addProduct(product)
                .setProductAction(productAction)
                .build());
    }


}

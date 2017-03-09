package com.zaptiye.quiz;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import io.doorbell.android.Doorbell;

public class AboutUsActivity extends Activity  implements OnClickListener{
	
	private TextView txtEmail, txtFacebook,txtweb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_about_us);
		
		txtEmail = (TextView)findViewById(R.id.txtEmail);
		txtEmail.setOnClickListener(this);
		txtFacebook = (TextView)findViewById(R.id.txtFacebook);
		txtFacebook.setOnClickListener(this);
        txtweb= (TextView) findViewById(R.id.txtweb);
		txtweb.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.txtEmail:
            // In your activity
            int appId = 5783; // Replace with your application's ID
            String apiKey = "d8LSxl0dS1SILDySmhyBD6iPsZzShAIY2opcbe5ZGYP1MTsnimNkpcHsvtWoeZl1"; // Replace with your application's API key
            Doorbell doorbellDialog = new Doorbell(AboutUsActivity.this, appId, apiKey); // Create the Doorbell object

            doorbellDialog.setEmail(""); // Prepopulate the email address field
            //doorbellDialog.setName("Philip Manavopoulos"); // Set the name of the user (if known)
            doorbellDialog.addProperty("loggedIn", true); // Optionally add some properties
            //doorbellDialog.addProperty("username", 'manavo');
            doorbellDialog.addProperty("loginCount", 123);
            doorbellDialog.setEmailHint("Email adresiniz");
            doorbellDialog.setMessageHint("Problem nedir ?");
            doorbellDialog.setPositiveButtonText("Yolla");
            doorbellDialog.setTitle("Bildirim");
            doorbellDialog.setNegativeButtonText("Kapat");

            doorbellDialog.setEmailFieldVisibility(View.VISIBLE); // Hide the email field, since we've filled it in already
            doorbellDialog.setPoweredByVisibility(View.GONE); // Hide the "Powered by Doorbell.io" text

            // Callback for when a message is successfully sent
            doorbellDialog.setOnFeedbackSentCallback(new io.doorbell.android.callbacks.OnFeedbackSentCallback() {
                @Override
                public void handle(String message) {
                    // Show the message in a different way, or use your own message!
                    Toast.makeText(AboutUsActivity.this, message, Toast.LENGTH_LONG).show();
                }
            });

            // Callback for when the dialog is shown
            doorbellDialog.setOnShowCallback(new io.doorbell.android.callbacks.OnShowCallback() {
                @Override
                public void handle() {
                    Toast.makeText(AboutUsActivity.this, "Dialog açıldı", Toast.LENGTH_LONG).show();
                }
            });

            doorbellDialog.show();

            break;
		case R.id.txtFacebook:
			Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(getResources().getString(R.string.facebook_url)));
			startActivity(browserIntent);
			break;


            case R.id.txtweb:
                Intent zaptiye = new Intent("android.intent.action.VIEW", Uri.parse(getResources().getString(R.string.zaptiye_url)));
                startActivity(zaptiye);
                break;

		}
	}
}

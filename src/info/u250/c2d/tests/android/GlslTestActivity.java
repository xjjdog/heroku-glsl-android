package info.u250.c2d.tests.android;

import info.u250.glsl.R;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class GlslTestActivity extends AndroidApplication implements AndroidInterface{

	protected AdView adView;
	RelativeLayout layout;
	public void onCreate (Bundle bundle) {
		super.onCreate(bundle);
		// Create the layout
        layout = new RelativeLayout(this);
		adView = new AdView(this); // Put in your secret key here
        adView.setAdUnitId(getString(R.string.adId));
        adView.setAdSize(AdSize.BANNER);
		adView.loadAd(new AdRequest.Builder().build());


        // Do the stuff that initialize() would do for you
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        
        Bundle extras = getIntent().getExtras();
		String testName = (String)extras.get("name");
		File cacheDir = StorageUtils.getCacheDirectory(this);
		ApplicationListener test = new TheEngineInstance(this,cacheDir,testName);
        // Create the libgdx View
        View gameView = initializeForView(test);

        // Add the libgdx view
        layout.addView(gameView);

        // Add the AdMob view
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        layout.addView(adView, adParams);

        // Hook it all up
        setContentView(layout);

	}
	@Override
	public void onResume() {
		super.onResume();
		if (adView != null) {
			adView.resume();
		}
	}

	@Override
	public void onPause() {
		if (adView != null) {
			adView.pause();
		}
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// Destroy the AdView.
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}
	@Override
	public void showDialog(final String file,final String value) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GlslTestActivity.this);
				alertDialogBuilder.setTitle(file);
				TextView txt = new TextView(GlslTestActivity.this);
				txt.setText(value);
				ScrollView scrollView = new ScrollView(GlslTestActivity.this);
				scrollView.addView(txt);
				alertDialogBuilder.setView(scrollView);

		        alertDialogBuilder.setCancelable(false);

		        // Setting Negative "Cancel" Button
		        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		                dialog.cancel();
		            }
		        });

		        // Setting Positive "Yes" Button
		        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		                
		            }
		        });

		        AlertDialog alertDialog = alertDialogBuilder.create();

		        try {
		            alertDialog.show();
		        } catch (Exception e) {
		            // WindowManager$BadTokenException will be caught and the app would
		            // not display the 'Force Close' message
		            e.printStackTrace();
		        }			}
		});
		
	}
}

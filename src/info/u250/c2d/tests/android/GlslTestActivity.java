package info.u250.c2d.tests.android;

import java.io.File;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class GlslTestActivity extends AndroidApplication {

	private static final String XID = "a1534f653f0ed46";
	protected AdView adView;
	public void onCreate (Bundle bundle) {
		super.onCreate(bundle);
		
		
		adView = new AdView(this); // Put in your secret key here
        adView.setAdUnitId(XID);
        adView.setAdSize(AdSize.BANNER);
        adView.loadAd(new AdRequest.Builder().build());
        adView.setVisibility(View.VISIBLE);

		
//		initialize(test, config);
        
		// Create the layout
        RelativeLayout layout = new RelativeLayout(this);

        // Do the stuff that initialize() would do for you
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        
        Bundle extras = getIntent().getExtras();
		String testName = (String)extras.get("name");
		File cacheDir = StorageUtils.getCacheDirectory(this);
		ApplicationListener test = new TheEngineInstance(cacheDir,testName);
        // Create the libgdx View
        View gameView = initializeForView(test);

        // Add the libgdx view
        layout.addView(gameView);

        // Add the AdMob view
        RelativeLayout.LayoutParams adParams = 
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        //layout.addView(adView, adParams);

        // Hook it all up
        setContentView(layout);
	}
}

package info.u250.c2d.tests.android;

import android.app.Application;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import info.u250.glsl.R;

public class MyApplication extends Application {

	@Override
    public void onCreate() {
        super.onCreate();

        DisplayImageOptions defaultOptions =
        
        new DisplayImageOptions.Builder()  
        .showImageOnLoading(R.drawable.item) 
        .showImageForEmptyUri(R.drawable.item)    
          .showImageOnFail(R.drawable.item)
          .cacheInMemory(true)  
          .cacheOnDisc(true)  
          .displayer(new SimpleBitmapDisplayer()) 
          .build();
        
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())  
        .threadPoolSize(3)// default  
        .threadPriority(Thread.NORM_PRIORITY - 1)// default  
        .tasksProcessingOrder(QueueProcessingType.LIFO)  
        .memoryCache(new LruMemoryCache(2 * 1024 * 1024))  
        .memoryCacheSizePercentage(13) // default  
        .defaultDisplayImageOptions(defaultOptions)  
        //.writeDebugLogs() // Remove for release app  
        .build();  
		// Initialize ImageLoader with configuration.  
		ImageLoader.getInstance().init(config);  
    }
}

package info.u250.c2d.tests.android;

import info.u250.glsl.R;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class SubsActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
        setContentView(R.layout.subs);
       
        
        Bundle extras = getIntent().getExtras();
		int from = extras.getInt("from");
		int to = extras.getInt("to");
		
		final List<Map<String,Object>> subs = new ArrayList<Map<String,Object>>();
		for(int i=from;i<to;i++){
			Map<String,Object> sub = new HashMap<String, Object>();
			sub.put("title", "va"+i+".fragment.glsl");
			sub.put("desc", "the fragment : va"+i+".fragment.glsl");
			//sub.put("image", Drawable.createFromPath(new File("/mnt/sdcard2/shader/glsl-img/va"+i+".png").getAbsolutePath()));
			sub.put("image", "https://raw.githubusercontent.com/yadongx/glsl/master/glsl-img/va"+i+".png");
			sub.put("name", sub.get("title"));
			subs.add(sub);
		}
       
        SimpleAdapter adapter = new SimpleAdapter(this, subs, R.layout.item,
        		new String[] { "title", "desc", "image" },
        		new int[] { R.id.item_title, R.id.item_description, R.id.item_image });
        setListAdapter(adapter);
        
        adapter.setViewBinder(new ViewBinder(){
            public boolean setViewValue(View view,Object data,String textRepresentation){
                if(view instanceof ImageView && data instanceof String){
                    ImageView iv=(ImageView)view;
//                  iv.setImageDrawable((Drawable)data);
                    ImageLoader.getInstance().displayImage(String.valueOf(data), iv);

                    return true;
                }
                else return false;
            }
        });
        
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	Map<?, ?> item = (Map<?, ?>)getListAdapter().getItem(position);
    	Bundle bundle = new Bundle();
		bundle.putString("name", item.get("name").toString());
		Intent intent = new Intent(this, GlslTestActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	

}

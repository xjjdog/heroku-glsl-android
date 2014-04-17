package info.u250.c2d.tests.android;

import info.u250.glsl.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AndroidTestStarter extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //process
        List<Map<String,Object>> tests = new ArrayList<Map<String,Object>>();
        int total = 11701;
        int per = 100;
        
        for(int i=0;i<total/per;i++){
        	Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", "The examples : "+i*per+"-"+(i+1)*per);
			map.put("desc", "Click to view more .");
			map.put("image", getResources().getIdentifier("drawable/item", null, getPackageName()));
			map.put("from", i*per);
			map.put("to", (i+1)*per);
			tests.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, tests, R.layout.item,
        		new String[] { "title", "desc", "image" },
        		new int[] { R.id.item_title, R.id.item_description, R.id.item_image });
        setListAdapter(adapter);
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	Map<?, ?> item = (Map<?, ?>)getListAdapter().getItem(position);
		System.out.println();
		
		Bundle bundle = new Bundle();
		bundle.putInt("from", Integer.parseInt(item.get("from")+""));
		bundle.putInt("to", Integer.parseInt(item.get("to")+""));
		Intent intent = new Intent(this, SubsActivity.class);
		intent.putExtras(bundle);

		startActivity(intent);
	}
}

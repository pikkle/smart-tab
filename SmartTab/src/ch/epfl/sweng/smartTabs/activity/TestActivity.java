package ch.epfl.sweng.smartTabs.activity;

import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.R.id;
import ch.epfl.sweng.smartTabs.R.layout;
import ch.epfl.sweng.smartTabs.R.menu;
import ch.epfl.sweng.smartTabs.gfx.FooterView;
import ch.epfl.sweng.smartTabs.gfx.HeaderView;
import ch.epfl.sweng.smartTabs.gfx.MusicSheetView;
import ch.epfl.sweng.smartTabs.gfx.TablatureView;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestActivity extends Activity {
	private HeaderView headerView;
	private FooterView footerView;
	private MusicSheetView musicSheetView;
	private TablatureView tablatureView;
	private Tab tab;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Intent intent = getIntent();
		Tab tab = (Tab) intent.getExtras().getSerializable("tab");
		
		setContentView(R.layout.activity_test);
		
		headerView = (HeaderView) (this.findViewById(R.id.headerView));
		footerView = (FooterView) (this.findViewById(R.id.footerView));
		musicSheetView = (MusicSheetView) (this.findViewById(R.id.musicSheetView));
		tablatureView = (TablatureView) (this.findViewById(R.id.tablatureView));
		
		headerView.setTitle(tab.getTabName());
		tablatureView.setTab(tab);
		tablatureView.setInstrument(Instrument.GUITAR);
		
		// Basic scrolling
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					tablatureView.scrollBy(10, 0);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
			if (footerView.isDisplayed()) {
				footerView.hide();
			} else {
				footerView.show();
			}
		}
		return true;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

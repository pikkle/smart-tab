package ch.epfl.sweng.smartTabs.gfx;

import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;
import android.content.Context;
import android.widget.HorizontalScrollView;

/**
 * @author JadKhoury
 *
 */
public class ScrollingView extends HorizontalScrollView{
	private TablatureView tablatureView;
	private MusicSheetView musicSheetView;
	

	public ScrollingView(Context context, Tab tab, Instrument instr, int pace) {
		super(context);
		tablatureView = new TablatureView(context, tab, instr, pace);
		musicSheetView = new MusicSheetView(context);
	}

	public boolean isTerminated() {
		return tablatureView.isTerminated();
	}

}

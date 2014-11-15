/**
 * 
 */
package ch.epfl.sweng.smartTabs.gfx;

import java.security.InvalidParameterException;

import android.graphics.Rect;

/**
 * Rect wrapper adding padding to it
 * @author pikkle
 */
public class Box {
	private Rect rect;
	private int padding;
	
	/**
	 * Creates a box with the given coordinates and the given padding
	 * @param left The X coordinate of the left side of the box
	 * @param top The Y coordinate of the top side of the box
	 * @param right The X coordinate of the right side of the box
	 * @param bottom The Y coordinate of the bottom side of the box
	 * @param padding The padding to apply to the box
	 * @throws InvalidParameterException when the padding is too  big or if the coordinates are bad (left>right || top>bottom)
	 */
	public Box(int left, int top, int right, int bottom, int padding) throws InvalidParameterException{
		if (padding > right-left || padding > bottom-top) {
			throw new InvalidParameterException("The padding is too high for the rectangle :" +
					"R(" + left + "," + top + "," + right + "," + bottom +") with padding="+ padding);
		} else if (left > right || top > bottom) {
			throw new InvalidParameterException("The values are invalid " + 
					"R(" + left + "," + top + "," + right + "," + bottom +")");
		}
		rect = new Rect(left, top, right, bottom);
	}
	
	/**
	 * Creates a box with a new padding
	 * @param b The base box
	 * @param padding The new padding
	 */
	public Box(Box b, int padding) {
		this(b.fLeft(), b.fTop(), b.fRight(), b.fBottom(), padding);
	}
	
	/**
	 * Gives the smallest Rectangle that contains both given rectangles
	 * @param r1 The first rectangle to connect
	 * @param r2 The second rectangle to connect
	 * @return The big box that contains both r1 and r2 in it with no padding
	 */
	public static Box bigUnion(Box r1, Box r2) {
		int left = Math.min(r1.left(), r2.left());
		int top = Math.min(r1.top(), r2.top());
		int right = Math.max(r1.right(), r2.right());
		int bottom = Math.max(r1.bottom(), r2.bottom());
		return new Box(left, top, right, bottom, 0);
	}
	
	/**
	 * Intersects both rectangles. Returns r1 if they cannot be intersected. Also removes the padding
	 * @param r1 The first rectangle to intersect
	 * @param r2 The second rectangle to intersect
	 * @return The intersection rectangle
	 */
	public static Box intersection(Box r1, Box r2) {
		Box r = new Box(r1, 0);
		r.rect.intersect(r2.rect);
		return r;
	}
	
	/**
	 * Sets a new padding
	 * @param padding
	 */
	public void setPadding(int padding) throws InvalidParameterException{
		if (padding > rect.right-rect.left || padding > rect.bottom-rect.top) {
			throw new InvalidParameterException("The padding is too high for the rectangle " +
					"R(" + rect.left + "," + rect.top + "," + rect.right + "," + rect.bottom +") with padding: "+ padding);
		}
	}
	public int left() {
		return rect.left+padding;
	}
	public int top() {
		return rect.top+padding;
	}
	public int right() {
		return rect.right-padding;
	}
	public int bottom() {
		return rect.bottom-padding;
	}
	public int fLeft() {
		return rect.left;
	}
	public int fTop() {
		return rect.top;
	}
	public int fRight() {
		return rect.right;
	}
	public int fBottom() {
		return rect.bottom;
	}
	public void moveBy(int dx, int dy) {
		rect.left += dx;
		rect.top += dy;
		rect.right += dx;
		rect.bottom += dy;
	}
	
	public String toString() {
		return "Rectangle: (" + rect.toString() +"); padding: " + padding;
	}
	
	
	/**
	 * Gives a smaller rectangle within a given one, leaving a margin between them of factor delta
	 * @param r The rectangle to resize
	 * @param deltaWidth The ratio of the margin on the left and right (0 <= deltaWidth <= 1)
	 * @param deltaWidth The ratio of the margin on the top and bottom (0 <= deltaHeight <= 1)
	 * @return The smaller rectangle centered at the same point
	 * @throws InvalidParameterException if delta isn't in the given bounds
	 */
	public static Rect marginRect(Rect r, float deltaWidth, float deltaHeight) throws InvalidParameterException {
		if (deltaWidth < 0 || deltaWidth > 1 || deltaHeight < 0 || deltaHeight > 1) {
			throw new InvalidParameterException("Delta should be between 0 and 1");
		}
		int newWidth = (int) (r.width() * deltaWidth);
		int newHeight = (int) (r.height() * deltaHeight);
		
		int left = (r.width()-newWidth)/2;
		int top = (r.height()-newHeight)/2;
		int right = left + newWidth;
		int bottom = top + newHeight;
		
		return new Rect(left, top, right, bottom);
	}
	
	/**
	 * Gives a smaller rectangle within a given one, leaving a margin between them of a given size
	 * @param r The rectangle to resize
	 * @param margin The size of the margin to apply
	 * @return The smaller rectangle centered at the same point
	 * @throws InvalidParameterException if the margin is negative or too big for the rectangle
	 */
	public static Rect marginRect(Rect r, int margin) throws InvalidParameterException {
		
		return marginRect(r, margin, margin, margin, margin);
	}
	
	/**
	 * Gives a smaller rectangle within a given one, leaving a margin between them of a given size
	 * @param r The rectangle to resize
	 * @param marginLeft The left margin
	 * @param marginTop The top margin
	 * @param marginRight The right margin
	 * @param marginBottom The bottom margin
	 * @return The smaller rectangle centered at the same point
	 * @throws InvalidParameterException if the margin is negative or too big for the rectangle
	 */
	public static Rect marginRect(Rect r, int marginLeft, int marginTop, int marginRight, int marginBottom) 
		throws InvalidParameterException {
		if (marginLeft < 0 || marginLeft > r.width() 
				|| marginTop < 0 || marginTop > r.height()
				|| marginRight < 0 || marginRight > r.width()
				|| marginBottom < 0 || marginBottom > r.height()
				|| marginTop + marginBottom > r.height()
				|| marginLeft + marginRight > r.width()) {
			throw new InvalidParameterException(
					"The margin should not be negative nor be bigger than the given rectangle's width or height\n"+ r 
					+ " + margins: ("+ marginLeft + ", " + marginTop + ", " + marginRight + ", " + marginBottom + ")");
		}
		return new Rect(r.left+marginLeft, r.top+marginTop, r.right-marginRight, r.bottom-marginBottom);
	}
	
	/**
	 * Gives the smallest Rectangle that contains both given rectangles
	 * @param r1 The first rectangle to connect
	 * @param r2 The second rectangle to connect
	 * @return The big rectangle that contains both r1 and r2 in it
	 */
	public static Rect bigUnion(Rect r1, Rect r2) {
		int left = Math.min(r1.left, r2.left);
		int top = Math.min(r1.top, r2.top);
		int right = Math.max(r1.right, r2.right);
		int bottom = Math.max(r1.bottom, r2.bottom);
		return new Rect(left, top, right, bottom);
	}
	
	/**
	 * Intersects both rectangles. Returns r1 if they cannot be intersected.
	 * @param r1 The first rectangle to intersect
	 * @param r2 The second rectangle to intersect
	 * @return The intersection rectangle
	 */
	public static Rect intersection(Rect r1, Rect r2) {
		Rect r = new Rect(r1);
		r.intersect(r2);
		return r;
	}
}

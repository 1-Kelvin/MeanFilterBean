package Beans;

import Aufgabe2.ROIFilter;
import Events.OnImageChangeEvent;
import Listeners.OnImagePropertyChangeListener;
import pmp.interfaces.IOable;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.io.StreamCorruptedException;
import java.util.Vector;

/**
 * Project: MeanFilterBean
 *
 * @author Simon
 * Created on: 02.12.2018
 * @version 1.0
 */
public class ROI extends Label implements OnImagePropertyChangeListener, IOable {

	PlanarImage planarImageOriginal; //in super class
	PlanarImage planarImageEdited; //in super class
	private Vector<OnImagePropertyChangeListener> listeners;
	private int x;
	private int y;
	private int height;
	private ROIFilter roiFilter;

	public ROI() {
		listeners = new Vector<>();
		setText("ROI");

		x = 0;
		y = 50;
		height = 70;

		roiFilter = new ROIFilter(x,y,height,this,this);
	}

	@Override
	public void onImagePropertyChanged(OnImageChangeEvent event) {
		this.planarImageOriginal = event.getPlanarImage();
		processROI();

		fireEvent();
	}

	//move to super class and use filter super class to write
	private void processROI() {
		try {
			roiFilter.write(planarImageOriginal);
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		}
	}

	//move to super class
	private void fireEvent() {
		OnImageChangeEvent event = new OnImageChangeEvent(this, planarImageEdited);
		for (OnImagePropertyChangeListener listener : listeners) {
			listener.onImagePropertyChanged(event);
		}
	}

	public void addOnImagePropertyChangeListener(OnImagePropertyChangeListener listener) {
		listeners.add(listener);
		fireEvent();
	}

	public void removeOnImagePropertyChangeListener(OnImagePropertyChangeListener listener) {
		listeners.remove(listener);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public Object read() throws StreamCorruptedException {
		return planarImageOriginal;
	}

	@Override
	public void write(Object value) throws StreamCorruptedException {
		planarImageEdited = (PlanarImage) value;
	}
}

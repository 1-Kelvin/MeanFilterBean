package Beans;

import Aufgabe2.MedianFilter;
import Events.OnImageChangeEvent;
import Listeners.OnImagePropertyChangeListener;
import pmp.interfaces.IOable;

import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;
import java.awt.*;
import java.io.StreamCorruptedException;
import java.util.Vector;

public class Median extends Label implements OnImagePropertyChangeListener, IOable {

    PlanarImage planarImageOriginal; //in super class
    PlanarImage planarImageEdited; //in super class
    private Vector<OnImagePropertyChangeListener> listeners;
    private int maskSize;
    private MedianFilter medianFilter;


    public Median(){
        listeners = new Vector<>();
        setText("Median");
        maskSize = 20;

        medianFilter = new MedianFilter(MedianFilterDescriptor.MEDIAN_MASK_SQUARE, maskSize, this, this);
    }


    @Override
    public void onImagePropertyChanged(OnImageChangeEvent event) {
        this.planarImageOriginal = event.getPlanarImage();
        processMedian();

        fireEvent();
    }

    private void processMedian() {
        try {
            medianFilter.write(planarImageOriginal);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

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

    public int getMaskSize() {
        return maskSize;
    }

    public void setMaskSize(int maskSize) {
        this.maskSize = maskSize;
        processMedian();
        fireEvent();
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

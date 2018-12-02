package Beans;

import Aufgabe2.ThresholdFilter;
import Events.OnImageChangeEvent;
import Listeners.OnImagePropertyChangeListener;
import pmp.interfaces.IOable;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.io.StreamCorruptedException;
import java.util.Vector;

public class Threshold extends Label implements OnImagePropertyChangeListener, IOable {
    private PlanarImage planarImageOriginal;
    private PlanarImage planarImageEdited;
    private double minimum;
    private double maximum;
    private double target;
    private Vector<OnImagePropertyChangeListener> listeners;

    private ThresholdFilter thresholdFilter;

    public Threshold () {
        listeners = new Vector<>();
        setText("Threshold");
        minimum = 0;
        maximum = 35;
        target = 255;
        setBackground(Color.CYAN);

        thresholdFilter = new ThresholdFilter(minimum, maximum, target, this, this);
    }

    @Override
    public void onImagePropertyChanged(OnImageChangeEvent event) {
        this.planarImageOriginal = event.getPlanarImage();
        processThreshold();

        fireEvent();
    }

    private void processThreshold() {
        try {
            thresholdFilter.write(planarImageOriginal);
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

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
        processThreshold();
        fireEvent();
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
        processThreshold();
        fireEvent();
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
        processThreshold();
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

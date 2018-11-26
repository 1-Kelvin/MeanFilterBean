package Filters;

import Events.OnImageChangeEvent;
import Listeners.OnImagePropertyChangeListener;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.util.Vector;

public class Threshold extends Label implements OnImagePropertyChangeListener {
    private PlanarImage planarImage;
    private double minimum;
    private double maximum;
    private double target;
    private Vector<OnImagePropertyChangeListener> listeners;

    public Threshold () {
        listeners = new Vector<>();
        setText("Threshold");
        minimum = 0;
        maximum = 35;
        target = 255;
        setBackground(Color.CYAN);
    }

    @Override
    public void onImagePropertyChanged(OnImageChangeEvent event) {
        this.planarImage = event.getPlanarImage();
        processThreshold();

        fireEvent();
    }

    private void processThreshold() {
        double[] lowThreshold = new double[1];
        double[] highThreshold = new double[1];
        double[] targetBrightness = new double[1];

        lowThreshold[0] = minimum;
        highThreshold[0] = maximum;
        targetBrightness[0] = target;

        ParameterBlock parameterBlock = new ParameterBlock();
        parameterBlock.addSource(planarImage);
        parameterBlock.add(lowThreshold);
        parameterBlock.add(highThreshold);
        parameterBlock.add(targetBrightness);

        planarImage = JAI.create("Threshold", parameterBlock);
    }

    private void fireEvent() {
        OnImageChangeEvent event = new OnImageChangeEvent(this, planarImage);
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
        fireEvent();
        this.minimum = minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        fireEvent();
        this.maximum = maximum;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        fireEvent();
        this.target = target;
    }
}

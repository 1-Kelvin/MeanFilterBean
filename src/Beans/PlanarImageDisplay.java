package Beans;

import Events.OnImageChangeEvent;
import Listeners.OnImagePropertyChangeListener;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class PlanarImageDisplay extends Canvas implements OnImagePropertyChangeListener {
    private PlanarImage planarImage;
    private Vector<OnImagePropertyChangeListener> listeners;

    public PlanarImageDisplay() {
        listeners = new Vector<>();
        setSize(50, 50);
        setBackground(Color.GREEN);
    }

    @Override
    public void onImagePropertyChanged(OnImageChangeEvent event) {
        planarImage = event.getPlanarImage();

        if(planarImage != null){
            repaint();
        }
        fireEvent();
    }

    @Override
    public void paint(Graphics g) {
        if(planarImage != null) {

            BufferedImage bufferedImage = planarImage.getAsBufferedImage();
            setSize(bufferedImage.getWidth(), bufferedImage.getHeight());
            g.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        }
    }

    private void fireEvent() {
        OnImageChangeEvent event = new OnImageChangeEvent(this, planarImage);

        for(OnImagePropertyChangeListener listener : listeners) {
            listener.onImagePropertyChanged(event);
        }
    }

    public void addOnImagePropertyChangeListener(OnImagePropertyChangeListener listener) {
        listeners.add(listener);
        fireEvent();
    }

    public void removeImagePropertyChangeListener(OnImagePropertyChangeListener listener) {
        listeners.remove(listener);
    }
}

package Filters;

import Events.OnFileChangeEvent;
import Events.OnImageChangeEvent;
import Listeners.OnFileChangeListener;
import Listeners.OnImagePropertyChangeListener;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.awt.*;
import java.io.File;
import java.util.Vector;

public class PlanarImageConverter extends Label implements OnFileChangeListener {
    private PlanarImage planarImage;
    private Vector<OnImagePropertyChangeListener> listeners;

    public PlanarImageConverter () {
        listeners = new Vector<>();
        setText("PlanarImageConverter");
        setBackground(Color.RED);
    }

    public void setPlanarImage(File file) {
        planarImage = JAI.create("fileload", file.getAbsolutePath());
    }

    @Override
    public void onFileChanged(OnFileChangeEvent event) {
        setPlanarImage(event.getFile());
        fireEvent();
    }

    private void fireEvent() {
        OnImageChangeEvent event = new OnImageChangeEvent(this, planarImage);

        for (OnImagePropertyChangeListener listener : listeners) {
            listener.onImagePropertyChanged(event);
        }
    }

    public void addOnImagePropertyChangeListener(OnImagePropertyChangeListener onImagePropertyChangeListener) {
        listeners.add(onImagePropertyChangeListener);
        fireEvent();
    }

    public void removeOnImagePropertyChangeListener(OnImagePropertyChangeListener onImagePropertyChangeListener) {
        listeners.remove(onImagePropertyChangeListener);
    }
}

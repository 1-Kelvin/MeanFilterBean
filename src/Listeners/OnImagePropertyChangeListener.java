package Listeners;

import Events.OnImageChangeEvent;

import java.util.EventListener;
import java.util.EventObject;

public interface OnImagePropertyChangeListener extends EventListener {
    void onImagePropertyChanged(OnImageChangeEvent event);
}

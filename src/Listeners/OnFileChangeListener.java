package Listeners;

import Events.OnFileChangeEvent;

import java.io.File;
import java.util.EventListener;

public interface OnFileChangeListener extends EventListener {
    void onFileChanged(OnFileChangeEvent event);
}

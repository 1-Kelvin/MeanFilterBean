package Beans;

import Events.OnFileChangeEvent;
import Listeners.OnFileChangeListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

public class FileChooser extends Label {
    private File file;
    private Vector<OnFileChangeListener> listeners;

    public FileChooser() {
        setText("MeanFileChooser");
        listeners = new Vector<>();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onMouseClicked(e);
            }
        });
    }

    public void onMouseClicked(MouseEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        FileFilter fileFilter = new FileNameExtensionFilter("Images", ImageIO.getReaderFileSuffixes());
        jFileChooser.setFileFilter(fileFilter);
        int returnValue = jFileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            file = jFileChooser.getSelectedFile();
            setText(file.getName());
            fireEvent();
        }
    }

    public void addOnFileChangeListener(OnFileChangeListener listener) {
        listeners.add(listener);
        fireEvent();
    }

    public void removeOnFileChangeListener(OnFileChangeListener listener) {
        listeners.remove(listener);
    }

    private void fireEvent() {
        OnFileChangeEvent event = new OnFileChangeEvent(this, file);

        for (OnFileChangeListener listener : listeners) {
            listener.onFileChanged(event);
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

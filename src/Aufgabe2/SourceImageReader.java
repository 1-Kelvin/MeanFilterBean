package Aufgabe2;

import pmp.filter.Source;
import pmp.interfaces.Writeable;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

public class SourceImageReader extends Source<PlanarImage> {

    private String _imagePath;
    private boolean _imageRead;

    public SourceImageReader(String imagePath) throws InvalidParameterException {
        _imagePath = imagePath;
    }

    public SourceImageReader(String imagePath, Writeable<PlanarImage> output) throws InvalidParameterException {
        super(output);
        _imagePath = imagePath;
    }

    @Override
    public PlanarImage read() throws StreamCorruptedException {
        PlanarImage image = null;
        if (!_imageRead) {
            image = JAI.create("fileload", _imagePath);
            _imageRead = true;
        }
        return image;
    }
}

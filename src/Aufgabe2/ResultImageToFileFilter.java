package Aufgabe2;

import pmp.filter.DataTransformationFilter1;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import java.security.InvalidParameterException;

public class ResultImageToFileFilter extends DataTransformationFilter1<PlanarImage> {

    private String _destinationPath;

    public ResultImageToFileFilter(String destination, Readable<PlanarImage> input, Writeable<PlanarImage> output) throws InvalidParameterException {
        super(input, output);
        _destinationPath = destination;
    }

    public ResultImageToFileFilter(String destination, Readable<PlanarImage> input) throws InvalidParameterException {
        super(input);
        _destinationPath = destination;
    }

    public ResultImageToFileFilter(String destination, Writeable<PlanarImage> output) throws InvalidParameterException {
        super(output);
        _destinationPath = destination;
    }

    @Override
    protected void process(PlanarImage image) {
        RenderedOp op = JAI.create("filestore", image, _destinationPath, "PNG");
    }
}

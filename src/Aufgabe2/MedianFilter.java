package Aufgabe2;

import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;
import javax.media.jai.operator.MedianFilterShape;
import java.security.InvalidParameterException;

public class MedianFilter extends DataTransformationFilter2<PlanarImage, PlanarImage> {

    private MedianFilterShape _filterShape;
    private int _maskSize;

    public MedianFilter(MedianFilterShape filterShape, int maskSize, Readable<PlanarImage> input, Writeable<PlanarImage> output) throws InvalidParameterException {
        super(input, output);
        _filterShape = filterShape;
        _maskSize = maskSize;
    }

    public MedianFilter(MedianFilterShape filterShape, int maskSize, Readable<PlanarImage> input) throws InvalidParameterException {
        super(input);
        _filterShape = filterShape;
        _maskSize = maskSize;
    }

    public MedianFilter(MedianFilterShape filterShape, int maskSize, Writeable<PlanarImage> output) throws InvalidParameterException {
        super(output);
        _filterShape = filterShape;
        _maskSize = maskSize;
    }

    @Override
    protected PlanarImage process(PlanarImage image) {
        return MedianFilterDescriptor.create(image, _filterShape, _maskSize, null);
    }
}

package Aufgabe2;

import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.security.InvalidParameterException;

public class ROIFilter extends DataTransformationFilter2<PlanarImage, PlanarImage> {

    private int _x;
    private int _y;
    private int _height;

    public ROIFilter(int x, int y, int height, Readable<PlanarImage> input, Writeable<PlanarImage> output) throws InvalidParameterException {
        super(input, output);
        _x = x;
        _y = y;
        _height = height;
    }

    public ROIFilter(int x, int y, int height, Readable<PlanarImage> input) throws InvalidParameterException {
        super(input);
        _x = x;
        _y = y;
        _height = height;
    }

    public ROIFilter(int x, int y, int height, Writeable<PlanarImage> output) throws InvalidParameterException {
        super(output);
        _x = x;
        _y = y;
        _height = height;
    }

    @Override
    protected PlanarImage process(PlanarImage image) {
        Rectangle rectangle = new Rectangle(_x, _y, image.getWidth() - _x, _height);
        PlanarImage imageRoi = PlanarImage.wrapRenderedImage((RenderedImage)image.getAsBufferedImage(rectangle, image.getColorModel()));
        imageRoi.setProperty("offsetX", _x);
        imageRoi.setProperty("offsetY", _y);
        return imageRoi;
    }
}

package Aufgabe2;

import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import javax.media.jai.operator.ThresholdDescriptor;
import java.security.InvalidParameterException;

public class ThresholdFilter extends DataTransformationFilter2<PlanarImage, PlanarImage> {
    private double[] _low;
    private double[] _high;
    private double[] _map;

    public ThresholdFilter(double lowValue, double highValue, double mapValue, Readable<PlanarImage> input, Writeable<PlanarImage> output) throws InvalidParameterException {
        super(input, output);
        _low = new double[]{lowValue};
        _high = new double[]{highValue};
        _map = new double[]{mapValue};
    }

    public ThresholdFilter(double lowValue, double highValue, double mapValue, Readable<PlanarImage> input) throws InvalidParameterException {
        super(input);
        _low = new double[]{lowValue};
        _high = new double[]{highValue};
        _map = new double[]{mapValue};
    }

    public ThresholdFilter(double lowValue, double highValue, double mapValue, Writeable<PlanarImage> output) throws InvalidParameterException {
        super(output);
        _low = new double[]{lowValue};
        _high = new double[]{highValue};
        _map = new double[]{mapValue};
    }

    @Override
    protected PlanarImage process(PlanarImage image) {
        PlanarImage thresholdImage = ThresholdDescriptor.create(image, _low, _high, _map, null);
        return thresholdImage;
    }
}

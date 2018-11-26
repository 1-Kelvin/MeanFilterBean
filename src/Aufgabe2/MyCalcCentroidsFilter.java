package Aufgabe2;

import CalcCentroidsFilter.CalcCentroidsFilter;
import CalcCentroidsFilter.Coordinate;
import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class MyCalcCentroidsFilter  extends DataTransformationFilter2<PlanarImage, Result> {

    private CalcCentroidsFilter _calcCentroidsFilter;

    public MyCalcCentroidsFilter(Readable<PlanarImage> input) throws InvalidParameterException {
        super(input);
        _calcCentroidsFilter = new CalcCentroidsFilter(input);
    }

    public MyCalcCentroidsFilter(Writeable output) throws InvalidParameterException {
        super(output);
        _calcCentroidsFilter = new CalcCentroidsFilter(output);
    }

    @Override
    protected Result process(PlanarImage image) {
        ArrayList<Coordinate> coordinates = _calcCentroidsFilter.process(image);
        Result result = new Result(coordinates.size(), coordinates, image);
        return result;
    }
}

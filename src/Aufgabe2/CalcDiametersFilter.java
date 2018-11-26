package Aufgabe2;

import CalcCentroidsFilter.Coordinate;
import pmp.filter.DataTransformationFilter1;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class CalcDiametersFilter extends DataTransformationFilter1<Result> {

    public CalcDiametersFilter(Readable<Result> input, Writeable<Result> output) throws InvalidParameterException {
        super(input, output);
    }

    public CalcDiametersFilter(Readable<Result> input) throws InvalidParameterException {
        super(input);
    }

    public CalcDiametersFilter(Writeable<Result> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected void process(Result result) {
        ArrayList<Coordinate> coordinates = result.getCalculatedCoordinates();
        PlanarImage image = result.getImage();

        if (coordinates != null && !coordinates.isEmpty() && image != null) {
            BufferedImage bi = image.getAsBufferedImage();

            for (Coordinate coordinate: coordinates) {
                int x = coordinate._x - (Integer) image.getProperty("offsetX");
                int y = coordinate._y - (Integer) image.getProperty("offsetY");
                int p = bi.getRaster().getSample(x + 1, y, 0);
                int radius = 0;
                while (p == 255) {
                    x++;
                    p = bi.getRaster().getSample(x, y, 0);
                    radius++;
                }
                result.addDiameter(radius * 2);  // Result den Durchmesser hinzufügen
            }
        }
    }
}

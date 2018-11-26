package Aufgabe2;

import CalcCentroidsFilter.Coordinate;
import pmp.filter.DataTransformationFilter1;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class EvaluateResultFilter extends DataTransformationFilter1<Result> {
    private List<Coordinate> _expectedCoordinates;
    private int _tolerance;

    public EvaluateResultFilter(int tolerance, String expectedResultPath, Readable<Result> input, Writeable<Result> output) throws InvalidParameterException, IOException {
        super(input, output);
        _tolerance = tolerance;
        readExpectedCoordinates(expectedResultPath);
    }

    public EvaluateResultFilter(int tolerance, String expectedResultPath, Readable<Result> input) throws InvalidParameterException, IOException {
        super(input);
        _tolerance = tolerance;
        readExpectedCoordinates(expectedResultPath);
    }

    public EvaluateResultFilter(int tolerance, String expectedResultPath, Writeable<Result> output) throws InvalidParameterException, IOException {
        super(output);
        _tolerance = tolerance;
        readExpectedCoordinates(expectedResultPath);
    }

    private void readExpectedCoordinates(String expectedResultPath) throws IOException {
        FileReader fileReader = new FileReader(expectedResultPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        _expectedCoordinates = new ArrayList<>();
        String line = bufferedReader.readLine();

        String[] splittedLine = line.split("\\[");
        String[] coordinates = splittedLine[1].split(", ");
        for (int i = 0; i < coordinates.length; i++) {
            String x_y_Coordinates = coordinates[i].replaceAll("[^\\d,]", "");
            String[] x_y_Coordinates_splitted = x_y_Coordinates.split(",");
            Coordinate coordinate = new Coordinate(Integer.parseInt(x_y_Coordinates_splitted[0]), Integer.parseInt(x_y_Coordinates_splitted[1]));
            _expectedCoordinates.add(coordinate);
        }

    }

    @Override
    protected void process(Result result) {
        ArrayList<Coordinate> resultCoordinates = result.getCalculatedCoordinates();

        for (int i = 0; i < resultCoordinates.size(); i++) {
            Coordinate resultCoordinate = resultCoordinates.get(i);
            Coordinate expectedCoordinate = _expectedCoordinates.get(i);
            int aberrationX = Math.abs(resultCoordinate._x - expectedCoordinate._x);
            int aberrationY = Math.abs(resultCoordinate._y - expectedCoordinate._y);
            if (aberrationX <= _tolerance && aberrationY <= _tolerance) {  // liegt im Toleranzbereich
                result.addResultInfo(expectedCoordinate, aberrationX, aberrationY, true);
            } else {
                result.addResultInfo(expectedCoordinate, aberrationX, aberrationY, false);
            }
        }
    }
}

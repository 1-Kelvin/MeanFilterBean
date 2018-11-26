package Aufgabe2;

import CalcCentroidsFilter.Coordinate;

import javax.media.jai.PlanarImage;
import java.util.ArrayList;

public class Result {
    private int _amount;
    private ArrayList<Coordinate> _calculatedCoordinates;
    private ArrayList<Coordinate> _expectedCoordinates;
    private ArrayList<Integer> _aberrationX;
    private ArrayList<Integer> _aberrationY;
    private ArrayList<Boolean> _isInToleranceRange;
    private ArrayList<Integer> _diameters;
    private PlanarImage _image;

    public Result(int amount, ArrayList<Coordinate> calculatedCoordinates, PlanarImage image) {
        _amount = amount;
        _calculatedCoordinates = calculatedCoordinates;
        _expectedCoordinates = new ArrayList<>();
        _aberrationX = new ArrayList<>();
        _aberrationY = new ArrayList<>();
        _isInToleranceRange = new ArrayList<>();
        _diameters = new ArrayList<>();
        _image = image;
    }

    public void addResultInfo(Coordinate expected, int aberrationX, int aberrationY, boolean isInToleranceRange) {
        _expectedCoordinates.add(expected);
        _aberrationX.add(aberrationX);
        _aberrationY.add(aberrationY);
        _isInToleranceRange.add(isInToleranceRange);
    }

    public void addDiameter(int diameter) {
        _diameters.add(diameter);
    }

    public int get_amount() {
        return _amount;
    }

    public ArrayList<Coordinate> getCalculatedCoordinates() {
        return _calculatedCoordinates;
    }

    public ArrayList<Coordinate> getExpectedCoordinates() {
        return _expectedCoordinates;
    }

    public ArrayList<Integer> getAberrationX() {
        return _aberrationX;
    }

    public ArrayList<Integer> getAberrationY() {
        return _aberrationY;
    }

    public ArrayList<Boolean> getIsInToleranceRange() {
        return _isInToleranceRange;
    }

    public ArrayList<Integer> getDiameters() {
        return _diameters;
    }

    public PlanarImage getImage() {
        return _image;
    }
}

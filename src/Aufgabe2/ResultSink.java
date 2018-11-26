package Aufgabe2;

import pmp.filter.Sink;
import pmp.interfaces.Readable;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

public class ResultSink extends Sink<Result> {

    private PrintWriter _pWriter;

    public ResultSink(String sinkPath) throws FileNotFoundException {
        _pWriter = new PrintWriter(sinkPath);
    }

    public ResultSink(String sinkPath, Readable<Result> input) throws InvalidParameterException, FileNotFoundException {
        super(input);
        _pWriter = new PrintWriter(sinkPath);
    }

    public void write(Result result) throws StreamCorruptedException {
        if (result != null) {
            _pWriter.println("Ergebnisse: ");
            _pWriter.println("Es wurden " + result.get_amount() + " Loetstellen gefunden.");
            _pWriter.println("Erwartet X \t Gefunden X \t ABWEICHUNG \t Erwartet Y \t Gefunden Y \t ABWEICHUNG \t X und Y in Toleranzberich \t Durchmesser");
            for (int i = 0; i < result.get_amount(); i++) {
                _pWriter.print(result.getExpectedCoordinates().get(i)._x + "\t\t\t\t");
                _pWriter.print(result.getCalculatedCoordinates().get(i)._x + "\t\t\t\t");
                _pWriter.print(result.getAberrationX().get(i).toString() + "\t\t\t\t");
                _pWriter.print(result.getExpectedCoordinates().get(i)._y + "\t\t\t\t");
                _pWriter.print(result.getCalculatedCoordinates().get(i)._y + "\t\t\t\t");
                _pWriter.print(result.getAberrationY().get(i).toString() + "\t\t\t\t");
                _pWriter.print(result.getIsInToleranceRange().get(i).toString() + "\t\t\t\t\t\t");
                _pWriter.println(result.getDiameters().get(i));
            }
            _pWriter.flush();
            _pWriter.close();
        }
    }



}

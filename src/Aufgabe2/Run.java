package Aufgabe2;

import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;
import pmp.pipes.SimplePipe;

import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;
import java.io.IOException;

public class Run {

    public static void main(String[] args) {
        System.setProperty("com.sun.media.jai.disableMediaLib", "true");
        args = new String[5];

        args[0] = "push";
        args[1] = "sourcecode/src/resources/loetstellen.jpg";
        args[2] = "resultImage.png";
        args[3] = "sourcecode/src/resources/expectedCentroids.txt";
        args[4] = "sourcecode/src/resources/result.txt";

        if (args.length != 5) {
            System.out.println("Bitte geben Sie folgende Parameter an: ");
            System.out.println("Parameter1: entweder \"push\" oder \"pull\"");
            System.out.println("Parameter2: Pfad des Bildes mit den Loetstellen");
            System.out.println("Parameter3: Pfad für das Resultatbild");
            System.out.println("Parameter4: Pfad der Datei mit den erwarteten Mittelpunkten");
            System.out.println("Parameter5: Pfad für die Textdatei mit dem Ergebnis der Auswertung");
        } else {
            String strategy = args[0];                  // push or pipe
            String sourceImagePath = args[1];           // src/loetstellen.JPG
            String resultImageDestination = args[2];    // src/resultImage.png
            String expectedResultPath = args[3];        // src/expectedCentroids.txt
            String resultDestination = args[4];         // src/result.txt
            if (strategy.equalsIgnoreCase("push")) {
                try {
                    runPushStrategy(sourceImagePath, resultImageDestination, expectedResultPath, resultDestination);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (strategy.equalsIgnoreCase("pull")) {
                try {
                    runPullStrategy(sourceImagePath, resultImageDestination, expectedResultPath, resultDestination);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Bitte geben Sie an welche Strategie ausgeführt werden soll: push oder pull");
            }
        }
    }

    public static void runPushStrategy(String sourceImagePath, String resultImageDestination, String expectedResultPath, String resultDestination) throws IOException {
        ResultSink resultSink = new ResultSink(resultDestination);
        SimplePipe<Result> resultPipe = new SimplePipe<>(resultSink);

        EvaluateResultFilter evaluateResultFilter = new EvaluateResultFilter(3, expectedResultPath, (Writeable<Result>) resultPipe);
        SimplePipe<Result> evaluatedResultPipe = new SimplePipe<>((Writeable<Result>) evaluateResultFilter);

        CalcDiametersFilter calcDiametersFilter = new CalcDiametersFilter((Writeable<Result>) evaluatedResultPipe);
        SimplePipe<Result> diametersPipe = new SimplePipe<>((Writeable<Result>) calcDiametersFilter);

        MyCalcCentroidsFilter myCalcCentroidsFilter = new MyCalcCentroidsFilter((Writeable<Result>) diametersPipe);
        SimplePipe<PlanarImage> centroidsPipe = new SimplePipe<>((Writeable<PlanarImage>) myCalcCentroidsFilter);

        ResultImageToFileFilter resultImageToFileFilter = new ResultImageToFileFilter(resultImageDestination, (Writeable<PlanarImage>) centroidsPipe);
        SimplePipe<PlanarImage> resultImagePipe = new SimplePipe<>((Writeable<PlanarImage>) resultImageToFileFilter);
        DisplayFilter displayOpening = new DisplayFilter("Opening Operator", (Writeable<PlanarImage>) resultImagePipe);
        SimplePipe<PlanarImage> openingPipe = new SimplePipe<>((Writeable<PlanarImage>) displayOpening);

        float[] kernelMatrix = new float[]{
                0, 1, 0,
                1, 1, 1,
                0, 1, 0
        };
        OpeningFilter openingFilter = new OpeningFilter(kernelMatrix, 3,3, 5, (Writeable<PlanarImage>) openingPipe);
        SimplePipe<PlanarImage> openingPipe2 = new SimplePipe<PlanarImage>((Writeable<PlanarImage>) openingFilter);
        DisplayFilter displayMedian = new DisplayFilter("Median Operator", (Writeable<PlanarImage>) openingPipe2);
        SimplePipe<PlanarImage> medianDisplayPipe = new SimplePipe<PlanarImage>((Writeable<PlanarImage>) displayMedian);

        MedianFilter medianFilter = new MedianFilter(MedianFilterDescriptor.MEDIAN_MASK_SQUARE, 5, (Writeable<PlanarImage>) medianDisplayPipe);
        SimplePipe<PlanarImage> medianPipe = new SimplePipe<PlanarImage>((Writeable<PlanarImage>) medianFilter);
        DisplayFilter displayThreshold = new DisplayFilter("Threshold Operator", (Writeable<PlanarImage>) medianPipe);
        SimplePipe<PlanarImage> thresholdDisplayPipe = new SimplePipe<PlanarImage>((Writeable<PlanarImage>) displayThreshold);

        ThresholdFilter thresholdFilter = new ThresholdFilter(0, 35, 255, (Writeable<PlanarImage>) thresholdDisplayPipe);
        SimplePipe<PlanarImage> thresholdPipe = new SimplePipe<PlanarImage>((Writeable<PlanarImage>) thresholdFilter);
        DisplayFilter displayRoi = new DisplayFilter("ROI Operator", (Writeable<PlanarImage>) thresholdPipe);
        SimplePipe<PlanarImage> roiDisplayPipe = new SimplePipe<PlanarImage>((Writeable<PlanarImage>) displayRoi);

        ROIFilter roiFilter = new ROIFilter(55, 50, 70, (Writeable<PlanarImage>) roiDisplayPipe);
        SimplePipe<PlanarImage> roiPipe = new SimplePipe<PlanarImage>((Writeable<PlanarImage>) roiFilter);
        DisplayFilter displaySource = new DisplayFilter("Original image", (Writeable<PlanarImage>) roiPipe);
        SimplePipe<PlanarImage> sourcePipe = new SimplePipe<>((Writeable<PlanarImage>) displaySource);

        SourceImageReader sourceImageReader = new SourceImageReader(sourceImagePath, sourcePipe);
        sourceImageReader.run();
    }

    public static void runPullStrategy(String sourceImagePath, String resultImageDestination, String expectedResultPath, String resultDestination) throws IOException {
        SourceImageReader sourceImageReader = new SourceImageReader(sourceImagePath);
        SimplePipe<PlanarImage> sourcePipe = new SimplePipe<>(sourceImageReader);
        DisplayFilter displaySource = new DisplayFilter("Original image", (Readable<PlanarImage>) sourcePipe);
        SimplePipe<PlanarImage> sourcePipe2 = new SimplePipe<PlanarImage>((Readable<PlanarImage>) displaySource);

        ROIFilter roiFilter = new ROIFilter(55, 50, 70, (Readable<PlanarImage>) sourcePipe2);
        SimplePipe<PlanarImage> roiPipe = new SimplePipe<PlanarImage>((Readable<PlanarImage>) roiFilter);
        DisplayFilter displayRoi = new DisplayFilter("ROI Operator", (Readable<PlanarImage>) roiPipe);
        SimplePipe<PlanarImage> roiPipe2 = new SimplePipe<PlanarImage>((Readable<PlanarImage>) displayRoi);

        ThresholdFilter thresholdFilter = new ThresholdFilter(0, 35, 255, (Readable<PlanarImage>) roiPipe2);
        SimplePipe<PlanarImage> thresholdPipe = new SimplePipe<PlanarImage>((Readable<PlanarImage>) thresholdFilter);
        DisplayFilter displayThreshold = new DisplayFilter("Threshold Operator", (Readable<PlanarImage>) thresholdPipe);
        SimplePipe<PlanarImage> thresholdPipe2 = new SimplePipe<PlanarImage>((Readable<PlanarImage>) displayThreshold);

        MedianFilter medianFilter = new MedianFilter(MedianFilterDescriptor.MEDIAN_MASK_SQUARE, 5, (Readable<PlanarImage>) thresholdPipe2);
        SimplePipe<PlanarImage> medianPipe = new SimplePipe<PlanarImage>((Readable<PlanarImage>) medianFilter);
        DisplayFilter displayMedian = new DisplayFilter("Median Operator", (Readable<PlanarImage>) medianPipe);
        SimplePipe<PlanarImage> medianPipe2 = new SimplePipe<PlanarImage>((Readable<PlanarImage>) displayMedian);

        float[] kernelMatrix = new float[]{
                0, 1, 0,
                1, 1, 1,
                0, 1, 0
        };
        OpeningFilter openingFilter = new OpeningFilter(kernelMatrix, 3,3, 5, (Readable<PlanarImage>) medianPipe2);
        SimplePipe<PlanarImage> openingPipe = new SimplePipe<>((Readable<PlanarImage>) openingFilter);
        DisplayFilter displayOpening = new DisplayFilter("Opening Operator", (Readable<PlanarImage>) openingPipe);
        SimplePipe<PlanarImage> openingPipe2 = new SimplePipe<>((Readable<PlanarImage>) displayOpening);

        ResultImageToFileFilter resultImageToFileFilter = new ResultImageToFileFilter(resultImageDestination, (Readable<PlanarImage>) openingPipe2);
        SimplePipe<PlanarImage> resultImagePipe = new SimplePipe<>((Readable<PlanarImage>) resultImageToFileFilter);

        MyCalcCentroidsFilter myCalcCentroidsFilter = new MyCalcCentroidsFilter((Readable<PlanarImage>) resultImagePipe);
        SimplePipe<Result> centroidsPipe = new SimplePipe<Result>(myCalcCentroidsFilter);

        CalcDiametersFilter calcCentroidsAndDiametersFilter = new CalcDiametersFilter((Readable<Result>) centroidsPipe);
        SimplePipe<Result> calcCentroidsAndDiametersPipe = new SimplePipe<>((Readable<Result>) calcCentroidsAndDiametersFilter);

        EvaluateResultFilter evaluateResultFilter = new EvaluateResultFilter(3, expectedResultPath, (Readable<Result>)calcCentroidsAndDiametersPipe);
        SimplePipe<Result> resultPipe = new SimplePipe<>((Readable<Result>) evaluateResultFilter);

        ResultSink resultSink = new ResultSink(resultDestination, resultPipe);
        resultSink.run();
    }
}

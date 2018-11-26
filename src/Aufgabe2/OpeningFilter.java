package Aufgabe2;

import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import javax.media.jai.operator.DilateDescriptor;
import javax.media.jai.operator.ErodeDescriptor;
import java.security.InvalidParameterException;

public class OpeningFilter extends DataTransformationFilter2<PlanarImage, PlanarImage> {

    private float[] _kernelMatrix;
    private int _kernelDimensionX;
    private int _kernelDimensionY;
    private int _executionAmount;

    public OpeningFilter(float[] kernelMatrix, int kernelDimensionX, int kernelDimensionY, int executionAmount, Readable<PlanarImage> input, Writeable<PlanarImage> output) throws InvalidParameterException {
        super(input, output);
        _kernelMatrix = kernelMatrix;
        _kernelDimensionX = kernelDimensionX;
        _kernelDimensionY = kernelDimensionY;
        _executionAmount = executionAmount;
    }

    public OpeningFilter(float[] kernelMatrix, int kernelDimensionX, int kernelDimensionY, int executionAmount, Readable<PlanarImage> input) throws InvalidParameterException {
        super(input);
        _kernelMatrix = kernelMatrix;
        _kernelDimensionX = kernelDimensionX;
        _kernelDimensionY = kernelDimensionY;
        _executionAmount = executionAmount;
    }

    public OpeningFilter(float[] kernelMatrix, int kernelDimensionX, int kernelDimensionY, int executionAmount, Writeable<PlanarImage> output) throws InvalidParameterException {
        super(output);
        _kernelMatrix = kernelMatrix;
        _kernelDimensionX = kernelDimensionX;
        _kernelDimensionY = kernelDimensionY;
        _executionAmount = executionAmount;
    }

    @Override
    protected PlanarImage process(PlanarImage image) {
        KernelJAI kernel = new KernelJAI(_kernelDimensionX, _kernelDimensionY, _kernelMatrix);

        // erode
        RenderedOp renderedOp = ErodeDescriptor.create(image, kernel, null);
        for (int i = 0; i < _executionAmount - 1; i++) {
            renderedOp = ErodeDescriptor.create(renderedOp, kernel, null);
        }

        //dilate
        for (int i = 0; i < _executionAmount; i++) {
            renderedOp = DilateDescriptor.create(renderedOp, kernel, null);
        }
        return renderedOp;
    }
}

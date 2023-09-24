package android.filterpacks.imageproc;

import android.app.slice.SliceItem;
import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;

/* loaded from: classes.dex */
public class ImageSlicer extends Filter {
    private int mInputHeight;
    private int mInputWidth;
    private Frame mOriginalFrame;
    private int mOutputHeight;
    private int mOutputWidth;
    @GenerateFieldPort(name = "padSize")
    private int mPadSize;
    private Program mProgram;
    private int mSliceHeight;
    private int mSliceIndex;
    private int mSliceWidth;
    @GenerateFieldPort(name = "xSlices")
    private int mXSlices;
    @GenerateFieldPort(name = "ySlices")
    private int mYSlices;

    public ImageSlicer(String name) {
        super(name);
        this.mSliceIndex = 0;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort(SliceItem.FORMAT_IMAGE, ImageFormat.create(3, 3));
        addOutputBasedOnInput(SliceItem.FORMAT_IMAGE, SliceItem.FORMAT_IMAGE);
    }

    @Override // android.filterfw.core.Filter
    public FrameFormat getOutputFormat(String portName, FrameFormat inputFormat) {
        return inputFormat;
    }

    private void calcOutputFormatForInput(Frame frame) {
        this.mInputWidth = frame.getFormat().getWidth();
        this.mInputHeight = frame.getFormat().getHeight();
        this.mSliceWidth = ((this.mInputWidth + this.mXSlices) - 1) / this.mXSlices;
        this.mSliceHeight = ((this.mInputHeight + this.mYSlices) - 1) / this.mYSlices;
        this.mOutputWidth = this.mSliceWidth + (this.mPadSize * 2);
        this.mOutputHeight = this.mSliceHeight + (this.mPadSize * 2);
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext context) {
        if (this.mSliceIndex == 0) {
            this.mOriginalFrame = pullInput(SliceItem.FORMAT_IMAGE);
            calcOutputFormatForInput(this.mOriginalFrame);
        }
        FrameFormat inputFormat = this.mOriginalFrame.getFormat();
        MutableFrameFormat outputFormat = inputFormat.mutableCopy();
        outputFormat.setDimensions(this.mOutputWidth, this.mOutputHeight);
        Frame output = context.getFrameManager().newFrame(outputFormat);
        if (this.mProgram == null) {
            this.mProgram = ShaderProgram.createIdentity(context);
        }
        int xSliceIndex = this.mSliceIndex % this.mXSlices;
        int ySliceIndex = this.mSliceIndex / this.mXSlices;
        float x0 = ((this.mSliceWidth * xSliceIndex) - this.mPadSize) / this.mInputWidth;
        float y0 = ((this.mSliceHeight * ySliceIndex) - this.mPadSize) / this.mInputHeight;
        ((ShaderProgram) this.mProgram).setSourceRect(x0, y0, this.mOutputWidth / this.mInputWidth, this.mOutputHeight / this.mInputHeight);
        this.mProgram.process(this.mOriginalFrame, output);
        this.mSliceIndex++;
        if (this.mSliceIndex == this.mXSlices * this.mYSlices) {
            this.mSliceIndex = 0;
            this.mOriginalFrame.release();
            setWaitsOnInputPort(SliceItem.FORMAT_IMAGE, true);
        } else {
            this.mOriginalFrame.retain();
            setWaitsOnInputPort(SliceItem.FORMAT_IMAGE, false);
        }
        pushOutput(SliceItem.FORMAT_IMAGE, output);
        output.release();
    }
}

package android.renderscript;

import android.renderscript.Script;

public class ScriptIntrinsicBlend extends ScriptIntrinsic {
    ScriptIntrinsicBlend(long id, RenderScript rs) {
        super(id, rs);
    }

    public static ScriptIntrinsicBlend create(RenderScript rs, Element e) {
        return new ScriptIntrinsicBlend(rs.nScriptIntrinsicCreate(7, e.getID(rs)), rs);
    }

    private void blend(int id, Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        if (!ain.getElement().isCompatible(Element.U8_4(this.mRS))) {
            throw new RSIllegalArgumentException("Input is not of expected format.");
        } else if (aout.getElement().isCompatible(Element.U8_4(this.mRS))) {
            forEach(id, ain, aout, (FieldPacker) null, opt);
        } else {
            throw new RSIllegalArgumentException("Output is not of expected format.");
        }
    }

    public void forEachClear(Allocation ain, Allocation aout) {
        forEachClear(ain, aout, (Script.LaunchOptions) null);
    }

    public void forEachClear(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        blend(0, ain, aout, opt);
    }

    public Script.KernelID getKernelIDClear() {
        return createKernelID(0, 3, (Element) null, (Element) null);
    }

    public void forEachSrc(Allocation ain, Allocation aout) {
        forEachSrc(ain, aout, (Script.LaunchOptions) null);
    }

    public void forEachSrc(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        blend(1, ain, aout, (Script.LaunchOptions) null);
    }

    public Script.KernelID getKernelIDSrc() {
        return createKernelID(1, 3, (Element) null, (Element) null);
    }

    public void forEachDst(Allocation ain, Allocation aout) {
    }

    public void forEachDst(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
    }

    public Script.KernelID getKernelIDDst() {
        return createKernelID(2, 3, (Element) null, (Element) null);
    }

    public void forEachSrcOver(Allocation ain, Allocation aout) {
        forEachSrcOver(ain, aout, (Script.LaunchOptions) null);
    }

    public void forEachSrcOver(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        blend(3, ain, aout, opt);
    }

    public Script.KernelID getKernelIDSrcOver() {
        return createKernelID(3, 3, (Element) null, (Element) null);
    }

    public void forEachDstOver(Allocation ain, Allocation aout) {
        forEachDstOver(ain, aout, (Script.LaunchOptions) null);
    }

    public void forEachDstOver(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        blend(4, ain, aout, opt);
    }

    public Script.KernelID getKernelIDDstOver() {
        return createKernelID(4, 3, (Element) null, (Element) null);
    }

    public void forEachSrcIn(Allocation ain, Allocation aout) {
        forEachSrcIn(ain, aout, (Script.LaunchOptions) null);
    }

    public void forEachSrcIn(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        blend(5, ain, aout, opt);
    }

    public Script.KernelID getKernelIDSrcIn() {
        return createKernelID(5, 3, (Element) null, (Element) null);
    }

    public void forEachDstIn(Allocation ain, Allocation aout) {
        forEachDstIn(ain, aout, (Script.LaunchOptions) null);
    }

    public void forEachDstIn(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        blend(6, ain, aout, opt);
    }

    public Script.KernelID getKernelIDDstIn() {
        return createKernelID(6, 3, (Element) null, (Element) null);
    }

    public void forEachSrcOut(Allocation ain, Allocation aout) {
        forEachSrcOut(ain, aout, (Script.LaunchOptions) null);
    }

    public void forEachSrcOut(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        blend(7, ain, aout, opt);
    }

    public Script.KernelID getKernelIDSrcOut() {
        return createKernelID(7, 3, (Element) null, (Element) null);
    }

    public void forEachDstOut(Allocation ain, Allocation aout) {
        forEachDstOut(ain, aout, (Script.LaunchOptions) null);
    }

    public void forEachDstOut(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        blend(8, ain, aout, opt);
    }

    public Script.KernelID getKernelIDDstOut() {
        return createKernelID(8, 3, (Element) null, (Element) null);
    }

    public void forEachSrcAtop(Allocation ain, Allocation aout) {
        forEachSrcAtop(ain, aout, (Script.LaunchOptions) null);
    }

    public void forEachSrcAtop(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        blend(9, ain, aout, opt);
    }

    public Script.KernelID getKernelIDSrcAtop() {
        return createKernelID(9, 3, (Element) null, (Element) null);
    }

    public void forEachDstAtop(Allocation ain, Allocation aout) {
        forEachDstAtop(ain, aout, (Script.LaunchOptions) null);
    }

    public void forEachDstAtop(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        blend(10, ain, aout, opt);
    }

    public Script.KernelID getKernelIDDstAtop() {
        return createKernelID(10, 3, (Element) null, (Element) null);
    }

    public void forEachXor(Allocation ain, Allocation aout) {
        forEachXor(ain, aout, (Script.LaunchOptions) null);
    }

    public void forEachXor(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        blend(11, ain, aout, opt);
    }

    public Script.KernelID getKernelIDXor() {
        return createKernelID(11, 3, (Element) null, (Element) null);
    }

    public void forEachMultiply(Allocation ain, Allocation aout) {
        forEachMultiply(ain, aout, (Script.LaunchOptions) null);
    }

    public void forEachMultiply(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        blend(14, ain, aout, opt);
    }

    public Script.KernelID getKernelIDMultiply() {
        return createKernelID(14, 3, (Element) null, (Element) null);
    }

    public void forEachAdd(Allocation ain, Allocation aout) {
        forEachAdd(ain, aout, (Script.LaunchOptions) null);
    }

    public void forEachAdd(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        blend(34, ain, aout, opt);
    }

    public Script.KernelID getKernelIDAdd() {
        return createKernelID(34, 3, (Element) null, (Element) null);
    }

    public void forEachSubtract(Allocation ain, Allocation aout) {
        forEachSubtract(ain, aout, (Script.LaunchOptions) null);
    }

    public void forEachSubtract(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
        blend(35, ain, aout, opt);
    }

    public Script.KernelID getKernelIDSubtract() {
        return createKernelID(35, 3, (Element) null, (Element) null);
    }
}

package android.renderscript;

import android.renderscript.Script;
import android.telecom.Logging.Session;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ScriptGroup extends BaseObj {
    private static final String TAG = "ScriptGroup";
    private List<Closure> mClosures;
    IO[] mInputs;
    private List<Input> mInputs2;
    private String mName;
    IO[] mOutputs;
    private Future[] mOutputs2;

    static class IO {
        Allocation mAllocation;
        Script.KernelID mKID;

        IO(Script.KernelID s) {
            this.mKID = s;
        }
    }

    static class ConnectLine {
        Type mAllocationType;
        Script.KernelID mFrom;
        Script.FieldID mToF;
        Script.KernelID mToK;

        ConnectLine(Type t, Script.KernelID from, Script.KernelID to) {
            this.mFrom = from;
            this.mToK = to;
            this.mAllocationType = t;
        }

        ConnectLine(Type t, Script.KernelID from, Script.FieldID to) {
            this.mFrom = from;
            this.mToF = to;
            this.mAllocationType = t;
        }
    }

    static class Node {
        int dagNumber;
        ArrayList<ConnectLine> mInputs = new ArrayList<>();
        ArrayList<Script.KernelID> mKernels = new ArrayList<>();
        Node mNext;
        ArrayList<ConnectLine> mOutputs = new ArrayList<>();
        Script mScript;

        Node(Script s) {
            this.mScript = s;
        }
    }

    public static final class Closure extends BaseObj {
        private static final String TAG = "Closure";
        private Object[] mArgs;
        private Map<Script.FieldID, Object> mBindings;
        private FieldPacker mFP;
        private Map<Script.FieldID, Future> mGlobalFuture;
        private Future mReturnFuture;
        private Allocation mReturnValue;

        Closure(long id, RenderScript rs) {
            super(id, rs);
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        Closure(android.renderscript.RenderScript r28, android.renderscript.Script.KernelID r29, android.renderscript.Type r30, java.lang.Object[] r31, java.util.Map<android.renderscript.Script.FieldID, java.lang.Object> r32) {
            /*
                r27 = this;
                r9 = r27
                r15 = r28
                r13 = r31
                r10 = 0
                r9.<init>(r10, r15)
                r9.mArgs = r13
                r14 = r30
                android.renderscript.Allocation r0 = android.renderscript.Allocation.createTyped(r15, r14)
                r9.mReturnValue = r0
                r12 = r32
                r9.mBindings = r12
                java.util.HashMap r0 = new java.util.HashMap
                r0.<init>()
                r9.mGlobalFuture = r0
                int r0 = r13.length
                int r1 = r32.size()
                int r8 = r0 + r1
                long[] r7 = new long[r8]
                long[] r6 = new long[r8]
                int[] r5 = new int[r8]
                long[] r4 = new long[r8]
                long[] r3 = new long[r8]
                r0 = 0
            L_0x0032:
                r2 = r0
                int r0 = r13.length
                if (r2 >= r0) goto L_0x006b
                r7[r2] = r10
                r16 = 0
                r17 = r13[r2]
                r0 = r27
                r1 = r28
                r18 = r2
                r20 = r3
                r3 = r16
                r21 = r4
                r4 = r17
                r22 = r5
                r5 = r6
                r23 = r6
                r6 = r22
                r24 = r7
                r7 = r21
                r25 = r8
                r8 = r20
                r0.retrieveValueAndDependenceInfo(r1, r2, r3, r4, r5, r6, r7, r8)
                int r0 = r18 + 1
                r3 = r20
                r4 = r21
                r5 = r22
                r6 = r23
                r7 = r24
                r8 = r25
                goto L_0x0032
            L_0x006b:
                r18 = r2
                r20 = r3
                r21 = r4
                r22 = r5
                r23 = r6
                r24 = r7
                r25 = r8
                java.util.Set r0 = r32.entrySet()
                java.util.Iterator r10 = r0.iterator()
                r26 = r18
            L_0x0083:
                boolean r0 = r10.hasNext()
                if (r0 == 0) goto L_0x00ba
                java.lang.Object r0 = r10.next()
                r11 = r0
                java.util.Map$Entry r11 = (java.util.Map.Entry) r11
                java.lang.Object r16 = r11.getValue()
                java.lang.Object r0 = r11.getKey()
                r8 = r0
                android.renderscript.Script$FieldID r8 = (android.renderscript.Script.FieldID) r8
                long r0 = r8.getID(r15)
                r24[r26] = r0
                r0 = r27
                r1 = r28
                r2 = r26
                r3 = r8
                r4 = r16
                r5 = r23
                r6 = r22
                r7 = r21
                r17 = r8
                r8 = r20
                r0.retrieveValueAndDependenceInfo(r1, r2, r3, r4, r5, r6, r7, r8)
                int r26 = r26 + 1
                goto L_0x0083
            L_0x00ba:
                r0 = r29
                long r1 = r0.getID(r15)
                android.renderscript.Allocation r3 = r9.mReturnValue
                long r3 = r3.getID(r15)
                r10 = r28
                r11 = r1
                r13 = r3
                r15 = r24
                r16 = r23
                r17 = r22
                r18 = r21
                r19 = r20
                long r1 = r10.nClosureCreate(r11, r13, r15, r16, r17, r18, r19)
                r9.setID(r1)
                dalvik.system.CloseGuard r3 = r9.guard
                java.lang.String r4 = "destroy"
                r3.open(r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.renderscript.ScriptGroup.Closure.<init>(android.renderscript.RenderScript, android.renderscript.Script$KernelID, android.renderscript.Type, java.lang.Object[], java.util.Map):void");
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        Closure(android.renderscript.RenderScript r25, android.renderscript.Script.InvokeID r26, java.lang.Object[] r27, java.util.Map<android.renderscript.Script.FieldID, java.lang.Object> r28) {
            /*
                r24 = this;
                r9 = r24
                r10 = r25
                r0 = 0
                r9.<init>(r0, r10)
                android.renderscript.FieldPacker r0 = android.renderscript.FieldPacker.createFromArray(r27)
                r9.mFP = r0
                r11 = r27
                r9.mArgs = r11
                r12 = r28
                r9.mBindings = r12
                java.util.HashMap r0 = new java.util.HashMap
                r0.<init>()
                r9.mGlobalFuture = r0
                int r13 = r28.size()
                long[] r14 = new long[r13]
                long[] r15 = new long[r13]
                int[] r8 = new int[r13]
                long[] r7 = new long[r13]
                long[] r6 = new long[r13]
                r0 = 0
                java.util.Set r1 = r28.entrySet()
                java.util.Iterator r16 = r1.iterator()
                r17 = r0
            L_0x0037:
                boolean r0 = r16.hasNext()
                if (r0 == 0) goto L_0x0076
                java.lang.Object r0 = r16.next()
                r18 = r0
                java.util.Map$Entry r18 = (java.util.Map.Entry) r18
                java.lang.Object r19 = r18.getValue()
                java.lang.Object r0 = r18.getKey()
                r5 = r0
                android.renderscript.Script$FieldID r5 = (android.renderscript.Script.FieldID) r5
                long r0 = r5.getID(r10)
                r14[r17] = r0
                r0 = r24
                r1 = r25
                r2 = r17
                r3 = r5
                r4 = r19
                r20 = r5
                r5 = r15
                r21 = r6
                r6 = r8
                r22 = r7
                r23 = r8
                r8 = r21
                r0.retrieveValueAndDependenceInfo(r1, r2, r3, r4, r5, r6, r7, r8)
                int r17 = r17 + 1
                r6 = r21
                r8 = r23
                goto L_0x0037
            L_0x0076:
                r21 = r6
                r22 = r7
                r23 = r8
                r7 = r26
                long r1 = r7.getID(r10)
                android.renderscript.FieldPacker r0 = r9.mFP
                byte[] r3 = r0.getData()
                r0 = r25
                r4 = r14
                r5 = r15
                r6 = r23
                long r0 = r0.nInvokeClosureCreate(r1, r3, r4, r5, r6)
                r9.setID(r0)
                dalvik.system.CloseGuard r2 = r9.guard
                java.lang.String r3 = "destroy"
                r2.open(r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.renderscript.ScriptGroup.Closure.<init>(android.renderscript.RenderScript, android.renderscript.Script$InvokeID, java.lang.Object[], java.util.Map):void");
        }

        public void destroy() {
            super.destroy();
            if (this.mReturnValue != null) {
                this.mReturnValue.destroy();
            }
        }

        /* access modifiers changed from: protected */
        public void finalize() throws Throwable {
            this.mReturnValue = null;
            super.finalize();
        }

        private void retrieveValueAndDependenceInfo(RenderScript rs, int index, Script.FieldID fid, Object obj, long[] values, int[] sizes, long[] depClosures, long[] depFieldIDs) {
            if (obj instanceof Future) {
                Future f = (Future) obj;
                obj = f.getValue();
                depClosures[index] = f.getClosure().getID(rs);
                Script.FieldID fieldID = f.getFieldID();
                depFieldIDs[index] = fieldID != null ? fieldID.getID(rs) : 0;
            } else {
                depClosures[index] = 0;
                depFieldIDs[index] = 0;
            }
            if (obj instanceof Input) {
                Input unbound = (Input) obj;
                if (index < this.mArgs.length) {
                    unbound.addReference(this, index);
                } else {
                    unbound.addReference(this, fid);
                }
                values[index] = 0;
                sizes[index] = 0;
                return;
            }
            ValueAndSize vs = new ValueAndSize(rs, obj);
            values[index] = vs.value;
            sizes[index] = vs.size;
        }

        public Future getReturn() {
            if (this.mReturnFuture == null) {
                this.mReturnFuture = new Future(this, (Script.FieldID) null, this.mReturnValue);
            }
            return this.mReturnFuture;
        }

        public Future getGlobal(Script.FieldID field) {
            Future f = this.mGlobalFuture.get(field);
            if (f != null) {
                return f;
            }
            Object obj = this.mBindings.get(field);
            if (obj instanceof Future) {
                obj = ((Future) obj).getValue();
            }
            Future f2 = new Future(this, field, obj);
            this.mGlobalFuture.put(field, f2);
            return f2;
        }

        /* access modifiers changed from: package-private */
        public void setArg(int index, Object obj) {
            if (obj instanceof Future) {
                obj = ((Future) obj).getValue();
            }
            this.mArgs[index] = obj;
            ValueAndSize vs = new ValueAndSize(this.mRS, obj);
            this.mRS.nClosureSetArg(getID(this.mRS), index, vs.value, vs.size);
        }

        /* access modifiers changed from: package-private */
        public void setGlobal(Script.FieldID fieldID, Object obj) {
            if (obj instanceof Future) {
                obj = ((Future) obj).getValue();
            }
            this.mBindings.put(fieldID, obj);
            ValueAndSize vs = new ValueAndSize(this.mRS, obj);
            this.mRS.nClosureSetGlobal(getID(this.mRS), fieldID.getID(this.mRS), vs.value, vs.size);
        }

        private static final class ValueAndSize {
            public int size;
            public long value;

            public ValueAndSize(RenderScript rs, Object obj) {
                if (obj instanceof Allocation) {
                    this.value = ((Allocation) obj).getID(rs);
                    this.size = -1;
                } else if (obj instanceof Boolean) {
                    this.value = ((Boolean) obj).booleanValue() ? 1 : 0;
                    this.size = 4;
                } else if (obj instanceof Integer) {
                    this.value = ((Integer) obj).longValue();
                    this.size = 4;
                } else if (obj instanceof Long) {
                    this.value = ((Long) obj).longValue();
                    this.size = 8;
                } else if (obj instanceof Float) {
                    this.value = (long) Float.floatToRawIntBits(((Float) obj).floatValue());
                    this.size = 4;
                } else if (obj instanceof Double) {
                    this.value = Double.doubleToRawLongBits(((Double) obj).doubleValue());
                    this.size = 8;
                }
            }
        }
    }

    public static final class Future {
        Closure mClosure;
        Script.FieldID mFieldID;
        Object mValue;

        Future(Closure closure, Script.FieldID fieldID, Object value) {
            this.mClosure = closure;
            this.mFieldID = fieldID;
            this.mValue = value;
        }

        /* access modifiers changed from: package-private */
        public Closure getClosure() {
            return this.mClosure;
        }

        /* access modifiers changed from: package-private */
        public Script.FieldID getFieldID() {
            return this.mFieldID;
        }

        /* access modifiers changed from: package-private */
        public Object getValue() {
            return this.mValue;
        }
    }

    public static final class Input {
        List<Pair<Closure, Integer>> mArgIndex = new ArrayList();
        List<Pair<Closure, Script.FieldID>> mFieldID = new ArrayList();
        Object mValue;

        Input() {
        }

        /* access modifiers changed from: package-private */
        public void addReference(Closure closure, int index) {
            this.mArgIndex.add(Pair.create(closure, Integer.valueOf(index)));
        }

        /* access modifiers changed from: package-private */
        public void addReference(Closure closure, Script.FieldID fieldID) {
            this.mFieldID.add(Pair.create(closure, fieldID));
        }

        /* access modifiers changed from: package-private */
        public void set(Object value) {
            this.mValue = value;
            for (Pair<Closure, Integer> p : this.mArgIndex) {
                ((Closure) p.first).setArg(((Integer) p.second).intValue(), value);
            }
            for (Pair<Closure, Script.FieldID> p2 : this.mFieldID) {
                ((Closure) p2.first).setGlobal((Script.FieldID) p2.second, value);
            }
        }

        /* access modifiers changed from: package-private */
        public Object get() {
            return this.mValue;
        }
    }

    ScriptGroup(long id, RenderScript rs) {
        super(id, rs);
        this.guard.open("destroy");
    }

    ScriptGroup(RenderScript rs, String name, List<Closure> closures, List<Input> inputs, Future[] outputs) {
        super(0, rs);
        this.mName = name;
        this.mClosures = closures;
        this.mInputs2 = inputs;
        this.mOutputs2 = outputs;
        long[] closureIDs = new long[closures.size()];
        for (int i = 0; i < closureIDs.length; i++) {
            closureIDs[i] = closures.get(i).getID(rs);
        }
        setID(rs.nScriptGroup2Create(name, RenderScript.getCachePath(), closureIDs));
        this.guard.open("destroy");
    }

    public Object[] execute(Object... inputs) {
        if (inputs.length < this.mInputs2.size()) {
            Log.e(TAG, toString() + " receives " + inputs.length + " inputs, less than expected " + this.mInputs2.size());
            return null;
        }
        if (inputs.length > this.mInputs2.size()) {
            Log.i(TAG, toString() + " receives " + inputs.length + " inputs, more than expected " + this.mInputs2.size());
        }
        int i = 0;
        for (int i2 = 0; i2 < this.mInputs2.size(); i2++) {
            Object obj = inputs[i2];
            if ((obj instanceof Future) || (obj instanceof Input)) {
                Log.e(TAG, toString() + ": input " + i2 + " is a future or unbound value");
                return null;
            }
            this.mInputs2.get(i2).set(obj);
        }
        this.mRS.nScriptGroup2Execute(getID(this.mRS));
        Object[] outputObjs = new Object[this.mOutputs2.length];
        int i3 = 0;
        Future[] futureArr = this.mOutputs2;
        int length = futureArr.length;
        while (i < length) {
            Object output = futureArr[i].getValue();
            if (output instanceof Input) {
                output = ((Input) output).get();
            }
            outputObjs[i3] = output;
            i++;
            i3++;
        }
        return outputObjs;
    }

    public void setInput(Script.KernelID s, Allocation a) {
        for (int ct = 0; ct < this.mInputs.length; ct++) {
            if (this.mInputs[ct].mKID == s) {
                this.mInputs[ct].mAllocation = a;
                this.mRS.nScriptGroupSetInput(getID(this.mRS), s.getID(this.mRS), this.mRS.safeID(a));
                return;
            }
        }
        throw new RSIllegalArgumentException("Script not found");
    }

    public void setOutput(Script.KernelID s, Allocation a) {
        for (int ct = 0; ct < this.mOutputs.length; ct++) {
            if (this.mOutputs[ct].mKID == s) {
                this.mOutputs[ct].mAllocation = a;
                this.mRS.nScriptGroupSetOutput(getID(this.mRS), s.getID(this.mRS), this.mRS.safeID(a));
                return;
            }
        }
        throw new RSIllegalArgumentException("Script not found");
    }

    public void execute() {
        this.mRS.nScriptGroupExecute(getID(this.mRS));
    }

    public static final class Builder {
        private int mKernelCount;
        private ArrayList<ConnectLine> mLines = new ArrayList<>();
        private ArrayList<Node> mNodes = new ArrayList<>();
        private RenderScript mRS;

        public Builder(RenderScript rs) {
            this.mRS = rs;
        }

        private void validateCycle(Node target, Node original) {
            for (int ct = 0; ct < target.mOutputs.size(); ct++) {
                ConnectLine cl = target.mOutputs.get(ct);
                if (cl.mToK != null) {
                    Node tn = findNode(cl.mToK.mScript);
                    if (!tn.equals(original)) {
                        validateCycle(tn, original);
                    } else {
                        throw new RSInvalidStateException("Loops in group not allowed.");
                    }
                }
                if (cl.mToF != null) {
                    Node tn2 = findNode(cl.mToF.mScript);
                    if (!tn2.equals(original)) {
                        validateCycle(tn2, original);
                    } else {
                        throw new RSInvalidStateException("Loops in group not allowed.");
                    }
                }
            }
        }

        private void mergeDAGs(int valueUsed, int valueKilled) {
            for (int ct = 0; ct < this.mNodes.size(); ct++) {
                if (this.mNodes.get(ct).dagNumber == valueKilled) {
                    this.mNodes.get(ct).dagNumber = valueUsed;
                }
            }
        }

        private void validateDAGRecurse(Node n, int dagNumber) {
            if (n.dagNumber == 0 || n.dagNumber == dagNumber) {
                n.dagNumber = dagNumber;
                for (int ct = 0; ct < n.mOutputs.size(); ct++) {
                    ConnectLine cl = n.mOutputs.get(ct);
                    if (cl.mToK != null) {
                        validateDAGRecurse(findNode(cl.mToK.mScript), dagNumber);
                    }
                    if (cl.mToF != null) {
                        validateDAGRecurse(findNode(cl.mToF.mScript), dagNumber);
                    }
                }
                return;
            }
            mergeDAGs(n.dagNumber, dagNumber);
        }

        private void validateDAG() {
            int ct = 0;
            for (int ct2 = 0; ct2 < this.mNodes.size(); ct2++) {
                Node n = this.mNodes.get(ct2);
                if (n.mInputs.size() == 0) {
                    if (n.mOutputs.size() != 0 || this.mNodes.size() <= 1) {
                        validateDAGRecurse(n, ct2 + 1);
                    } else {
                        throw new RSInvalidStateException("Groups cannot contain unconnected scripts");
                    }
                }
            }
            int dagNumber = this.mNodes.get(0).dagNumber;
            while (ct < this.mNodes.size()) {
                if (this.mNodes.get(ct).dagNumber == dagNumber) {
                    ct++;
                } else {
                    throw new RSInvalidStateException("Multiple DAGs in group not allowed.");
                }
            }
        }

        private Node findNode(Script s) {
            for (int ct = 0; ct < this.mNodes.size(); ct++) {
                if (s == this.mNodes.get(ct).mScript) {
                    return this.mNodes.get(ct);
                }
            }
            return null;
        }

        private Node findNode(Script.KernelID k) {
            for (int ct = 0; ct < this.mNodes.size(); ct++) {
                Node n = this.mNodes.get(ct);
                for (int ct2 = 0; ct2 < n.mKernels.size(); ct2++) {
                    if (k == n.mKernels.get(ct2)) {
                        return n;
                    }
                }
            }
            return null;
        }

        public Builder addKernel(Script.KernelID k) {
            if (this.mLines.size() != 0) {
                throw new RSInvalidStateException("Kernels may not be added once connections exist.");
            } else if (findNode(k) != null) {
                return this;
            } else {
                this.mKernelCount++;
                Node n = findNode(k.mScript);
                if (n == null) {
                    n = new Node(k.mScript);
                    this.mNodes.add(n);
                }
                n.mKernels.add(k);
                return this;
            }
        }

        public Builder addConnection(Type t, Script.KernelID from, Script.FieldID to) {
            Node nf = findNode(from);
            if (nf != null) {
                Node nt = findNode(to.mScript);
                if (nt != null) {
                    ConnectLine cl = new ConnectLine(t, from, to);
                    this.mLines.add(new ConnectLine(t, from, to));
                    nf.mOutputs.add(cl);
                    nt.mInputs.add(cl);
                    validateCycle(nf, nf);
                    return this;
                }
                throw new RSInvalidStateException("To script not found.");
            }
            throw new RSInvalidStateException("From script not found.");
        }

        public Builder addConnection(Type t, Script.KernelID from, Script.KernelID to) {
            Node nf = findNode(from);
            if (nf != null) {
                Node nt = findNode(to);
                if (nt != null) {
                    ConnectLine cl = new ConnectLine(t, from, to);
                    this.mLines.add(new ConnectLine(t, from, to));
                    nf.mOutputs.add(cl);
                    nt.mInputs.add(cl);
                    validateCycle(nf, nf);
                    return this;
                }
                throw new RSInvalidStateException("To script not found.");
            }
            throw new RSInvalidStateException("From script not found.");
        }

        public ScriptGroup create() {
            if (this.mNodes.size() != 0) {
                for (int ct = 0; ct < this.mNodes.size(); ct++) {
                    this.mNodes.get(ct).dagNumber = 0;
                }
                validateDAG();
                ArrayList<IO> inputs = new ArrayList<>();
                ArrayList<IO> outputs = new ArrayList<>();
                long[] kernels = new long[this.mKernelCount];
                int idx = 0;
                for (int ct2 = 0; ct2 < this.mNodes.size(); ct2++) {
                    Node n = this.mNodes.get(ct2);
                    int ct22 = 0;
                    while (ct22 < n.mKernels.size()) {
                        Script.KernelID kid = n.mKernels.get(ct22);
                        int idx2 = idx + 1;
                        kernels[idx] = kid.getID(this.mRS);
                        boolean hasOutput = false;
                        boolean hasInput = false;
                        for (int ct3 = 0; ct3 < n.mInputs.size(); ct3++) {
                            if (n.mInputs.get(ct3).mToK == kid) {
                                hasInput = true;
                            }
                        }
                        for (int ct32 = 0; ct32 < n.mOutputs.size(); ct32++) {
                            if (n.mOutputs.get(ct32).mFrom == kid) {
                                hasOutput = true;
                            }
                        }
                        if (!hasInput) {
                            inputs.add(new IO(kid));
                        }
                        if (!hasOutput) {
                            outputs.add(new IO(kid));
                        }
                        ct22++;
                        idx = idx2;
                    }
                }
                if (idx == this.mKernelCount) {
                    long[] src = new long[this.mLines.size()];
                    long[] dstk = new long[this.mLines.size()];
                    long[] dstf = new long[this.mLines.size()];
                    long[] types = new long[this.mLines.size()];
                    for (int ct4 = 0; ct4 < this.mLines.size(); ct4++) {
                        ConnectLine cl = this.mLines.get(ct4);
                        src[ct4] = cl.mFrom.getID(this.mRS);
                        if (cl.mToK != null) {
                            dstk[ct4] = cl.mToK.getID(this.mRS);
                        }
                        if (cl.mToF != null) {
                            dstf[ct4] = cl.mToF.getID(this.mRS);
                        }
                        types[ct4] = cl.mAllocationType.getID(this.mRS);
                    }
                    long id = this.mRS.nScriptGroupCreate(kernels, src, dstk, dstf, types);
                    if (id != 0) {
                        ScriptGroup sg = new ScriptGroup(id, this.mRS);
                        sg.mOutputs = new IO[outputs.size()];
                        for (int ct5 = 0; ct5 < outputs.size(); ct5++) {
                            sg.mOutputs[ct5] = outputs.get(ct5);
                        }
                        sg.mInputs = new IO[inputs.size()];
                        for (int ct6 = 0; ct6 < inputs.size(); ct6++) {
                            sg.mInputs[ct6] = inputs.get(ct6);
                        }
                        return sg;
                    }
                    throw new RSRuntimeException("Object creation error, should not happen.");
                }
                throw new RSRuntimeException("Count mismatch, should not happen.");
            }
            throw new RSInvalidStateException("Empty script groups are not allowed");
        }
    }

    public static final class Binding {
        private final Script.FieldID mField;
        private final Object mValue;

        public Binding(Script.FieldID field, Object value) {
            this.mField = field;
            this.mValue = value;
        }

        /* access modifiers changed from: package-private */
        public Script.FieldID getField() {
            return this.mField;
        }

        /* access modifiers changed from: package-private */
        public Object getValue() {
            return this.mValue;
        }
    }

    public static final class Builder2 {
        private static final String TAG = "ScriptGroup.Builder2";
        List<Closure> mClosures = new ArrayList();
        List<Input> mInputs = new ArrayList();
        RenderScript mRS;

        public Builder2(RenderScript rs) {
            this.mRS = rs;
        }

        private Closure addKernelInternal(Script.KernelID k, Type returnType, Object[] args, Map<Script.FieldID, Object> globalBindings) {
            Closure c = new Closure(this.mRS, k, returnType, args, globalBindings);
            this.mClosures.add(c);
            return c;
        }

        private Closure addInvokeInternal(Script.InvokeID invoke, Object[] args, Map<Script.FieldID, Object> globalBindings) {
            Closure c = new Closure(this.mRS, invoke, args, globalBindings);
            this.mClosures.add(c);
            return c;
        }

        public Input addInput() {
            Input unbound = new Input();
            this.mInputs.add(unbound);
            return unbound;
        }

        public Closure addKernel(Script.KernelID k, Type returnType, Object... argsAndBindings) {
            ArrayList<Object> args = new ArrayList<>();
            Map<Script.FieldID, Object> bindingMap = new HashMap<>();
            if (!seperateArgsAndBindings(argsAndBindings, args, bindingMap)) {
                return null;
            }
            return addKernelInternal(k, returnType, args.toArray(), bindingMap);
        }

        public Closure addInvoke(Script.InvokeID invoke, Object... argsAndBindings) {
            ArrayList<Object> args = new ArrayList<>();
            Map<Script.FieldID, Object> bindingMap = new HashMap<>();
            if (!seperateArgsAndBindings(argsAndBindings, args, bindingMap)) {
                return null;
            }
            return addInvokeInternal(invoke, args.toArray(), bindingMap);
        }

        public ScriptGroup create(String name, Future... outputs) {
            if (name == null || name.isEmpty() || name.length() > 100 || !name.equals(name.replaceAll("[^a-zA-Z0-9-]", Session.SESSION_SEPARATION_CHAR_CHILD))) {
                throw new RSIllegalArgumentException("invalid script group name");
            }
            ScriptGroup scriptGroup = new ScriptGroup(this.mRS, name, this.mClosures, this.mInputs, outputs);
            this.mClosures = new ArrayList();
            this.mInputs = new ArrayList();
            return scriptGroup;
        }

        private boolean seperateArgsAndBindings(Object[] argsAndBindings, ArrayList<Object> args, Map<Script.FieldID, Object> bindingMap) {
            int i = 0;
            while (i < argsAndBindings.length && !(argsAndBindings[i] instanceof Binding)) {
                args.add(argsAndBindings[i]);
                i++;
            }
            while (i < argsAndBindings.length) {
                if (!(argsAndBindings[i] instanceof Binding)) {
                    return false;
                }
                Binding b = argsAndBindings[i];
                bindingMap.put(b.getField(), b.getValue());
                i++;
            }
            return true;
        }
    }

    public void destroy() {
        super.destroy();
        if (this.mClosures != null) {
            for (Closure c : this.mClosures) {
                c.destroy();
            }
        }
    }
}

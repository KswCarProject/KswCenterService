package android.filterfw.io;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterFactory;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.KeyValueMap;
import android.filterfw.core.ProtocolException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class TextGraphReader extends GraphReader {
    private KeyValueMap mBoundReferences;
    private ArrayList<Command> mCommands = new ArrayList<>();
    /* access modifiers changed from: private */
    public Filter mCurrentFilter;
    /* access modifiers changed from: private */
    public FilterGraph mCurrentGraph;
    /* access modifiers changed from: private */
    public FilterFactory mFactory;
    private KeyValueMap mSettings;

    private interface Command {
        void execute(TextGraphReader textGraphReader) throws GraphIOException;
    }

    private class ImportPackageCommand implements Command {
        private String mPackageName;

        public ImportPackageCommand(String packageName) {
            this.mPackageName = packageName;
        }

        public void execute(TextGraphReader reader) throws GraphIOException {
            try {
                reader.mFactory.addPackage(this.mPackageName);
            } catch (IllegalArgumentException e) {
                throw new GraphIOException(e.getMessage());
            }
        }
    }

    private class AddLibraryCommand implements Command {
        private String mLibraryName;

        public AddLibraryCommand(String libraryName) {
            this.mLibraryName = libraryName;
        }

        public void execute(TextGraphReader reader) {
            FilterFactory unused = reader.mFactory;
            FilterFactory.addFilterLibrary(this.mLibraryName);
        }
    }

    private class AllocateFilterCommand implements Command {
        private String mClassName;
        private String mFilterName;

        public AllocateFilterCommand(String className, String filterName) {
            this.mClassName = className;
            this.mFilterName = filterName;
        }

        public void execute(TextGraphReader reader) throws GraphIOException {
            try {
                Filter unused = reader.mCurrentFilter = reader.mFactory.createFilterByClassName(this.mClassName, this.mFilterName);
            } catch (IllegalArgumentException e) {
                throw new GraphIOException(e.getMessage());
            }
        }
    }

    private class InitFilterCommand implements Command {
        private KeyValueMap mParams;

        public InitFilterCommand(KeyValueMap params) {
            this.mParams = params;
        }

        public void execute(TextGraphReader reader) throws GraphIOException {
            try {
                reader.mCurrentFilter.initWithValueMap(this.mParams);
                reader.mCurrentGraph.addFilter(TextGraphReader.this.mCurrentFilter);
            } catch (ProtocolException e) {
                throw new GraphIOException(e.getMessage());
            }
        }
    }

    private class ConnectCommand implements Command {
        private String mSourceFilter;
        private String mSourcePort;
        private String mTargetFilter;
        private String mTargetName;

        public ConnectCommand(String sourceFilter, String sourcePort, String targetFilter, String targetName) {
            this.mSourceFilter = sourceFilter;
            this.mSourcePort = sourcePort;
            this.mTargetFilter = targetFilter;
            this.mTargetName = targetName;
        }

        public void execute(TextGraphReader reader) {
            reader.mCurrentGraph.connect(this.mSourceFilter, this.mSourcePort, this.mTargetFilter, this.mTargetName);
        }
    }

    public FilterGraph readGraphString(String graphString) throws GraphIOException {
        FilterGraph result = new FilterGraph();
        reset();
        this.mCurrentGraph = result;
        parseString(graphString);
        applySettings();
        executeCommands();
        reset();
        return result;
    }

    private void reset() {
        this.mCurrentGraph = null;
        this.mCurrentFilter = null;
        this.mCommands.clear();
        this.mBoundReferences = new KeyValueMap();
        this.mSettings = new KeyValueMap();
        this.mFactory = new FilterFactory();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x00d2, code lost:
        r0 = r1;
        r2 = r7;
        r41 = r10;
        r1 = r11;
        r42 = r15;
        r35 = r37;
        r10 = r38;
        r7 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x00de, code lost:
        r11 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0224, code lost:
        r0 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0225, code lost:
        r35 = r37;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0227, code lost:
        r2 = r44;
        r1 = r45;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parseString(java.lang.String r47) throws android.filterfw.io.GraphIOException {
        /*
            r46 = this;
            r6 = r46
            java.lang.String r0 = "@[a-zA-Z]+"
            java.util.regex.Pattern r7 = java.util.regex.Pattern.compile(r0)
            java.lang.String r0 = "\\}"
            java.util.regex.Pattern r8 = java.util.regex.Pattern.compile(r0)
            java.lang.String r0 = "\\{"
            java.util.regex.Pattern r9 = java.util.regex.Pattern.compile(r0)
            java.lang.String r0 = "(\\s+|//[^\\n]*\\n)+"
            java.util.regex.Pattern r10 = java.util.regex.Pattern.compile(r0)
            java.lang.String r0 = "[a-zA-Z\\.]+"
            java.util.regex.Pattern r11 = java.util.regex.Pattern.compile(r0)
            java.lang.String r0 = "[a-zA-Z\\./:]+"
            java.util.regex.Pattern r12 = java.util.regex.Pattern.compile(r0)
            java.lang.String r0 = "\\[[a-zA-Z0-9\\-_]+\\]"
            java.util.regex.Pattern r13 = java.util.regex.Pattern.compile(r0)
            java.lang.String r0 = "=>"
            java.util.regex.Pattern r14 = java.util.regex.Pattern.compile(r0)
            java.lang.String r0 = ";"
            java.util.regex.Pattern r15 = java.util.regex.Pattern.compile(r0)
            java.lang.String r0 = "[a-zA-Z0-9\\-_]+"
            java.util.regex.Pattern r5 = java.util.regex.Pattern.compile(r0)
            r16 = 0
            r17 = 1
            r18 = 2
            r19 = 3
            r20 = 4
            r21 = 5
            r22 = 6
            r23 = 7
            r24 = 8
            r25 = 9
            r26 = 10
            r27 = 11
            r28 = 12
            r29 = 13
            r30 = 14
            r31 = 15
            r32 = 16
            r0 = 0
            android.filterfw.io.PatternScanner r1 = new android.filterfw.io.PatternScanner
            r4 = r47
            r1.<init>(r4, r10)
            r3 = r1
            r1 = 0
            r2 = 0
            r33 = 0
            r34 = 0
            r35 = 0
            r36 = r34
            r34 = r33
            r33 = r2
        L_0x0077:
            r2 = r0
            r0 = r35
            boolean r35 = r3.atEnd()
            if (r35 != 0) goto L_0x02e1
            r37 = r0
            r0 = 1
            switch(r2) {
                case 0: goto L_0x0256;
                case 1: goto L_0x022d;
                case 2: goto L_0x0204;
                case 3: goto L_0x01ed;
                case 4: goto L_0x01ce;
                case 5: goto L_0x01b8;
                case 6: goto L_0x019a;
                case 7: goto L_0x0184;
                case 8: goto L_0x016d;
                case 9: goto L_0x014d;
                case 10: goto L_0x0137;
                case 11: goto L_0x0120;
                case 12: goto L_0x00e1;
                case 13: goto L_0x00c4;
                case 14: goto L_0x00b6;
                case 15: goto L_0x00a8;
                case 16: goto L_0x0097;
                default: goto L_0x0086;
            }
        L_0x0086:
            r43 = r2
            r2 = r7
            r41 = r10
            r42 = r15
            r10 = r1
            r7 = r3
            r1 = r11
            r11 = r5
            r35 = r37
            r0 = r43
            goto L_0x02d3
        L_0x0097:
            java.lang.String r0 = ";"
            r3.eat(r15, r0)
            r0 = 0
            r2 = r7
            r41 = r10
            r42 = r15
            r35 = r37
            r10 = r1
            r7 = r3
            r1 = r11
            goto L_0x00de
        L_0x00a8:
            android.filterfw.core.KeyValueMap r0 = r6.readKeyValueAssignments(r3, r15)
            r38 = r1
            android.filterfw.core.KeyValueMap r1 = r6.mSettings
            r1.putAll(r0)
            r1 = 16
            goto L_0x00d2
        L_0x00b6:
            r38 = r1
            java.lang.String r0 = "<external-identifier>"
            java.lang.String r0 = r3.eat(r5, r0)
            r6.bindExternal(r0)
            r1 = 16
            goto L_0x00d2
        L_0x00c4:
            r38 = r1
            android.filterfw.core.KeyValueMap r0 = r6.readKeyValueAssignments(r3, r15)
            android.filterfw.core.KeyValueMap r1 = r6.mBoundReferences
            r1.putAll(r0)
            r1 = 16
        L_0x00d2:
            r0 = r1
            r2 = r7
            r41 = r10
            r1 = r11
            r42 = r15
            r35 = r37
            r10 = r38
            r7 = r3
        L_0x00de:
            r11 = r5
            goto L_0x02d3
        L_0x00e1:
            r38 = r1
            java.lang.String r1 = "[<target-port-name>]"
            java.lang.String r1 = r3.eat(r13, r1)
            int r35 = r1.length()
            r39 = r2
            int r2 = r35 + -1
            java.lang.String r35 = r1.substring(r0, r2)
            java.util.ArrayList<android.filterfw.io.TextGraphReader$Command> r2 = r6.mCommands
            android.filterfw.io.TextGraphReader$ConnectCommand r0 = new android.filterfw.io.TextGraphReader$ConnectCommand
            r40 = r0
            r37 = r1
            r41 = r10
            r10 = r38
            r1 = r46
            r42 = r15
            r43 = r39
            r15 = r2
            r2 = r33
            r44 = r7
            r7 = r3
            r3 = r34
            r4 = r36
            r45 = r11
            r11 = r5
            r5 = r35
            r0.<init>(r2, r3, r4, r5)
            r15.add(r0)
            r0 = 16
            goto L_0x0227
        L_0x0120:
            r43 = r2
            r44 = r7
            r41 = r10
            r45 = r11
            r42 = r15
            r10 = r1
            r7 = r3
            r11 = r5
            java.lang.String r0 = "<target-filter-name>"
            java.lang.String r36 = r7.eat(r11, r0)
            r0 = 12
            goto L_0x0225
        L_0x0137:
            r43 = r2
            r44 = r7
            r41 = r10
            r45 = r11
            r42 = r15
            r10 = r1
            r7 = r3
            r11 = r5
            java.lang.String r0 = "=>"
            r7.eat(r14, r0)
            r0 = 11
            goto L_0x0225
        L_0x014d:
            r43 = r2
            r44 = r7
            r41 = r10
            r45 = r11
            r42 = r15
            r10 = r1
            r7 = r3
            r11 = r5
            java.lang.String r1 = "[<source-port-name>]"
            java.lang.String r1 = r7.eat(r13, r1)
            int r2 = r1.length()
            int r2 = r2 - r0
            java.lang.String r34 = r1.substring(r0, r2)
            r0 = 10
            goto L_0x0225
        L_0x016d:
            r43 = r2
            r44 = r7
            r41 = r10
            r45 = r11
            r42 = r15
            r10 = r1
            r7 = r3
            r11 = r5
            java.lang.String r0 = "<source-filter-name>"
            java.lang.String r33 = r7.eat(r11, r0)
            r0 = 9
            goto L_0x0225
        L_0x0184:
            r43 = r2
            r44 = r7
            r41 = r10
            r45 = r11
            r42 = r15
            r10 = r1
            r7 = r3
            r11 = r5
            java.lang.String r0 = "}"
            r7.eat(r8, r0)
            r0 = 0
            goto L_0x0225
        L_0x019a:
            r43 = r2
            r44 = r7
            r41 = r10
            r45 = r11
            r42 = r15
            r10 = r1
            r7 = r3
            r11 = r5
            android.filterfw.core.KeyValueMap r0 = r6.readKeyValueAssignments(r7, r8)
            java.util.ArrayList<android.filterfw.io.TextGraphReader$Command> r1 = r6.mCommands
            android.filterfw.io.TextGraphReader$InitFilterCommand r2 = new android.filterfw.io.TextGraphReader$InitFilterCommand
            r2.<init>(r0)
            r1.add(r2)
            r1 = 7
            goto L_0x0224
        L_0x01b8:
            r43 = r2
            r44 = r7
            r41 = r10
            r45 = r11
            r42 = r15
            r10 = r1
            r7 = r3
            r11 = r5
            java.lang.String r0 = "{"
            r7.eat(r9, r0)
            r0 = 6
            goto L_0x0225
        L_0x01ce:
            r43 = r2
            r44 = r7
            r41 = r10
            r45 = r11
            r42 = r15
            r10 = r1
            r7 = r3
            r11 = r5
            java.lang.String r0 = "<filter-name>"
            java.lang.String r0 = r7.eat(r11, r0)
            java.util.ArrayList<android.filterfw.io.TextGraphReader$Command> r1 = r6.mCommands
            android.filterfw.io.TextGraphReader$AllocateFilterCommand r2 = new android.filterfw.io.TextGraphReader$AllocateFilterCommand
            r2.<init>(r10, r0)
            r1.add(r2)
            r1 = 5
            goto L_0x0224
        L_0x01ed:
            r43 = r2
            r44 = r7
            r41 = r10
            r45 = r11
            r42 = r15
            r10 = r1
            r7 = r3
            r11 = r5
            java.lang.String r0 = "<class-name>"
            java.lang.String r0 = r7.eat(r11, r0)
            r1 = 4
            r10 = r0
            goto L_0x0224
        L_0x0204:
            r43 = r2
            r44 = r7
            r41 = r10
            r45 = r11
            r42 = r15
            r10 = r1
            r7 = r3
            r11 = r5
            java.lang.String r0 = "<library-name>"
            java.lang.String r0 = r7.eat(r12, r0)
            java.util.ArrayList<android.filterfw.io.TextGraphReader$Command> r1 = r6.mCommands
            android.filterfw.io.TextGraphReader$AddLibraryCommand r2 = new android.filterfw.io.TextGraphReader$AddLibraryCommand
            r2.<init>(r0)
            r1.add(r2)
            r1 = 16
        L_0x0224:
            r0 = r1
        L_0x0225:
            r35 = r37
        L_0x0227:
            r2 = r44
            r1 = r45
            goto L_0x02d3
        L_0x022d:
            r43 = r2
            r44 = r7
            r41 = r10
            r45 = r11
            r42 = r15
            r10 = r1
            r7 = r3
            r11 = r5
            java.lang.String r0 = "<package-name>"
            r1 = r45
            java.lang.String r0 = r7.eat(r1, r0)
            java.util.ArrayList<android.filterfw.io.TextGraphReader$Command> r2 = r6.mCommands
            android.filterfw.io.TextGraphReader$ImportPackageCommand r3 = new android.filterfw.io.TextGraphReader$ImportPackageCommand
            r3.<init>(r0)
            r2.add(r3)
            r2 = 16
            r0 = r2
            r35 = r37
            r2 = r44
            goto L_0x02d3
        L_0x0256:
            r43 = r2
            r44 = r7
            r41 = r10
            r42 = r15
            r10 = r1
            r7 = r3
            r1 = r11
            r11 = r5
            java.lang.String r0 = "<command>"
            r2 = r44
            java.lang.String r0 = r7.eat(r2, r0)
            java.lang.String r3 = "@import"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0277
            r3 = 1
        L_0x0273:
            r0 = r3
            r35 = r37
            goto L_0x02d3
        L_0x0277:
            java.lang.String r3 = "@library"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0281
            r3 = 2
            goto L_0x0273
        L_0x0281:
            java.lang.String r3 = "@filter"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x028b
            r3 = 3
            goto L_0x0273
        L_0x028b:
            java.lang.String r3 = "@connect"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0296
            r3 = 8
            goto L_0x0273
        L_0x0296:
            java.lang.String r3 = "@set"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x02a1
            r3 = 13
            goto L_0x0273
        L_0x02a1:
            java.lang.String r3 = "@external"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x02ac
            r3 = 14
            goto L_0x0273
        L_0x02ac:
            java.lang.String r3 = "@setting"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x02b7
            r3 = 15
            goto L_0x0273
        L_0x02b7:
            android.filterfw.io.GraphIOException r3 = new android.filterfw.io.GraphIOException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Unknown command '"
            r4.append(r5)
            r4.append(r0)
            java.lang.String r5 = "'!"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        L_0x02d3:
            r4 = r47
            r3 = r7
            r5 = r11
            r15 = r42
            r11 = r1
            r7 = r2
            r1 = r10
            r10 = r41
            goto L_0x0077
        L_0x02e1:
            r37 = r0
            r43 = r2
            r2 = r7
            r41 = r10
            r42 = r15
            r10 = r1
            r7 = r3
            r1 = r11
            r11 = r5
            r0 = 16
            r3 = r43
            if (r3 == r0) goto L_0x02ff
            if (r3 != 0) goto L_0x02f7
            goto L_0x02ff
        L_0x02f7:
            android.filterfw.io.GraphIOException r0 = new android.filterfw.io.GraphIOException
            java.lang.String r4 = "Unexpected end of input!"
            r0.<init>(r4)
            throw r0
        L_0x02ff:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.filterfw.io.TextGraphReader.parseString(java.lang.String):void");
    }

    public KeyValueMap readKeyValueAssignments(String assignments) throws GraphIOException {
        return readKeyValueAssignments(new PatternScanner(assignments, Pattern.compile("\\s+")), (Pattern) null);
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.filterfw.core.KeyValueMap readKeyValueAssignments(android.filterfw.io.PatternScanner r26, java.util.regex.Pattern r27) throws android.filterfw.io.GraphIOException {
        /*
            r25 = this;
            r0 = r25
            r1 = r26
            r2 = 0
            r3 = 1
            r4 = 2
            r5 = 3
            java.lang.String r6 = "="
            java.util.regex.Pattern r6 = java.util.regex.Pattern.compile(r6)
            java.lang.String r7 = ";"
            java.util.regex.Pattern r7 = java.util.regex.Pattern.compile(r7)
            java.lang.String r8 = "[a-zA-Z]+[a-zA-Z0-9]*"
            java.util.regex.Pattern r8 = java.util.regex.Pattern.compile(r8)
            java.lang.String r9 = "'[^']*'|\\\"[^\\\"]*\\\""
            java.util.regex.Pattern r9 = java.util.regex.Pattern.compile(r9)
            java.lang.String r10 = "[0-9]+"
            java.util.regex.Pattern r10 = java.util.regex.Pattern.compile(r10)
            java.lang.String r11 = "[0-9]*\\.[0-9]+f?"
            java.util.regex.Pattern r11 = java.util.regex.Pattern.compile(r11)
            java.lang.String r12 = "\\$[a-zA-Z]+[a-zA-Z0-9]"
            java.util.regex.Pattern r12 = java.util.regex.Pattern.compile(r12)
            java.lang.String r13 = "true|false"
            java.util.regex.Pattern r13 = java.util.regex.Pattern.compile(r13)
            r14 = 0
            android.filterfw.core.KeyValueMap r15 = new android.filterfw.core.KeyValueMap
            r15.<init>()
            r16 = 0
            r17 = 0
            r18 = r2
            r19 = r3
            r2 = r14
            r3 = r16
            r14 = r17
        L_0x004c:
            boolean r16 = r26.atEnd()
            if (r16 != 0) goto L_0x014a
            if (r27 == 0) goto L_0x0063
            boolean r20 = r26.peek(r27)
            if (r20 != 0) goto L_0x005b
            goto L_0x0063
        L_0x005b:
            r21 = r4
            r22 = r5
            r24 = r7
            goto L_0x0150
        L_0x0063:
            switch(r2) {
                case 0: goto L_0x0131;
                case 1: goto L_0x0122;
                case 2: goto L_0x007c;
                case 3: goto L_0x006e;
                default: goto L_0x0066;
            }
        L_0x0066:
            r21 = r4
            r22 = r5
            r24 = r7
            goto L_0x0140
        L_0x006e:
            r21 = r4
            java.lang.String r4 = ";"
            r1.eat(r7, r4)
            r2 = 0
            r22 = r5
            r24 = r7
            goto L_0x0140
        L_0x007c:
            r21 = r4
            java.lang.String r4 = r1.tryEat(r9)
            r14 = r4
            r22 = r5
            r5 = 1
            if (r4 == 0) goto L_0x0098
            int r4 = r14.length()
            int r4 = r4 - r5
            java.lang.String r4 = r14.substring(r5, r4)
            r15.put(r3, r4)
        L_0x0094:
            r24 = r7
            goto L_0x0114
        L_0x0098:
            java.lang.String r4 = r1.tryEat(r12)
            r14 = r4
            if (r4 == 0) goto L_0x00da
            int r4 = r14.length()
            java.lang.String r4 = r14.substring(r5, r4)
            android.filterfw.core.KeyValueMap r5 = r0.mBoundReferences
            if (r5 == 0) goto L_0x00b2
            android.filterfw.core.KeyValueMap r5 = r0.mBoundReferences
            java.lang.Object r5 = r5.get(r4)
            goto L_0x00b4
        L_0x00b2:
            r5 = r17
        L_0x00b4:
            if (r5 == 0) goto L_0x00ba
            r15.put(r3, r5)
            goto L_0x0094
        L_0x00ba:
            android.filterfw.io.GraphIOException r0 = new android.filterfw.io.GraphIOException
            r23 = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r24 = r7
            java.lang.String r7 = "Unknown object reference to '"
            r5.append(r7)
            r5.append(r4)
            java.lang.String r7 = "'!"
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            r0.<init>(r5)
            throw r0
        L_0x00da:
            r24 = r7
            java.lang.String r0 = r1.tryEat(r13)
            r14 = r0
            if (r0 == 0) goto L_0x00ef
            boolean r0 = java.lang.Boolean.parseBoolean(r14)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            r15.put(r3, r0)
            goto L_0x0114
        L_0x00ef:
            java.lang.String r0 = r1.tryEat(r11)
            r14 = r0
            if (r0 == 0) goto L_0x0102
            float r0 = java.lang.Float.parseFloat(r14)
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            r15.put(r3, r0)
            goto L_0x0114
        L_0x0102:
            java.lang.String r0 = r1.tryEat(r10)
            r14 = r0
            if (r0 == 0) goto L_0x0116
            int r0 = java.lang.Integer.parseInt(r14)
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r15.put(r3, r0)
        L_0x0114:
            r0 = 3
            goto L_0x012f
        L_0x0116:
            android.filterfw.io.GraphIOException r0 = new android.filterfw.io.GraphIOException
            java.lang.String r4 = "<value>"
            java.lang.String r4 = r1.unexpectedTokenMessage(r4)
            r0.<init>(r4)
            throw r0
        L_0x0122:
            r21 = r4
            r22 = r5
            r24 = r7
            java.lang.String r0 = "="
            r1.eat(r6, r0)
            r0 = 2
        L_0x012f:
            r2 = r0
            goto L_0x0140
        L_0x0131:
            r21 = r4
            r22 = r5
            r24 = r7
            java.lang.String r0 = "<identifier>"
            java.lang.String r0 = r1.eat(r8, r0)
            r2 = 1
            r3 = r0
        L_0x0140:
            r4 = r21
            r5 = r22
            r7 = r24
            r0 = r25
            goto L_0x004c
        L_0x014a:
            r21 = r4
            r22 = r5
            r24 = r7
        L_0x0150:
            if (r2 == 0) goto L_0x0176
            r0 = 3
            if (r2 != r0) goto L_0x0156
            goto L_0x0176
        L_0x0156:
            android.filterfw.io.GraphIOException r0 = new android.filterfw.io.GraphIOException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Unexpected end of assignments on line "
            r4.append(r5)
            int r5 = r26.lineNo()
            r4.append(r5)
            java.lang.String r5 = "!"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r0.<init>(r4)
            throw r0
        L_0x0176:
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: android.filterfw.io.TextGraphReader.readKeyValueAssignments(android.filterfw.io.PatternScanner, java.util.regex.Pattern):android.filterfw.core.KeyValueMap");
    }

    private void bindExternal(String name) throws GraphIOException {
        if (this.mReferences.containsKey(name)) {
            this.mBoundReferences.put(name, this.mReferences.get(name));
            return;
        }
        throw new GraphIOException("Unknown external variable '" + name + "'! You must add a reference to this external in the host program using addReference(...)!");
    }

    private void checkReferences() throws GraphIOException {
        for (String reference : this.mReferences.keySet()) {
            if (!this.mBoundReferences.containsKey(reference)) {
                throw new GraphIOException("Host program specifies reference to '" + reference + "', which is not declared @external in graph file!");
            }
        }
    }

    private void applySettings() throws GraphIOException {
        for (String setting : this.mSettings.keySet()) {
            Object value = this.mSettings.get(setting);
            if (setting.equals("autoBranch")) {
                expectSettingClass(setting, value, String.class);
                if (value.equals("synced")) {
                    this.mCurrentGraph.setAutoBranchMode(1);
                } else if (value.equals("unsynced")) {
                    this.mCurrentGraph.setAutoBranchMode(2);
                } else if (value.equals("off")) {
                    this.mCurrentGraph.setAutoBranchMode(0);
                } else {
                    throw new GraphIOException("Unknown autobranch setting: " + value + "!");
                }
            } else if (setting.equals("discardUnconnectedOutputs")) {
                expectSettingClass(setting, value, Boolean.class);
                this.mCurrentGraph.setDiscardUnconnectedOutputs(((Boolean) value).booleanValue());
            } else {
                throw new GraphIOException("Unknown @setting '" + setting + "'!");
            }
        }
    }

    private void expectSettingClass(String setting, Object value, Class expectedClass) throws GraphIOException {
        if (value.getClass() != expectedClass) {
            throw new GraphIOException("Setting '" + setting + "' must have a value of type " + expectedClass.getSimpleName() + ", but found a value of type " + value.getClass().getSimpleName() + "!");
        }
    }

    private void executeCommands() throws GraphIOException {
        Iterator<Command> it = this.mCommands.iterator();
        while (it.hasNext()) {
            it.next().execute(this);
        }
    }
}

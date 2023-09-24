package com.ibm.icu.text;

import com.ibm.icu.text.MessagePattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes5.dex */
public final class MessagePatternUtil {
    private MessagePatternUtil() {
    }

    public static MessageNode buildMessageNode(String patternString) {
        return buildMessageNode(new MessagePattern(patternString));
    }

    public static MessageNode buildMessageNode(MessagePattern pattern) {
        int limit = pattern.countParts() - 1;
        if (limit < 0) {
            throw new IllegalArgumentException("The MessagePattern is empty");
        }
        if (pattern.getPartType(0) != MessagePattern.Part.Type.MSG_START) {
            throw new IllegalArgumentException("The MessagePattern does not represent a MessageFormat pattern");
        }
        return buildMessageNode(pattern, 0, limit);
    }

    /* loaded from: classes5.dex */
    public static class Node {
        private Node() {
        }
    }

    /* loaded from: classes5.dex */
    public static class MessageNode extends Node {
        private volatile List<MessageContentsNode> list;

        public List<MessageContentsNode> getContents() {
            return this.list;
        }

        public String toString() {
            return this.list.toString();
        }

        private MessageNode() {
            super();
            this.list = new ArrayList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addContentsNode(MessageContentsNode node) {
            if ((node instanceof TextNode) && !this.list.isEmpty()) {
                MessageContentsNode lastNode = this.list.get(this.list.size() - 1);
                if (lastNode instanceof TextNode) {
                    TextNode textNode = (TextNode) lastNode;
                    textNode.text += ((TextNode) node).text;
                    return;
                }
            }
            this.list.add(node);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public MessageNode freeze() {
            this.list = Collections.unmodifiableList(this.list);
            return this;
        }
    }

    /* loaded from: classes5.dex */
    public static class MessageContentsNode extends Node {
        private Type type;

        /* loaded from: classes5.dex */
        public enum Type {
            TEXT,
            ARG,
            REPLACE_NUMBER
        }

        static /* synthetic */ MessageContentsNode access$600() {
            return createReplaceNumberNode();
        }

        public Type getType() {
            return this.type;
        }

        public String toString() {
            return "{REPLACE_NUMBER}";
        }

        private MessageContentsNode(Type type) {
            super();
            this.type = type;
        }

        private static MessageContentsNode createReplaceNumberNode() {
            return new MessageContentsNode(Type.REPLACE_NUMBER);
        }
    }

    /* loaded from: classes5.dex */
    public static class TextNode extends MessageContentsNode {
        private String text;

        public String getText() {
            return this.text;
        }

        @Override // com.ibm.icu.text.MessagePatternUtil.MessageContentsNode
        public String toString() {
            return "\u00ab" + this.text + "\u00bb";
        }

        private TextNode(String text) {
            super(MessageContentsNode.Type.TEXT);
            this.text = text;
        }
    }

    /* loaded from: classes5.dex */
    public static class ArgNode extends MessageContentsNode {
        private MessagePattern.ArgType argType;
        private ComplexArgStyleNode complexStyle;
        private String name;
        private int number;
        private String style;
        private String typeName;

        static /* synthetic */ ArgNode access$800() {
            return createArgNode();
        }

        public MessagePattern.ArgType getArgType() {
            return this.argType;
        }

        public String getName() {
            return this.name;
        }

        public int getNumber() {
            return this.number;
        }

        public String getTypeName() {
            return this.typeName;
        }

        public String getSimpleStyle() {
            return this.style;
        }

        public ComplexArgStyleNode getComplexStyle() {
            return this.complexStyle;
        }

        @Override // com.ibm.icu.text.MessagePatternUtil.MessageContentsNode
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('{');
            sb.append(this.name);
            if (this.argType != MessagePattern.ArgType.NONE) {
                sb.append(',');
                sb.append(this.typeName);
                if (this.argType == MessagePattern.ArgType.SIMPLE) {
                    if (this.style != null) {
                        sb.append(',');
                        sb.append(this.style);
                    }
                } else {
                    sb.append(',');
                    sb.append(this.complexStyle.toString());
                }
            }
            sb.append('}');
            return sb.toString();
        }

        private ArgNode() {
            super(MessageContentsNode.Type.ARG);
            this.number = -1;
        }

        private static ArgNode createArgNode() {
            return new ArgNode();
        }
    }

    /* loaded from: classes5.dex */
    public static class ComplexArgStyleNode extends Node {
        private MessagePattern.ArgType argType;
        private boolean explicitOffset;
        private volatile List<VariantNode> list;
        private double offset;

        public MessagePattern.ArgType getArgType() {
            return this.argType;
        }

        public boolean hasExplicitOffset() {
            return this.explicitOffset;
        }

        public double getOffset() {
            return this.offset;
        }

        public List<VariantNode> getVariants() {
            return this.list;
        }

        public VariantNode getVariantsByType(List<VariantNode> numericVariants, List<VariantNode> keywordVariants) {
            if (numericVariants != null) {
                numericVariants.clear();
            }
            keywordVariants.clear();
            VariantNode other = null;
            for (VariantNode variant : this.list) {
                if (variant.isSelectorNumeric()) {
                    numericVariants.add(variant);
                } else if ("other".equals(variant.getSelector())) {
                    if (other == null) {
                        other = variant;
                    }
                } else {
                    keywordVariants.add(variant);
                }
            }
            return other;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('(');
            sb.append(this.argType.toString());
            sb.append(" style) ");
            if (hasExplicitOffset()) {
                sb.append("offset:");
                sb.append(this.offset);
                sb.append(' ');
            }
            sb.append(this.list.toString());
            return sb.toString();
        }

        private ComplexArgStyleNode(MessagePattern.ArgType argType) {
            super();
            this.list = new ArrayList();
            this.argType = argType;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addVariant(VariantNode variant) {
            this.list.add(variant);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public ComplexArgStyleNode freeze() {
            this.list = Collections.unmodifiableList(this.list);
            return this;
        }
    }

    /* loaded from: classes5.dex */
    public static class VariantNode extends Node {
        private MessageNode msgNode;
        private double numericValue;
        private String selector;

        public String getSelector() {
            return this.selector;
        }

        public boolean isSelectorNumeric() {
            return this.numericValue != -1.23456789E8d;
        }

        public double getSelectorValue() {
            return this.numericValue;
        }

        public MessageNode getMessage() {
            return this.msgNode;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (isSelectorNumeric()) {
                sb.append(this.numericValue);
                sb.append(" (");
                sb.append(this.selector);
                sb.append(") {");
            } else {
                sb.append(this.selector);
                sb.append(" {");
            }
            sb.append(this.msgNode.toString());
            sb.append('}');
            return sb.toString();
        }

        private VariantNode() {
            super();
            this.numericValue = -1.23456789E8d;
        }
    }

    private static MessageNode buildMessageNode(MessagePattern pattern, int start, int limit) {
        int prevPatternIndex = pattern.getPart(start).getLimit();
        MessageNode node = new MessageNode();
        int i = start + 1;
        while (true) {
            MessagePattern.Part part = pattern.getPart(i);
            int patternIndex = part.getIndex();
            if (prevPatternIndex < patternIndex) {
                node.addContentsNode(new TextNode(pattern.getPatternString().substring(prevPatternIndex, patternIndex)));
            }
            if (i == limit) {
                return node.freeze();
            }
            MessagePattern.Part.Type partType = part.getType();
            if (partType == MessagePattern.Part.Type.ARG_START) {
                int argLimit = pattern.getLimitPartIndex(i);
                node.addContentsNode(buildArgNode(pattern, i, argLimit));
                i = argLimit;
                part = pattern.getPart(i);
            } else if (partType == MessagePattern.Part.Type.REPLACE_NUMBER) {
                node.addContentsNode(MessageContentsNode.access$600());
            }
            prevPatternIndex = part.getLimit();
            i++;
        }
    }

    private static ArgNode buildArgNode(MessagePattern pattern, int start, int limit) {
        ArgNode node = ArgNode.access$800();
        MessagePattern.ArgType argType = node.argType = pattern.getPart(start).getArgType();
        int start2 = start + 1;
        MessagePattern.Part part = pattern.getPart(start2);
        node.name = pattern.getSubstring(part);
        if (part.getType() == MessagePattern.Part.Type.ARG_NUMBER) {
            node.number = part.getValue();
        }
        int start3 = start2 + 1;
        switch (argType) {
            case SIMPLE:
                int start4 = start3 + 1;
                node.typeName = pattern.getSubstring(pattern.getPart(start3));
                if (start4 < limit) {
                    node.style = pattern.getSubstring(pattern.getPart(start4));
                }
                break;
            case CHOICE:
                node.typeName = "choice";
                node.complexStyle = buildChoiceStyleNode(pattern, start3, limit);
                break;
            case PLURAL:
                node.typeName = "plural";
                node.complexStyle = buildPluralStyleNode(pattern, start3, limit, argType);
                break;
            case SELECT:
                node.typeName = "select";
                node.complexStyle = buildSelectStyleNode(pattern, start3, limit);
                break;
            case SELECTORDINAL:
                node.typeName = "selectordinal";
                node.complexStyle = buildPluralStyleNode(pattern, start3, limit, argType);
                break;
        }
        return node;
    }

    private static ComplexArgStyleNode buildChoiceStyleNode(MessagePattern pattern, int start, int limit) {
        ComplexArgStyleNode node = new ComplexArgStyleNode(MessagePattern.ArgType.CHOICE);
        while (start < limit) {
            int valueIndex = start;
            MessagePattern.Part part = pattern.getPart(start);
            double value = pattern.getNumericValue(part);
            int start2 = start + 2;
            int msgLimit = pattern.getLimitPartIndex(start2);
            VariantNode variant = new VariantNode();
            variant.selector = pattern.getSubstring(pattern.getPart(valueIndex + 1));
            variant.numericValue = value;
            variant.msgNode = buildMessageNode(pattern, start2, msgLimit);
            node.addVariant(variant);
            start = msgLimit + 1;
        }
        return node.freeze();
    }

    private static ComplexArgStyleNode buildPluralStyleNode(MessagePattern pattern, int start, int limit, MessagePattern.ArgType argType) {
        ComplexArgStyleNode node = new ComplexArgStyleNode(argType);
        MessagePattern.Part offset = pattern.getPart(start);
        if (offset.getType().hasNumericValue()) {
            node.explicitOffset = true;
            node.offset = pattern.getNumericValue(offset);
            start++;
        }
        while (start < limit) {
            int start2 = start + 1;
            MessagePattern.Part selector = pattern.getPart(start);
            double value = -1.23456789E8d;
            MessagePattern.Part part = pattern.getPart(start2);
            if (part.getType().hasNumericValue()) {
                value = pattern.getNumericValue(part);
                start2++;
            }
            int msgLimit = pattern.getLimitPartIndex(start2);
            VariantNode variant = new VariantNode();
            variant.selector = pattern.getSubstring(selector);
            variant.numericValue = value;
            variant.msgNode = buildMessageNode(pattern, start2, msgLimit);
            node.addVariant(variant);
            start = msgLimit + 1;
        }
        return node.freeze();
    }

    private static ComplexArgStyleNode buildSelectStyleNode(MessagePattern pattern, int start, int limit) {
        ComplexArgStyleNode node = new ComplexArgStyleNode(MessagePattern.ArgType.SELECT);
        while (start < limit) {
            int start2 = start + 1;
            MessagePattern.Part selector = pattern.getPart(start);
            int msgLimit = pattern.getLimitPartIndex(start2);
            VariantNode variant = new VariantNode();
            variant.selector = pattern.getSubstring(selector);
            variant.msgNode = buildMessageNode(pattern, start2, msgLimit);
            node.addVariant(variant);
            start = msgLimit + 1;
        }
        return node.freeze();
    }
}

package com.tk.main.analyze;

import java.util.List;

/**
 * @author HDU_WS
 * @Classname SolValue
 * @Description TODO
 * @Date 2021/8/17 19:36
 * @Created by TheKing_Shun
 */

public class SolValue implements Comparable<SolValue> {
    public static final SolValue NULL = new SolValue();
    public static final SolValue VOID = new SolValue();
    private Object value;


    private SolValue() {
        value = new Object();
    }

    public SolValue(Object value) {
        if (value == null) {
            throw new RuntimeException("?");
        }
        this.value = value;
        if (!(isBoolean() || isList() || isString() || isNumber())) {
            throw new RuntimeException("??");
        }

    }

    public Double asDouble() {
        return (Double) value;
    }

    public boolean isNull() {
        return this == NULL;
    }

    public boolean isVOID() {
        return this == VOID;
    }

    public int getSize() {
        if (this.isList()) {
            return this.asLIst().size();
        } else if (this.isString()) {
            return this.asString().length();
        } else if (this.isNumber()) {
            return this.asString().length();
        } else {
            return -1;
        }


    }

    public String asString() {
        return (String) value;
    }

    public Integer asInt() {
        return (Integer) value;
    }

    public List<SolValue> asLIst() {
        return (List<SolValue>) value;
    }


    public boolean isNumber() {
        return value instanceof Number;
    }

    public boolean isString() {
        return value instanceof String;
    }

    public boolean isList() {
        return value instanceof List;
    }

    public boolean isBoolean() {
        return value instanceof Boolean;
    }


    @Override
    public int compareTo(SolValue o) {
        return 0;
    }

    @Override
    public String toString() {
        return "SolValue{" +
                "value=" + value +
                '}';
    }
}

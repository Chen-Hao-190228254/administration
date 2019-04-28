package com.skm.exa.mybatis;

import java.io.Serializable;

/**
 * @author dhc
 * 2019-03-07 15:12
 */
public class BetweenPair<T> implements Serializable {
    private T begin;
    private T end;

    public BetweenPair(T begin, T end) {
        this.begin = begin;
        this.end = end;
    }

    public T getBegin() {
        return begin;
    }

    public void setBegin(T begin) {
        this.begin = begin;
    }

    public T getEnd() {
        return end;
    }

    public void setEnd(T end) {
        this.end = end;
    }
}

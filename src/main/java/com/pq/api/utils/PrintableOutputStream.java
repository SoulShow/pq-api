package com.pq.api.utils;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 能够打印输出的内容的流
 *
 * @author liken
 * @date 15/3/14
 */
public class PrintableOutputStream extends OutputStream {

    /**
     * 结果最大长度
     */
    private final static int MAX_LENGTH = 750;
    private final static int UNLIMITED = -1;

    private final OutputStream delegateOutputStream;
    private int maxCount;

    private AutoArray array;

    private boolean dumpAll;//默认最大的长度

    public PrintableOutputStream(OutputStream os, boolean dumpAll) {
        this.delegateOutputStream = os;

        this.array = new AutoArray();

        this.dumpAll = dumpAll;
        if (dumpAll) {
            this.maxCount = UNLIMITED;
        } else {
            this.maxCount = MAX_LENGTH;
        }
    }

    /**
     * 默认是有限制的
     *
     * @param os
     */
    public PrintableOutputStream(OutputStream os) {
        this(os, true);
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    @Override
    public void write(int b) throws IOException {
        delegateOutputStream.write(b);

        //对打出的流量进行控制
        if (!dumpAll && this.maxCount <= array.getPos()) {
            return;
        }
        array.append(b);
    }

    /**
     * 获得当前使用了多少流量
     *
     * @return
     */
    public int getLength() {
        return this.array.getPos();
    }

    @Override
    public String toString() {
        if (array.isEmpty()) {
            return "";
        }

        return array.asString();
    }

}

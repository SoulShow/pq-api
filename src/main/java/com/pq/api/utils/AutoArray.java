package com.pq.api.utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 能够自动扩展的数组
 *
 * @author liken
 * @date 15/3/14
 */
public class AutoArray {

    /**
     * An array of bytes that was provided
     * by the creator of the stream. Elements <code>buf[0]</code>
     * through <code>buf[count-1]</code> are the
     * only bytes that can ever be read from the
     * stream;  element <code>buf[pos]</code> is
     * the next byte to be read.
     */
    private byte value[];

    /**
     * The index of the next character to read from the input stream buffer.
     * This value should always be nonnegative
     * and not larger than the value of <code>count</code>.
     * The next byte to be read from the input stream buffer
     * will be <code>buf[pos]</code>.
     */
    private int pos;

    /**
     * The index one greater than the last valid character in the input
     * stream buffer.
     * This value should always be nonnegative
     * and not larger than the length of <code>buf</code>.
     * It  is one greater than the position of
     * the last byte within <code>buf</code> that
     * can ever be read  from the input stream buffer.
     */
    private int count;

    /**
     * Creates a <code>ByteArrayInputStream</code>
     * so that it  uses <code>buf</code> as its
     * buffer array.
     * The buffer array is not copied.
     * The initial value of <code>pos</code>
     * is <code>0</code> and the initial value
     * of  <code>count</code> is the length of
     * <code>buf</code>.
     */
    public AutoArray() {
        this.value = new byte[16];
        this.pos = 0;
        this.count = value.length;
    }

    public AutoArray append(byte b) {
        int newPos = pos + 1;
        if (newPos == count) {
            expandCapacity(count + 1);
        }
        value[pos] = b;
        this.pos = newPos;
//        this.count = this.pos - 1;
        return this;
    }

    public AutoArray append(int b) {
        return append((byte) b);
    }

    void expandCapacity(int minimumCapacity) {
        int newCapacity = value.length * 2 + 2;
        if (newCapacity - minimumCapacity < 0)
            newCapacity = minimumCapacity;
        if (newCapacity < 0) {
            if (minimumCapacity < 0) // overflow
                throw new OutOfMemoryError();
            newCapacity = Integer.MAX_VALUE;
        }
        value = Arrays.copyOf(value, newCapacity);
        this.count = value.length;
    }

    public byte[] getValue() {
        return Arrays.copyOf(value, pos);
    }

    /**
     * 获取当前整个已经创建的数组的大小
     *
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * 获取有效数组的大小，真正的值
     *
     * @return
     */
    public int getPos() {
        return pos;
    }

    public boolean isEmpty() {
        return getPos() <= 0;
    }

    public String asString() {
        return asString("UTF-8");
    }

    public String asString(String encoding) {
        try {
            return new String(getValue(), encoding);
        } catch (UnsupportedEncodingException e) {
            return new String();
        }
    }
}


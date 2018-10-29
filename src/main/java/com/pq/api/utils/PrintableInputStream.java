package com.pq.api.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 可计算输入流读取数类,对于普通的字节流直接可以用本类来包装，获取当前读取的字节数
 * {@link #
 * getStreamCount()}
 *
 * @author liken
 * @date 15/3/14
 */
public class PrintableInputStream extends InputStream {

    /**
     * 每读取一个字节，记录一次, 当流处理完毕之后，再查询该Count的值，就是读取的总字节数
     */
    private InputStream delegateInputStream;

    private AutoArray array;

    private boolean removeNewLine;//是否去掉换行符

    public PrintableInputStream(InputStream in) {
        this(in, true);
    }

    public PrintableInputStream(InputStream in, boolean removeNewLine) {
        if (in == null) {
            throw new NullPointerException("the source inputstream should not be null.");
        }

        this.delegateInputStream = in;

        array = new AutoArray();

        this.removeNewLine = removeNewLine;
    }

    @Override
    public int read() throws IOException {
        int result = -1;

        result = delegateInputStream.read();
        if (result != -1) {//如果是一次有效的读取，则计数器 +1

            if (removeNewLine) {
                if (!isNewLine(result)) {
                    array.append(result);
                }
            } else {
                array.append(result);
            }
        }

        return result;
    }

    private boolean isNewLine(int result) {
        return result == '\n' || result == '\r';
    }

    /**
     * 获取流的大小
     *
     * @return
     */
    public int getLength() {
        return array.getPos();
    }

    @Override
    public String toString() {

        if (array.isEmpty()) {
            return "";
        }

        return array.asString();
    }

}

package cn.edu.nwpu.rj416.util.objects;

import com.sun.media.sound.SoftTuning;

import java.util.Arrays;

/**
 * @ClassName MLinkedBuffer
 * @Description TODO
 * @Author pigeonliu
 * @Date 2022/10/5 21:47
 */
public class MLinkedBuffer {

    private static final int BUFFER_SIZE = 512;

    private byte[] buffer;

    private int offset;

    private MLinkedBuffer next;

    public MLinkedBuffer() {
        buffer = new byte[BUFFER_SIZE];
        offset = 0;
        next = null;
    }

    public MLinkedBuffer(byte[] bytes) {
        this(bytes, 0);
    }

    public MLinkedBuffer(byte[] bytes, int startIndex) {
        this();

        int remain = bytes.length - startIndex;
        for (int i = 0; i < Math.min(BUFFER_SIZE, remain); i++) {
            buffer[offset++] = bytes[startIndex++];
        }
        // 超限
        if (startIndex < bytes.length) {
            next = new MLinkedBuffer(bytes, startIndex);
        }
    }

    /**
     * 将linkedbuffer 从头到尾转换为 byte[]
     *
     * @return
     */
    public byte[] getBytes() {
        int totalSize = 0;
        MLinkedBuffer cur = this;
        while (cur != null) {
            totalSize += cur.offset;
            cur = cur.next;
        }
        byte[] resBytes = new byte[totalSize];
        cur = this;
        int index = 0;
        while (cur != null) {
            System.arraycopy(cur.buffer, 0, resBytes, index, cur.offset);
            index += cur.offset;
            cur = cur.next;
        }

        return resBytes;
    }

    public int getSize() {
        int totalSize = 0;
        MLinkedBuffer cur = this;
        while (cur != null) {
            totalSize += cur.offset;
            cur = cur.next;
        }

        return totalSize;
    }

    public boolean remainSizeIsNotEnough(int size) {
        return BUFFER_SIZE - offset < size;
    }

    /**
     * 移动到最后的节点, 如果节点能容纳size位, 返回此节点
     * 否则 new 新节点并返回.
     *
     * @param size
     * @return
     */
    public MLinkedBuffer moveToLastAppendNode(int size) {
        MLinkedBuffer cur = this;
        while (cur.next != null) {
            cur = cur.next;
        }

        // 节点剩余空间不足, 开启新节点
        if (remainSizeIsNotEnough(size)) {
            cur.next = new MLinkedBuffer();
            cur = cur.next;
        }

        return cur;
    }

    /**
     * append内容进入 链表中
     * 如果最末尾的buffer存不下 , 直接开辟新的链表节点.
     * 其余append方法同理.
     *
     * @param b
     * @return 返回最后的结点引用
     */
    public MLinkedBuffer appendByte(byte b) {
        MLinkedBuffer lastAppendNode = moveToLastAppendNode(1);
        lastAppendNode.buffer[lastAppendNode.offset++] = b;

        return lastAppendNode;

    }

    public MLinkedBuffer appendChar(char ch) {
        MLinkedBuffer lastAppendNode = moveToLastAppendNode(2);

        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) ((ch >> 8) & 0xFF);
        //再放低八位
        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) (ch & 0xFF);

        return lastAppendNode;
    }

    public MLinkedBuffer appendShort(short s) {
        MLinkedBuffer lastAppendNode = moveToLastAppendNode(2);
        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) ((s >> 8) & 0xFF);
        //再放低八位
        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) (s & 0xFF);

        return lastAppendNode;
    }

    public MLinkedBuffer appendInt(int i) {
        MLinkedBuffer lastAppendNode = moveToLastAppendNode(4);

        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) ((i >> 24) & 0xFF);
        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) ((i >> 16) & 0xFF);
        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) ((i >> 8) & 0xFF);
        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) (i & 0xFF);

        return lastAppendNode;
    }

    public MLinkedBuffer appendLong(long l) {
        MLinkedBuffer lastAppendNode = moveToLastAppendNode(4);

        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) ((l >> 56) & 0xFF);
        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) ((l >> 48) & 0xFF);
        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) ((l >> 40) & 0xFF);
        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) ((l >> 32) & 0xFF);
        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) ((l >> 24) & 0xFF);
        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) ((l >> 16) & 0xFF);
        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) ((l >> 8) & 0xFF);
        lastAppendNode.buffer[lastAppendNode.offset++] = (byte) (l & 0xFF);

        return lastAppendNode;
    }

    public MLinkedBuffer appendFloat(float f) {
        int n = Float.floatToIntBits(f);
        return appendInt(n);
    }

    public MLinkedBuffer appendDouble(double d) {
        long n = Double.doubleToLongBits(d);
        return appendLong(n);
    }


    /**
     * 继续在最后的节点 append 字节
     * 避免浪费空间
     *
     * @param bytes
     * @return
     */
    public MLinkedBuffer appendBytes(byte[] bytes) {
        MLinkedBuffer lastAppendNode = moveToLastAppendNode(0);

        int index = 0;
        for (int i = 0; i < Math.min(bytes.length, BUFFER_SIZE - lastAppendNode.offset); i++) {
            lastAppendNode.buffer[lastAppendNode.offset++] = bytes[index++];
        }
        if (index < bytes.length) {
            lastAppendNode.next = new MLinkedBuffer(bytes, index);
            return lastAppendNode.next;
        }

        return lastAppendNode;
    }

    public MLinkedBuffer appendMVLInt(MVLInt n) {
        return appendBytes(n.getBytes());
    }

    public MLinkedBuffer appendMVLInt(long n) {
        return appendBytes(new MVLInt(n).getBytes());
    }

    public MLinkedBuffer appendMLinkedBuffer(MLinkedBuffer buffer) {
        MLinkedBuffer lastAppendNode = moveToLastAppendNode(0);
        lastAppendNode.next = buffer;

        return buffer.moveToLastAppendNode(0);
    }


//    public String getStr() {
//        StringBuilder sb = new StringBuilder();
//        MLinkedBuffer cur = this;
//        int index = 1;
//
//        while (cur != null) {
//            sb.append(index++)
//                    .append("\n")
//                    .append(cur.getOffset())
//                    .append("\n");
//            cur = cur.next;
//        }
//        return sb.toString();
//    }


    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public MLinkedBuffer getNext() {
        return next;
    }

    public void setNext(MLinkedBuffer next) {
        this.next = next;
    }


    public static void main(String[] args) {

        byte[] bytes = new byte[]{'1', '2', '3'};
        bytes = new byte[1000];
        MLinkedBuffer mLinkedBuffer = new MLinkedBuffer(bytes);
        mLinkedBuffer.next = new MLinkedBuffer(bytes);

//        System.out.println(Arrays.toString(mLinkedBuffer.getBytes()));
//        System.out.println(mLinkedBuffer.getBytes().length);

        MLinkedBuffer buffer = new MLinkedBuffer();
        buffer.appendBytes(new byte[510]);

        buffer.appendBytes(new byte[1000]);

        System.out.println(Arrays.toString(buffer.getBytes()));
        System.out.println(buffer.getBytes().length);


    }
}

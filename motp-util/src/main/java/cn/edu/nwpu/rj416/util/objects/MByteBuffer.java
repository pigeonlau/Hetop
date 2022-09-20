package cn.edu.nwpu.rj416.util.objects;

import cn.edu.nwpu.rj416.util.exception.runtime.MIndexOutOfBoundException;
import cn.edu.nwpu.rj416.util.exception.runtime.MInvalidValueException;

//字节缓存工具类
public class MByteBuffer { //32位系统，页表一页大小为4kB
    private static final int PAGE_SIZE = 4 << 10;//4的二进制补码左移十位后是4096（页大小）
    private byte[] buffer = null; //字节码缓存
    private int offset = 0;
    private int size = 0; //私有属性（字节数组buffer、偏移量offset、大小size）


    public MByteBuffer() { //无参构造函数
        this.buffer = new byte[PAGE_SIZE]; //设置自身buffer大小为4kB
    }

    public MByteBuffer(byte[] bytes) { //有参构造函数（根据传入的字节数组构造）
        int len = bytes.length;
        int length = PAGE_SIZE;
        while (length < len) {
            length *= 2;
        }
        buffer = new byte[length];
        size = length;
        System.arraycopy(bytes, 0, buffer, 0, len);
    }

    //在需要时扩展缓存区大小（加一页即4kB）
    private void extendIfNessary(int size) {
        int len = this.buffer.length; //获取自身缓存区大小
        int lenNeed = this.offset + size; //偏移量加上扩展大小为需要的缓存区大小

        if (len >= lenNeed) {
            return;
        } //本身的大小足够，无需扩展，返回

        //自身缓存大小不够，翻倍
        while (len < lenNeed) {
            len *= 2;
        }

        byte[] newBuffer = new byte[len];
        System.arraycopy(this.buffer, 0, newBuffer, 0, this.offset);
        this.buffer = newBuffer;
    }

    //确保偏移量不超过缓冲区自身大小
    private void assetOffset(int offset, int size) {
        if (offset + size > this.size) {
            throw new MIndexOutOfBoundException();
        }//传入的偏移量和size之和超过自身size，抛出索引越界异常
    }

    //更新大小
    private void updataSize() {
        if (this.offset > this.size) { //偏移量大于缓冲区大小
            this.size = this.offset;  //置缓冲区大小为偏移量值
        }
    }

    public int getOffset() {
        return this.offset;
    }

    public int getSize() {
        return this.size;
    }

    //设置偏移量为原数值加len（跳跃）
    public void skip(int len) {
        if (len < 0) {
            return;
        }
        this.assetOffset(offset, len);
        this.offset += len;
    }

    //设置偏移量为原数值减len（回退）
    public void back(int len) {
        if (len < 0) {
            return;
        }
        if (this.offset < len) {
            throw new MIndexOutOfBoundException();
        }
        this.offset -= len;
    }

    //设置缓冲区大小
    public int setSize(int size) {
        if (size <= this.buffer.length) {
            return this.buffer.length;
        }

        this.extendIfNessary(size - this.buffer.length); //大小不够进行扩展

        return this.buffer.length;
    }

    //获取字节码缓存
    public byte[] getBytes() {
        byte[] bytes = new byte[this.size];
        System.arraycopy(this.buffer, 0, bytes, 0, this.size);
        return bytes;
    }

    public byte[] getRawBuffer() {
        return buffer;
    }

    //在缓冲区中附加一个字节码
    public int appendByte(byte v) {
        this.extendIfNessary(1);
        this.buffer[offset++] = v;
        this.updataSize();
        return 1;
    }

    //在字节码缓冲区指定位置写入一个字节码
    public MByteBuffer writeByte(int offset, byte v) {
        this.assetOffset(offset, 1);
        this.buffer[offset++] = v;
        return this;
    }

    //读取偏移量处字节码
    public byte readByte() {
        this.assetOffset(offset, 1);
        return this.buffer[this.offset++];
    }

    //读取指定位置处字节码
    public byte readByte(int offset) {
        this.assetOffset(offset, 1);
        return this.buffer[offset];
    }

    //在缓冲区附加一个char型数据的字节码
    public int appendChar(char v) {
        this.extendIfNessary(2);
        this.buffer[offset++] = (byte) ((v >> 8) & 0xFF); //先放高八位
        this.buffer[offset++] = (byte) (v & 0xFF); //再放低八位
        this.updataSize();
        return 2;
    }

    //在缓冲区指定位置写入一个char型数据的字节码
    public MByteBuffer writeChar(int offset, char v) {
        this.assetOffset(offset, 2);
        this.buffer[offset++] = (byte) ((v >> 8) & 0xFF);
        this.buffer[offset++] = (byte) (v & 0xFF);
        return this;
    }

    //读取偏移量位置存放的char数据
    public char readChar() {
        this.assetOffset(offset, 2);
        short n = this.buffer[this.offset++];
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF); //按位与相当于将低八位和高八位连起来
        return (char) n;
    }

    //读取指定位置存放的char型数据
    public char readChar(int offset) {
        this.assetOffset(offset, 2);
        short n = this.buffer[offset++];
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        return (char) n;
    }

    //在缓冲区中附加一个short数据的字节码
    public int appendShort(short v) {
        this.extendIfNessary(2);
        this.buffer[offset++] = (byte) ((v >> 8) & 0xFF);
        this.buffer[offset++] = (byte) (v & 0xFF);
        this.updataSize();
        return 2;
    }

    //在指定位置写入一个short数据的字节码
    public MByteBuffer writeShort(int offset, short v) {
        this.assetOffset(offset, 2);
        this.buffer[offset++] = (byte) ((v >> 8) & 0xFF);
        this.buffer[offset++] = (byte) (v & 0xFF);
        return this;
    }

    //读取偏移量位置存放的short数据
    public short readShort() {
        this.assetOffset(offset, 2);
        short n = this.buffer[this.offset++];
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        return n;
    }

    //读取指定位置存放的short型数据
    public short readShort(int offset) {
        this.assetOffset(offset, 2);
        short n = this.buffer[offset++];
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        return n;
    }

    //在缓冲区中附加一个int数据的字节码
    public int appendInt(int v) {
        this.extendIfNessary(4);
        this.buffer[offset++] = (byte) ((v >> 24) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 16) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 8) & 0xFF);
        this.buffer[offset++] = (byte) (v & 0xFF);
        this.updataSize();
        return 4;
    }

    //在指定位置写入一个int数据的字节码
    public MByteBuffer writeInt(int offset, int v) {
        this.assetOffset(offset, 4);
        this.buffer[offset++] = (byte) ((v >> 24) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 16) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 8) & 0xFF);
        this.buffer[offset++] = (byte) (v & 0xFF);
        return this;
    }

    //读取偏移量位置存放的int型数据
    public int readInt() {
        this.assetOffset(offset, 4);
        int n = this.buffer[this.offset++];
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        return n;
    }

    //读取指定位置存放的int型数据
    public int readInt(int offset) {
        this.assetOffset(offset, 4);
        int n = this.buffer[offset++];
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        return n;
    }

    //在缓冲区中附加一个long数据的字节码
    public int appendLong(long v) {
        this.extendIfNessary(8);
        this.buffer[offset++] = (byte) ((v >> 56) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 48) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 40) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 32) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 24) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 16) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 8) & 0xFF);
        this.buffer[offset++] = (byte) (v & 0xFF);
        this.updataSize();
        return 8;
    }

    //在指定位置写入一个long数据的字节码
    public MByteBuffer writeLong(int offset, long v) {
        this.assetOffset(offset, 8);
        this.buffer[offset++] = (byte) ((v >> 56) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 48) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 40) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 32) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 24) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 16) & 0xFF);
        this.buffer[offset++] = (byte) ((v >> 8) & 0xFF);
        this.buffer[offset++] = (byte) (v & 0xFF);
        return this;
    }

    //读取偏移量位置存放的long型数据
    public long readLong() {
        this.assetOffset(offset, 8);
        long n = this.buffer[this.offset++];
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        return n;
    }

    //读取指定位置存放的long型数据
    public long readLong(int offset) {
        this.assetOffset(offset, 8);
        long n = this.buffer[offset++];
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        n <<= 8;
        n |= (this.buffer[offset++] & 0xFF);
        return n;
    }

    //在缓冲区中附加一个float数据的字节码（先按IEEE 754标准转int）
    public int appendFloat(float v) {
        int n = Float.floatToIntBits(v);
        int len = this.appendInt(n);
        this.updataSize();
        return len;
    }

    //在缓冲区中指定位置写入一个float数据的字节码（先按IEEE 754标准转int）
    public MByteBuffer writeFloat(int offset, float v) {
        int n = Float.floatToIntBits(v);
        this.writeInt(offset, n);
        return this;
    }

    //读取偏移量位置存放的float型数据
    public float readFloat() {
        int n = this.readInt();
        return Float.intBitsToFloat(n);
    }

    //读取指定位置存放的long型数据
    public float readFloat(int offset) {
        int n = this.readInt(offset);
        return Float.intBitsToFloat(n);
    }

    //在缓冲区中附加一个double数据的字节码（先按IEEE 754标准转int）
    public int appendDouble(double v) {
        long n = Double.doubleToLongBits(v);
        int len = this.appendLong(n);
        this.updataSize();
        return len;
    }

    //在缓冲区中指定位置写入一个double数据的字节码（先按IEEE 754标准转int）
    public MByteBuffer writeDouble(int offset, double v) {
        long n = Double.doubleToLongBits(v);
        this.writeLong(offset, n);
        return this;
    }

    //读取偏移量位置存放的double型数据
    public double readDouble() {
        long n = this.readLong();
        return Double.longBitsToDouble(n);
    }

    //读取指定位置存放的double型数据
    public double readDouble(int offset) {
        long n = this.readLong(offset);
        return Double.longBitsToDouble(n);
    }

    //在缓冲区中附加一段字节码数组
    public int appendBytes(byte[] bytes) {
        this.extendIfNessary(bytes.length);
        System.arraycopy(bytes, 0, this.buffer, this.offset, bytes.length);
        this.offset += bytes.length;
        this.updataSize();
        return bytes.length;
    }

    //在缓冲区中指定位置处添加一段字节码数组
    public MByteBuffer writeBytes(int offset, byte[] bytes) {
        this.assetOffset(offset, bytes.length);
        System.arraycopy(bytes, 0, this.buffer, offset, bytes.length);
        this.updataSize();
        return this;
    }

    //获取缓冲区中从偏移量位置开始存放的指定长度的一段字节码数据
    public byte[] readBytes(int len) {
        if (len < 0) {
            len = 0;
        }
        this.assetOffset(offset, len);
        byte[] bytes = new byte[len];
        System.arraycopy(this.buffer, this.offset, bytes, 0, len);
        this.offset += len;
        return bytes;
    }

    //获取缓冲区中从指定位置开始存放的指定长度的一段字节码数据
    public byte[] readBytes(int offset, int len) {
        if (len < 0) {
            len = 0;
        }
        this.assetOffset(offset, len);
        byte[] bytes = new byte[len];
        System.arraycopy(this.buffer, offset, bytes, 0, len);
        return bytes;
    }

    /**
     * 写入变长的整形
     *
     * @param n
     * @return 实际写入的长度（单位是Byte）
     */
    //在缓冲区中附加一段变长整型的字节码数据
    public int appendMVLInt(MVLInt n) {
        byte[] buffer = n.getBytes();
        return this.appendBytes(buffer);
    }

    //在缓冲区中附加一段变长整型的字节码数据（先根据传入的long型数据构造其对应的变长整型类对象）
    public int appendMVLInt(long n) {
        MVLInt vli = new MVLInt(n);
        byte[] buffer = vli.getBytes();
        return this.appendBytes(buffer);
    }

    //在缓冲区中指定位置处附加一段变长整型的字节码数据
    public int writeMVLInt(int offset, MVLInt n) {
        byte[] buffer = n.getBytes();
        this.writeBytes(offset, buffer);
        return n.getLen();
    }

    //读取偏移量处存放的变长整型数据
    public MVLInt readMVLInt() {
        byte b = this.readByte(); //先获取高八位（read完后偏移量加一）
        if (b >= 0) { //高八位大于0说明最高位是0，存放的是一字节的变长整型数据
            return new MVLInt(b); //返回这个字节即可
        }

        this.back(1); //回退一个字节（因为高八位也是变长整型数据的一部分）

        if ((b & 0b11100000) == MVLInt.Bit2Flag) { //通过高三位可以判断是几字节的变长整型数
            byte[] buffer = this.readBytes(2); //高三位100是两字节变长整型
            return MVLInt.valueOf(buffer, 0);
        }
        if ((b & 0b11100000) == MVLInt.Bit4Flag) {
            byte[] buffer = this.readBytes(4); //高三位110是四字节变长整型
            return MVLInt.valueOf(buffer, 0);
        }
        if ((b & 0b11100000) == MVLInt.Bit8Flag) {
            byte[] buffer = this.readBytes(8); //高三位111是八字节变长整型
            return MVLInt.valueOf(buffer, 0);
        }

        throw new MInvalidValueException("无法正确读取MVLInt");
    }

    //读取指定位置处存放的变长整型数据
    public MVLInt readMVLInt(int offset) {
        byte b = this.readByte(offset);
        if (b > 0) {
            return new MVLInt(b);
        }

        //此处不需要回退是因为可以直接从指定位置获取字节码数据，并不涉及原本的偏移量
        if ((b & 0b11100000) == MVLInt.Bit2Flag) {
            byte[] buffer = this.readBytes(offset, 2);
            return MVLInt.valueOf(buffer, 0);
        }
        if ((b & 0b11100000) == MVLInt.Bit4Flag) {
            byte[] buffer = this.readBytes(offset, 4);
            return MVLInt.valueOf(buffer, 0);
        }
        if ((b & 0b11100000) == MVLInt.Bit8Flag) {
            byte[] buffer = this.readBytes(offset, 8);
            return MVLInt.valueOf(buffer, 0);
        }

        throw new MInvalidValueException("无法正确读取MVLInt");
    }
}

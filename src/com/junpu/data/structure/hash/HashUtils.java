package com.junpu.data.structure.hash;

/**
 * hash 工具类
 *
 * @author junpu
 * @date 2022/4/6
 */
public class HashUtils {

    /**
     * int 类型的 hash 值，为它本身
     */
    public static int hash(int value) {
        return value;
    }

    /**
     * float 类型的 hash 值，为 float 值对应的 int 值
     */
    public static int hash(float value) {
        return Float.floatToIntBits(value);
    }

    /**
     * long 类型的 hash 值，为它的高 32 位与低 32 位异或的值，再强制转换成 int
     * 选择异或而不是与、或操作，是因为如果高 32 为都是 1，哈希冲突太大；
     */
    public static int hash(long value) {
        return (int) (value ^ (value >>> 32));
    }

    /**
     * double 类型的 hash 值，为它对应的 long 类型的值的 hash 值；
     */
    public static int hash(double value) {
        long bits = Double.doubleToLongBits(value);
        return (int) (bits ^ (bits >>> 32));
    }

    /**
     * 字符串的 hash 值
     * 例：
     * “abcd” = a * n^3 + b * n^2 + c * n^1 + d * n^0 = [(a * n + b) * n + c] * n + d;
     * n 的值可以自由设定;
     * java 中 n 的值为 31，因为 31 为奇素数，它的乘法运算可以转换为位运算，31 * i = (i << 5) - i；
     * jvm 会将 31 的乘法运算转换为位运算，以提高效率;
     */
    public static int hash(String value) {
        int len = value.length();
        int code = 0;
        for (int i = 0; i < len; i++) {
            code = code * 31 + value.charAt(i); // 等价于 (code << 5) - code + value.charAt(i)
        }
        return code;
    }

    /**
     * hash 转换为 index.
     * hash 值要想转换为数组下标，用 hash % arr.length 就行；
     * 由于取模运算比位运算效率低，所以可以用位运算代替：
     * hash & (arr.length - 1)，且 arr.length 必须是 2^n，也就是 2，4，8，16...；
     * 例：
     * hash = 01010101, array.length = 16(二进制为 10000);
     * hash & (array.length - 1) = 01010101 & 1111 = 101，101 转换为10进制为 5；
     * 所以 index = 5；
     *
     * @param arrayLength 必须是 2^n
     */
    public static int hashToIndex(int hash, int arrayLength) {
        return hash & (arrayLength - 1);
    }
}

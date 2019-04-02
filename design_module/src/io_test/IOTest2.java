package io_test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class IOTest2 {
    public static void main(String[] args) {
        //写一个字符
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(new FileOutputStream("c.txt"));//查看源码，java8 默认utf-8
            osw.write('a');
            osw.write(97);
            osw.write('中');
            // 为什么数据没有进去呢?
            // 原因是：字符 = 2字节
            // 文件中数据存储的基本单位是字节。
            // void flush()
            osw.flush();
            //写一个字符数组
            char[] chars = {'a','b','中','国'};
            osw.write(chars);
            osw.flush();
            //写一个字符数组的一部分
            osw.write(chars,2,2);
            osw.flush();
            //写一个字符串
            osw.write("\n\r中国");
            //写一个字符串的一部分
            osw.write("中国你好",2,2);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

package io_test;

import java.io.*;

public class IOTest {
    public static void main(String[] args) {
        FileOutputStream outputStream = null;
        FileInputStream inputStream = null;
        BufferedOutputStream bos;
        BufferedInputStream bis;
        try {
            //FileOutputStream fos = new FileOutputStream(file);
            outputStream = new FileOutputStream("a.txt",true);
            inputStream = new FileInputStream("a.txt");
            bos = new BufferedOutputStream(new FileOutputStream("a.txt", true));
            bis = new BufferedInputStream(new FileInputStream("a.txt"));
            /*
             * 创建字节输出流对象了做了几件事情：
             * A:调用系统功能去创建文件
             * B:创建outputStream对象
             * C:把foutputStream对象指向这个文件
             */

//            for (int i = 0; i <5 ; i++) {
//                outputStream.write("hello".getBytes());
//                outputStream.write("\n\r".getBytes());
//            }
            byte[] bys={97,98,99,100,101};
            bos.write(bys,0,3);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = bis.read(buf)) != -1) {
                System.out.println(new String(buf, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



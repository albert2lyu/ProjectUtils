package com.sunhz.projectutils.fileutils;

import android.content.Context;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 文件操作类
 */
public class FileUtils {


    private FileUtils() {
    }


    /**
     * 写入对象到指定路径
     *
     * @param obj      待写入对象
     * @param fileName 文件名
     * @param filePath 文件路径
     * @throws IOException
     */
    public static synchronized void writeObjectToFile(Serializable obj, String fileName, String filePath)
            throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(new File(filePath, fileName));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } finally {
            if (oos != null) oos.close();
            if (fos != null) fos.close();
        }
    }

    /**
     * 从指定路径读取对象
     *
     * @param fileName 文件名
     * @param filePath 文件路径
     * @return 读取出的对象
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static synchronized Object readObjectToFile(String fileName, String filePath) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(new File(filePath, fileName));
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } finally {
            if (ois != null) ois.close();
            if (fis != null) fis.close();
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param filePath 文件的路径
     * @return 文件拓展名
     */
    public static synchronized String getFileSuffix(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
    }

    /**
     * 判断某文件是否存在
     *
     * @param absolutePackagePath 文件的绝对路径
     * @return true:存在,false:不存在
     */
    public static synchronized boolean isFileExist(String absolutePackagePath) {
        return new File(absolutePackagePath).exists();
    }

    /**
     * 判断当前路径是否是一个文件夹
     *
     * @param file 路径
     * @return true:是文件夹,false:不是文件夹
     */
    public static synchronized boolean isDirectory(File file) {
        return file.isDirectory();
    }

    /**
     * 判断当前路径是否是一个文件
     *
     * @param file 路径
     * @return true:是文件,false:不是文件
     */
    public static synchronized boolean isFile(File file) {
        return file.isFile();
    }

    /**
     * 删除文件,删除目录,删除目录下所有文件
     *
     * @param path 将要删除的文件目录
     * @return true:删除成功,false:删除失败
     */
    public static synchronized boolean deleteDir(File path) {
        if (path.isDirectory()) {
            String[] children = path.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(path, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return path.delete();
    }

    /**
     * 获取Asset文件夹下的文本内容
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static synchronized String getAssetFileContent(Context mContext, String fileName) throws Exception {
        return InputStream2String(mContext.getApplicationContext().getResources().getAssets().open(fileName));
    }

    /**
     * 根据inputStream生成String 并关闭inputStream
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static synchronized String InputStream2String(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException();
        }
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }

            return outputStream.toString();
        } finally {
            if (outputStream != null) outputStream.close();
            if (inputStream != null) inputStream.close();
        }
    }

    /**
     * 解析xml
     *
     * @param file
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static synchronized Document XmlByDomj4(File file) throws ParserConfigurationException, SAXException,
            IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(file);
    }

    /**
     * 根据指定编码，读取数据
     *
     * @param is        字符流
     * @param encodeStr 字符编码
     * @return 内容
     * @throws Exception
     */
    public static synchronized String read(InputStream is, String encodeStr) throws IOException {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            byte[] data = out.toByteArray();
            return new String(data, encodeStr);
        } finally {
            if (is != null) is.close();
            if (out != null) out.close();
        }
    }

    /**
     * 根据默认编码,读取文本文件
     *
     * @param file
     * @return str
     * @throws IOException
     */
    public static synchronized String read(File file) throws IOException {
        InputStreamReader inputReader = null;
        BufferedReader bufferReader = null;
        OutputStream outputStream = null;
        StringBuffer strBuffer = new StringBuffer();
        try {
            InputStream inputStream = new FileInputStream(file);
            inputReader = new InputStreamReader(inputStream);
            bufferReader = new BufferedReader(inputReader);

            // 读取一行
            String line = null;

            while ((line = bufferReader.readLine()) != null) {
                strBuffer.append(line);
            }
        } finally {
            if (inputReader != null) inputReader.close();
            if (bufferReader != null) bufferReader.close();
            if (outputStream != null) outputStream.close();
        }
        return strBuffer.toString();
    }

    /**
     * 写入文本
     *
     * @param file
     * @param writeData
     * @throws IOException
     */
    public static synchronized void write(File file, String writeData) throws IOException {
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(file);
            writer = new BufferedWriter(fw);
            writer.write(writeData);
        } finally {
            if (writer != null) writer.close();
            if (fw != null) fw.close();
        }
    }

    /**
     * 将raw中的初始文件保存到指定目录下
     *
     * @param rawId        raw中文件的id
     * @param fileSavePath 转储的文件路径文件名称
     * @return true:保存成功
     * @throws IOException
     */
    public static synchronized boolean InitFileToSDCard(Context mContext, int rawId, String fileSavePath) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = mContext.getApplicationContext().getResources().openRawResource(rawId); // 这里就是Raw文件引用位置
            SaveInputStreamToFile(inputStream, fileSavePath);
        } finally {
            if (inputStream != null) inputStream.close();
        }
        return true;
    }

    /**
     * 将Assets中的初始文件保存到指定目录下
     *
     * @param assetsFileName Assets中文件的名称
     * @param fileSavePath   转储的文件路径文件名称
     * @return true:保存成功
     * @throws Exception
     */
    public static synchronized boolean InitAssetsFileToSDCard(Context mContext, String assetsFileName, String fileSavePath)
            throws IOException {
        if (assetsFileName == null) {
            return false;
        }
        InputStream inputStream = null;
        try {
            inputStream = mContext.getApplicationContext().getResources().getAssets().open(assetsFileName);
            SaveInputStreamToFile(inputStream, fileSavePath);
        } finally {
            if (inputStream != null) inputStream.close();
        }
        return true;
    }

    /**
     * 将数据流保存到指定文件
     *
     * @param inputStream
     * @param fileSavePath
     * @throws IOException
     */
    public static synchronized void SaveInputStreamToFile(InputStream inputStream, String fileSavePath)
            throws IOException {
        File file = new File(fileSavePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = null;
        try {
            int len = 4096;
            int readCount = 0, readSum = 0;
            byte[] buffer = new byte[len];
            fos = new FileOutputStream(fileSavePath);
            while ((readCount = inputStream.read(buffer)) != -1) {
                readSum += readCount;
                fos.write(buffer, 0, readCount);
            }
            fos.flush();
        } finally {
            if (fos != null) fos.close();
        }
    }

    /**
     * 复制文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @throws IOException
     */
    public static synchronized void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null) inBuff.close();
            if (outBuff != null) outBuff.close();
        }
    }

    /**
     * 复制文件夹(不包含该文件夹，只有该文件夹中的全部子文件或文件夹)
     *
     * @param sourceDir 源文件夹
     * @param targetDir 目标文件夹
     * @throws IOException
     */
    public static synchronized void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator
                        + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + File.separator + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + File.separator + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

    /**
     * 返回文件夹下所有文件的绝对路径
     *
     * @param strPath 目标文件夹
     * @return
     */
    public static synchronized List<String> refreshFileList(String strPath) {
        List<String> filelist = new ArrayList<String>();
        File dir = new File(strPath);
        File[] files = dir.listFiles();

        if (files == null) return filelist;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                filelist.addAll(refreshFileList(files[i].getAbsolutePath()));
            } else {
                filelist.add(files[i].getAbsolutePath());
            }
        }
        return filelist;
    }

    /**
     * 返回当前文件夹下所有文件的名字
     *
     * @param strPath 文件路径
     * @return 路径下所有文件的名字
     */
    public static synchronized List<String> queryFileNameList(String strPath) {
        List<String> filelist = new ArrayList<String>();
        File dir = new File(strPath);
        File[] files = dir.listFiles();

        if (files == null) return filelist;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                filelist.addAll(refreshFileList(files[i].getName()));
            } else {
                filelist.add(files[i].getName());
            }
        }
        return filelist;
    }

    /**
     * 将文件创建为一个流
     *
     * @param file 文件路径
     * @return 流对象
     * @throws IOException
     */
    public static synchronized FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canRead()) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

    /**
     * 将文本文件中行的集合返回
     *
     * @param file 文本文件路径
     * @return 文本文件中行的集合
     * @throws IOException
     */
    public static synchronized List<String> readLines(File file) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return readLines(in);
        } finally {
            if (in != null) in.close();
        }
    }

    /**
     * 按照指定编码,将文本文件中行的集合返回
     *
     * @param file     文本文件路径
     * @param encoding 编码
     * @return 文本文件中行的集合
     * @throws IOException
     */
    public static synchronized List<String> readLines(File file, String encoding) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return readLines(in, encoding);
        } finally {
            if (in != null) in.close();
        }
    }

    /**
     * 将文本文件的流中的内容以行集合的方式返回
     *
     * @param input 文本文件的流
     * @return 内容的行集合
     * @throws IOException
     */
    public static synchronized List<String> readLines(InputStream input) throws IOException {
        return readLines(new InputStreamReader(input));
    }

    /**
     * 使用指定的编码,将文本文件的流中的内容以行集合的方式返回
     *
     * @param input    文本文件的流
     * @param encoding 编码
     * @return 内容的行集合
     * @throws IOException
     */
    public static synchronized List<String> readLines(InputStream input, String encoding) throws IOException {
        return readLines(new InputStreamReader(input, encoding));
    }

    /**
     * 文本文件的流中的内容以行集合的方式返回
     *
     * @param input 文本文件的流
     * @return 内容的行集合
     * @throws IOException
     */
    public static synchronized List<String> readLines(Reader input) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(input);
            List<String> list = new ArrayList<String>();
            String line = reader.readLine();
            while (line != null) {
                list.add(line);
                line = reader.readLine();
            }

            return list;
        } finally {
            if (reader != null) reader.close();
        }
    }

    /**
     * 取得文件大小
     *
     * @param file 文件路径
     * @return 若文件不存在, 返回-1.若文件存在,则正常返回文件大小.
     * @throws Exception
     */
    public static synchronized long getFileSizes(File file) throws IOException {
        long size = -1;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                size = fis.available();
            } finally {
                if (fis != null) fis.close();
            }
        }
        return size;
    }

    /**
     * 获取文件夹大小
     *
     * @param file 文件夹路径
     * @return 若文件夹不存在, 则返回-1.若文件夹存在,但文件夹内无文件,则返回0.若文件夹存在,并文件夹内有文件,则正常返回文件夹大小
     * .
     * @throws Exception
     */
    public static synchronized long getDirectorySize(File file) throws Exception// 取得文件夹大小
    {
        long size = -1;
        if (file.isDirectory() && file.exists()) {
            File fileList[] = file.listFiles();
            if (fileList == null) {
                size = 0;
                return size;
            }
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size += getDirectorySize(fileList[i]);
                } else {
                    size += fileList[i].length();
                }
            }
        }
        return size;
    }

    /**
     * 根据文件大小,计算文件大小所属单位
     *
     * @param fileSize 文件大小
     * @return 文件大小和单位
     */
    public static synchronized String formetFileSize(long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "K";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 取文件夹下文件个数,若文件夹下还有文件夹,不包含文件夹个数
     *
     * @param file 文件夹路径
     * @return 文件个数(不包含文件夹)
     */
    public static synchronized long getFileInDirectoryNumber(File file) {
        long fileNumber = 0;
        File flist[] = file.listFiles();
        fileNumber = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                fileNumber += getFileInDirectoryNumber(flist[i]);
                fileNumber--;
            }
        }
        return fileNumber;
    }

    /**
     * 对文件夹下所有文件的后缀名进行修改
     *
     * @param path 文件夹路径
     * @param from 原始后缀名(包含.)
     * @param to   修改后的后缀名(包含.)
     */
    public static synchronized void reNameAllFileInDirectory(String path, String from, String to) {
        File f = new File(path);
        File[] fs = f.listFiles();
        for (int i = 0; i < fs.length; ++i) {
            File fileTemp = fs[i];
            if (fileTemp.isDirectory()) {
                reNameAllFileInDirectory(fileTemp.getPath(), from, to);
            } else {
                String name = fileTemp.getName();
                if (name.endsWith(from)) {
                    fileTemp.renameTo(new File(fileTemp.getParent() + "/"
                            + name.substring(0, name.indexOf(from)) + to));
                }
            }
        }
    }

    /**
     * 将流转换为byte数组
     *
     * @param inputStream 数据流
     * @return byte数组
     * @throws IOException
     */
    public static synchronized byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        } finally {
            if (byteArrayOutputStream != null) byteArrayOutputStream.close();
            if (inputStream != null) inputStream.close();
        }
    }

    /**
     * 将byte数组转换为InputStream
     *
     * @param byteArray 待转换byte数组
     * @return InputStream
     */
    public static synchronized InputStream byteArrayToInputStream(byte[] byteArray) {
        return new ByteArrayInputStream(byteArray);
    }

    /**
     * 获取文件的最后修改时间
     *
     * return 0，为文件不存在
     *
     * @return long
     */
    public static synchronized long getFileLastModifiedTime(File file) {
        return file.lastModified();
    }


}

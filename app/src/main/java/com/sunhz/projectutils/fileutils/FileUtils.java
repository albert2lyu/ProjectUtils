/**
 * Copyright (c) 2015, Spencer , ChinaSunHZ (www.spencer-dev.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import java.io.Reader;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * file action tool
 * Created by Spencer (www.spencer-dev.com) on 15/2/21.
 */
public class FileUtils {

    private FileUtils() {

    }

    /**
     * write object to file
     *
     * @param obj      object
     * @param fileName file name
     * @param filePath file path
     * @throws IOException write failure
     */
    public static void writeObjectToFile(Serializable obj, String fileName, String filePath) throws IOException {
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
     * read object from file
     *
     * @param fileName file name
     * @param filePath file path
     * @return object
     * @throws IOException            read failure
     * @throws ClassNotFoundException Can not find an object corresponding class
     */
    public static Object readObjectToFile(String fileName, String filePath) throws IOException,
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
     * Get the file extension
     *
     * @param filePath file path
     * @return file extension
     */
    public static String getFileSuffix(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
    }

    /**
     * Determine whether a file exists
     *
     * @param absoluteFilePath file absolute Path
     * @return true:exists,false:not exists
     */
    public static boolean isFileExist(String absoluteFilePath) {
        return new File(absoluteFilePath).exists();
    }

    /**
     * Determining whether the current path is a folder
     *
     * @param file file
     * @return true: is directory,false:not directory
     */
    public static boolean isDirectory(File file) {
        return file.isDirectory();
    }

    /**
     * Determining whether the current path is a file
     *
     * @param file file
     * @return true:is file ,false: not file
     */
    public static synchronized boolean isFile(File file) {
        return file.isFile();
    }

    /**
     * Delete files, delete a directory, delete all the files in the directory, including himself
     *
     * @param path  will be deleted directory or file
     * @return true: delete success,false: delete failure
     */
    public static synchronized boolean deleteDir(File path) {
        if (path.isDirectory()) {
            String[] children = path.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(path, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return path.delete();
    }

    /**
     * Get text in Asset folder
     *
     * @param mContext Context
     * @param fileName file name
     * @return file text content
     * @throws IOException get failure
     */
    public static String getAssetFileContent(Context mContext, String fileName) throws Exception {
        return inputStream2String(mContext.getApplicationContext().getResources().getAssets().open(fileName));
    }

    /**
     * get inputStream in Asset folder
     *
     * @param mContext Context
     * @param fileName file name
     * @return file inputStream
     * @throws IOException get failure
     */
    public static InputStream getAssetFileInputStream(Context mContext, String fileName) throws Exception {
        return mContext.getApplicationContext().getResources().getAssets().open(fileName);
    }

    /**
     * inputStream to String, close inputStream
     *
     * @param inputStream inputStream
     * @return String
     * @throws IOException get failure
     */
    public static String inputStream2String(InputStream inputStream) throws IOException {
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
     * parse xml
     *
     * @param file file
     * @return dom document
     * @throws ParserConfigurationException parse failure
     * @throws IOException                  parse failure
     * @throws SAXException                 parse failure
     */
    public static Document XmlByDomj4(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(file);
    }

    /**
     * According to the specified encoding, read a text file
     *
     * @param is        inputStream
     * @param encodeStr encode type
     * @return content
     * @throws IOException read failure
     */
    public static String read(InputStream is, String encodeStr) throws IOException {
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
     * According to the specified encoding, read a text file
     *
     * @param file file
     * @return file content
     * @throws IOException read failure
     */
    public static String read(File file) throws IOException {
        InputStreamReader inputReader = null;
        BufferedReader bufferReader = null;
        InputStream inputStream = null;
        StringBuffer strBuffer = new StringBuffer();
        try {
            inputStream = new FileInputStream(file);
            inputReader = new InputStreamReader(inputStream);
            bufferReader = new BufferedReader(inputReader);

            String line = null;
            while ((line = bufferReader.readLine()) != null) {
                strBuffer.append(line);
            }
        } finally {
            if (bufferReader != null) bufferReader.close();
            if (inputReader != null) inputReader.close();
            if (inputStream != null) inputStream.close();
        }
        return strBuffer.toString();
    }

    /**
     * write text
     *
     * @param file      file
     * @param writeData write text
     * @throws IOException write failure
     */
    public static void write(File file, String writeData) throws IOException {
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
     * Save the raw files to the specified directory
     *
     * @param mContext     Context
     * @param rawId        raw id
     * @param filePath file path (include file name)
     * @return true: save success
     * @throws IOException save failure
     */
    public static boolean saveRawFileTospecifiedPath(Context mContext, int rawId, String filePath) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = mContext.getApplicationContext().getResources().openRawResource(rawId);
            saveInputStreamToFile(inputStream, filePath);
        } finally {
            if (inputStream != null) inputStream.close();
        }
        return true;
    }

    /**
     * Save the Asset files to the specified directory
     *
     * @param mContext       Context
     * @param assetsFileName file name in asset
     * @param filePath   file path (include file name)
     * @return true:保存成功
     * @throws IOException 保存失败
     */
    public static boolean saveAssetsFileTospecifiedPath(Context mContext, String assetsFileName, String filePath)
            throws IOException {
        if (assetsFileName == null) {
            return false;
        }
        InputStream inputStream = null;
        try {
            inputStream = mContext.getApplicationContext().getResources().getAssets().open(assetsFileName);
            saveInputStreamToFile(inputStream, filePath);
        } finally {
            if (inputStream != null) inputStream.close();
        }
        return true;
    }

    /**
     * save inputStream to file
     *
     * @param inputStream  inputStream
     * @param filePath file path (include file name)
     * @throws IOException save failure
     */
    public static void saveInputStreamToFile(InputStream inputStream, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = null;
        try {
            int len = 4096;
            int readCount = 0, readSum = 0;
            byte[] buffer = new byte[len];
            fos = new FileOutputStream(filePath);
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
     * copy file
     *
     * @param sourceFile source file path
     * @param targetFile target file path
     * @throws IOException copy failure
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
        } finally {
            if (inBuff != null) inBuff.close();
            if (outBuff != null) outBuff.close();
        }
    }

    /**
     * copy folder , not including root folder , including all file and all folder in root folder
     *
     * @param sourceDir source folder path
     * @param targetDir target folder path
     * @throws IOException copy failure
     */
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        (new File(targetDir)).mkdirs();
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                File sourceFile = file[i];
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                String dir1 = sourceDir + File.separator + file[i].getName();
                String dir2 = targetDir + File.separator + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

    /**
     * get all file absolute path in the folder
     *
     * @param strPath target folder path
     * @return all file absolute path in the folder
     */
    public static List<String> refreshFileList(String strPath) {
        List<String> fileList = new ArrayList<String>();
        File dir = new File(strPath);
        File[] files = dir.listFiles();

        if (files == null) return fileList;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                fileList.addAll(refreshFileList(files[i].getAbsolutePath()));
            } else {
                fileList.add(files[i].getAbsolutePath());
            }
        }
        return fileList;
    }

    /**
     * get all file name in folder
     *
     * @param strPath target folder path
     * @return all file name in folder
     */
    public static List<String> queryFileNameList(String strPath) {
        List<String> fileList = new ArrayList<String>();
        File dir = new File(strPath);
        File[] files = dir.listFiles();

        if (files == null) return fileList;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                fileList.addAll(refreshFileList(files[i].getName()));
            } else {
                fileList.add(files[i].getName());
            }
        }
        return fileList;
    }

    /**
     * Will create a file into an InputStream
     *
     * @param file file
     * @return inputStream
     * @throws IOException inputStream create failure
     */
    public static FileInputStream openInputStream(File file) throws IOException {
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
     * get list in text file line
     *
     * @param file file
     * @return list in text file line
     * @throws IOException read lines failure
     */
    public static List<String> readLines(File file) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return readLines(in);
        } finally {
            if (in != null) in.close();
        }
    }

    /**
     * specified encoding,get list in text file line
     *
     * @param file     file
     * @param encoding encoding
     * @return  list in text file line
     * @throws IOException read lines failure
     */
    public static List<String> readLines(File file, String encoding) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return readLines(in, encoding);
        } finally {
            if (in != null) in.close();
        }
    }

    /**
     * InputStream text will turn into a list (each line is a list item)
     *
     * @param inputStream inputStream
     * @return list
     * @throws IOException read failure
     */
    public static List<String> readLines(InputStream inputStream) throws IOException {
        return readLines(new InputStreamReader(inputStream));
    }

    /**
     * specified encoding,InputStream text will turn into a list (each line is a list item)
     *
     * @param inputStream inputStream
     * @param encoding encoding
     * @return list
     * @throws IOException read failure
     */
    public static List<String> readLines(InputStream inputStream, String encoding) throws IOException {
        return readLines(new InputStreamReader(inputStream, encoding));
    }

    /**
     * Reader text will turn into a list (each line is a list item)
     *
     * @param reader reader
     * @return list
     * @throws IOException read failure
     */
    public static List<String> readLines(Reader reader) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(reader);
            List<String> list = new ArrayList<String>();
            String line = bufferedReader.readLine();
            while (line != null) {
                list.add(line);
                line = bufferedReader.readLine();
            }

            return list;
        } finally {
            if (bufferedReader != null) bufferedReader.close();
        }
    }

    /**
     * get file size
     *
     * @param file file
     * @return If the file does not exist, -1 is returned. If the file exists, the file is returned to normal size.
     * @throws IOException get file size failure
     */
    public static long getFileSizes(File file) throws IOException {
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
     * get folder size
     *
     * @param file file
     * @return If the folder does not exist, -1 is returned.
     *         If the folder exists, but no file in folder , then returns 0.
     *         If the folder exists, and has the file in folder, the folder returns to normal size.
     *
     * @throws IOException get folder size failure
     */
    public static long getDirectorySize(File file) throws IOException {
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
     * format size
     *
     * @param size  size
     * @return size unit
     */
    public static String formetFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * Get folder under the file number, if there is a folder under the folder, the folder does not contain the number
     *
     * @param file file
     * @return file size
     */
    public static long getFileInDirectoryNumber(File file) {
        long fileNumber = 0;
        File fileList[] = file.listFiles();
        fileNumber = fileList.length;
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                fileNumber += getFileInDirectoryNumber(fileList[i]);
                fileNumber--;
            }
        }
        return fileNumber;
    }

    /**
     * modified all file extension in folder
     *
     * @param path folder path
     * @param from The original extension (included '.')
     * @param to   Suffix after the change (included '.')
     */
    public static void reNameAllFileInDirectory(String path, String from, String to) {
        File f = new File(path);
        File[] fs = f.listFiles();
        for (int i = 0; i < fs.length; ++i) {
            File fileTemp = fs[i];
            if (fileTemp.isDirectory()) {
                reNameAllFileInDirectory(fileTemp.getPath(), from, to);
            } else {
                String name = fileTemp.getName();
                if (name.endsWith(from)) {
                    fileTemp.renameTo(new File(fileTemp.getParent() + "/" + name.substring(0, name.indexOf(from)) + to));
                }
            }
        }
    }

    /**
     * The InputStream is converted to a byte array
     *
     * @param inputStream inputStream
     * @return byte array
     * @throws IOException converted failure
     */
    public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
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
     * The byte array is converted to a inputStream
     *
     * @param byteArray byte array
     * @return InputStream
     */
    public static InputStream byteArrayToInputStream(byte[] byteArray) {
        return new ByteArrayInputStream(byteArray);
    }

    /**
     * Get the file was last modified
     *
     * @param file file
     * @return file exist : return last modified , file not exist : return 0
     */
    public static long getFileLastModifiedTime(File file) {
        return file.lastModified();
    }

    /**
     * Get the file was last modified
     *
     * @param filePath file path
     * @return file exist : return last modified , file not exist : return 0
     */
    public static long getFileLastModifiedTime(String filePath) {
        File file = new File(filePath);
        return file.lastModified();
    }


}

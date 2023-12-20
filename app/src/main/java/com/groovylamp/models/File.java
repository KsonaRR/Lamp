package com.groovylamp.models;

import android.annotation.SuppressLint;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class File {
    @SuppressLint("NewApi")
    public String ReadFile(String path, String fileName){
        try(FileInputStream fis = new FileInputStream(path+fileName)){
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer, 0, buffer.length);
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                Files.createFile(Paths.get(path + fileName));
                WriteFile(path, fileName, "[]");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return "[]";
        }
    }
    public boolean WriteFile(String path,String fileName, String data){
        try (FileOutputStream fos = new FileOutputStream(path + fileName, false)) {
            fos.write(data.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

package com.besprout.voip.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {
	
	public static void writeToFile(String fileName, String data) {
		File file = new File(fileName);
		FileWriter fw = null;
		try {
			fw = new FileWriter(file, false);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(fw != null) fw.close();
			} catch (Exception e2) {
			}
		}
	}
	
	public static void writeToFile(String fileName, String data, boolean isAppend) {
		File file = new File(fileName);
		FileWriter fw = null;
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			
			fw = new FileWriter(file, isAppend);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(fw != null) fw.close();
			} catch (Exception e2) {
			}
		}
	}
	
	public static String readFile(String fileName) {
		StringBuffer sb = new StringBuffer();
		File file = new File(fileName);
		FileInputStream in = null;
		BufferedReader br = null;
		try {
			in = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(in));
			
			String line = null;
			while((line = br.readLine()) != null){
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(br != null) br.close();
				if(in != null) in.close();
			} catch (Exception e2) {
			}
		}
		
		return sb.toString();
	}
}

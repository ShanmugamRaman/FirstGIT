package com.example.configurationdemo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileReaderWriter {

	private static final String FILE_PATH = "C:\\Users\\BD5039\\Desktop\\test.txt";
	private static final int SIZE = 13;
	private static final String dateFormatPattern = "yyyy-MM-dd HH:mm:ss.SSS" ;
	private static List<String> lines = new ArrayList<String>();
	private static final int WRITE_MAX_LIMIT = 1000000;
	
	public static void main(String[] args) {
		writeTempFile();
		readTempFile();
	}

	private static void readTempFile() {
		System.out.println("readTempFile started : "
				+ new SimpleDateFormat().format(System.currentTimeMillis()));

 		readBufferedReader();
		//readMappedByteBuffer();
		readScanner();
		readScanner2();
		
		System.out.println("readTempFile completed : "
				+ new SimpleDateFormat().format(System.currentTimeMillis()));
	}

	private static void readScanner2() {
		long startTime = System.currentTimeMillis();
		System.out.println("Scanner(2) read starts.. "+new SimpleDateFormat(dateFormatPattern).format(startTime));
		try (FileInputStream inputStream =  new FileInputStream(FILE_PATH);
				Scanner sc = new Scanner(inputStream, "UTF-8");) {
		    
		    while (sc.hasNextLine()) {
		        lines.add(sc.nextLine());
		    }
		    // note that Scanner suppresses exceptions
		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(lines.size()+"--"+lines.get(100));
		lines.clear();
		long endTime = System.currentTimeMillis();
		System.out.println("Scanner(2) read ends.. "+new SimpleDateFormat(dateFormatPattern).format(endTime));
		System.out.println("**************************"+(endTime - startTime)+"************************************");
	}

	private static void readScanner() {
		long startTime = System.currentTimeMillis();
		System.out.println("Scanner read starts.. "+new SimpleDateFormat(dateFormatPattern).format(startTime));
		try (Scanner s = new Scanner(new FileReader(FILE_PATH))) {
            while (s.hasNext()) {
            	lines.add(s.nextLine());
            }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(lines.size()+"--"+lines.get(100));
		lines.clear();
		long endTime = System.currentTimeMillis();
		System.out.println("Scanner read ends.. "+new SimpleDateFormat(dateFormatPattern).format(endTime));
		System.out.println("**************************"+(endTime - startTime)+"************************************");
	}

	private static void readMappedByteBuffer() {
		long startTime = System.currentTimeMillis();
		System.out.println("MappedByteBuffer read starts.. "+new SimpleDateFormat(dateFormatPattern).format(startTime));
		try (FileInputStream f = new FileInputStream( FILE_PATH );){
			
			FileChannel ch = f.getChannel( );
			MappedByteBuffer mb = ch.map( MapMode.READ_ONLY, 0L, ch.size() );
			byte[] barray = new byte[SIZE];
			int nGet;
			while( mb.hasRemaining( ) )
			{
			    nGet = Math.min( mb.remaining( ), SIZE );
			    mb.get( barray, 0, nGet );
			    for ( int i=0; i<nGet; i++ )
			    	lines.add(new String(barray));			
			    	
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(lines.size()+"--"+lines.get(100));
		lines.clear();
		long endTime = System.currentTimeMillis();
		System.out.println("MappedByteBuffer read ends.. "+new SimpleDateFormat(dateFormatPattern).format(endTime));
		System.out.println("**************************"+(endTime - startTime)+"************************************");
	}

	private static void readBufferedReader() {
		// BufferedReader.. 
		long startTime = System.currentTimeMillis();
		System.out.println("BufferedReader read starts.. "+new SimpleDateFormat(dateFormatPattern).format(startTime));
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));) {
			lines = br.lines().collect(Collectors.toList());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(lines.size()+"--"+lines.get(100));
		lines.clear();
		long endTime = System.currentTimeMillis();
		System.out.println("BufferedReader read ends.. "+new SimpleDateFormat(dateFormatPattern).format(endTime));
		System.out.println("**************************"+(endTime - startTime)+"************************************");
	}

	private static void writeTempFile() {
		long counter = 1;
		System.out.println("writeTempFile started : "
				+ new SimpleDateFormat().format(System.currentTimeMillis()));
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH));) {
			do {
				counter++;
				bw.write("Hello World!!");
				bw.newLine();
			} while (counter <= WRITE_MAX_LIMIT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("writeTempFile completed : "
				+ new SimpleDateFormat().format(System.currentTimeMillis()));
	}

}

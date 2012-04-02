//: CountLengthCallable.java

package io.concurrent;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*
public class CountLengthCallable<T extends Long> implements Callable<T> {
	private File beginPath;
	
	public CountLengthCallable(File beginPath) {
		this.beginPath = beginPath;
	}

	public T call() throws Exception {
		ArrayList<FutureTask<Long> > fts = new ArrayList<FutureTask<Long> >();
		long allFileLength = 0;
		File[] files = beginPath.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				CountLengthCallable clc = new CountLengthCallable(f);
				FutureTask<Long> ft = new FutureTask<Long>(clc);
				fts.add(ft);
				new Thread(ft).start();
				
				System.out.println(f.getPath() + " : " + ft.get());
				fts.add(ft);
			} else {
				allFileLength += f.length();
			}
		}
		
		for (FutureTask<Long> ft : fts) {
			allFileLength += ft.get();
		}
		
		return (T)new Long(allFileLength);
	}

	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String filePath = CountLengthCallable.class.getResource("").getPath() + "../../../";
		//System.out.println(filePath);
		File f = new File(filePath);
		CountLengthCallable<Long> clc = new CountLengthCallable<Long>(f);
		FutureTask<Long> ft = new FutureTask<Long>(clc);
		new Thread(ft).start();
		
		System.out.println(f.getPath() + " : " + ft.get());
	}
}
*/

public class CountLengthCallable implements Callable<Long> {
	private File beginPath;
	
	public CountLengthCallable(File beginPath) {
		this.beginPath = beginPath;
	}

	public Long call() throws Exception {
		ArrayList<FutureTask<Long> > fts = new ArrayList<FutureTask<Long> >();
		long allFileLength = 0;
		File[] files = beginPath.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				CountLengthCallable clc = new CountLengthCallable(f);
				FutureTask<Long> ft = new FutureTask<Long>(clc);
				new Thread(ft).start();
				
				System.out.println(f.getPath() + " : " + ft.get());
				fts.add(ft);
			} else {
				allFileLength += f.length();
			}
		}
		
		for (FutureTask<Long> ft : fts) {
			allFileLength += ft.get();
		}
		
		return new Long(allFileLength);
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String filePath = CountLengthCallable.class.getResource("").getPath() + "../../";// + "../../../";
		//System.out.println(filePath);
		File f = new File(filePath);
		
		CountLengthCallable clc = new CountLengthCallable(f);
		FutureTask<Long> ft = new FutureTask<Long>(clc);
		new Thread(ft).start();
		
		System.out.println(f.getPath() + " : " + ft.get());
	}
}

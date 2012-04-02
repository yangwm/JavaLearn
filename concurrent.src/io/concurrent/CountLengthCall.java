package io.concurrent;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Executors;

/*
public class CountLengthCall<T extends Long> implements Callable<T>  {
	private File beginPath;
	private ExecutorService pool;
	
	public CountLengthCall(File beginPath, ExecutorService pool) {
		this.beginPath = beginPath;
		this.pool = pool;
	}
	
	public T call() throws Exception {
		ArrayList<Future<Long> > futures = new ArrayList<Future<Long> >();
		long allFileLength = 0;
		File[] files = beginPath.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				CountLengthCall<Long> clc = new CountLengthCall<Long>(f,pool);
				FutureTask<Long> ft = (FutureTask<Long>) pool.submit(clc);
				futures.add(ft);
				System.out.println(f.getPath() + " : " + ft.get());
			} else {
				allFileLength += f.length();
			}
		}
		
		for (Future<Long> f : futures) {
			allFileLength += f.get();
		}
		
		return (T)new Long(allFileLength);
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String filePath = CountLengthCall.class.getResource("").getPath() + "../../../";
		File f = new File(filePath);
		//ExecutorService pool = Executors.newFixedThreadPool(50);
		ExecutorService pool = Executors.newCachedThreadPool();
		CountLengthCall<Long> clc = new CountLengthCall<Long>(f, pool);
		Future<Long> fu = pool.submit(clc);
		System.out.println(fu.get());
		pool.shutdown();
	}
}
*/
public class CountLengthCall implements Callable<Long>  {
	private File beginPath;
	private ExecutorService pool;
	
	public CountLengthCall(File beginPath, ExecutorService pool) {
		this.beginPath = beginPath;
		this.pool = pool;
	}
	
	public Long call() throws Exception {
		ArrayList<Future<Long> > futures = new ArrayList<Future<Long> >();
		long allFileLength = 0;
		File[] files = beginPath.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				CountLengthCall clc = new CountLengthCall(f,pool);
				FutureTask<Long> ft = (FutureTask<Long>) pool.submit(clc);
				//Future<Long> ft = pool.submit(clc);
				futures.add(ft);

				System.out.println(f.getPath() + " : " + ft.get());
			} else {
				allFileLength += f.length();
			}
		}
		
		for (Future<Long> f : futures) {
			allFileLength += f.get();
		}
		
		return new Long(allFileLength);
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String filePath = CountLengthCall.class.getResource("").getPath() + "../../../";
		File f = new File(filePath);

		ExecutorService pool = Executors.newFixedThreadPool(5);
		//ExecutorService pool = Executors.newCachedThreadPool();
		CountLengthCall clc = new CountLengthCall(f, pool);
		FutureTask<Long> ft = (FutureTask<Long>) pool.submit(clc);
		//Future<Long> ft = pool.submit(clc);

		System.out.println(f.getPath() + " : " + ft.get());
		pool.shutdown();
	}
}


package jdyn.timing;

import java.lang.reflect.Array;

public class TimeCreates
{
	public static final long PAUSE_TIME = 100;

	protected long m_start;
	protected boolean m_initialized;
	protected int m_match;
	protected int m_value;
	protected long m_totalTime;
	protected int m_passCount;
	
	public class TimingClass
	{
		protected int m_value;
	}

	protected static void printJVM() {
		System.out.println("Java version " +
			System.getProperty("java.version"));
		String text = System.getProperty("java.vm.name");
		if (text != null) {
			System.out.println(text);
		}
		text = System.getProperty("java.vm.version");
		if (text != null) {
			System.out.println(text);
		}
		text = System.getProperty("java.vm.vendor");
		if (text == null) {
			text = System.getProperty("java.vendor");
		}
		System.out.println(text);
	}
	
	protected void initTime() {
		m_start = System.currentTimeMillis();
	}
	
	protected void reportTime(int pass) {
		long time = System.currentTimeMillis() - m_start;
		System.out.print(" " + time);
		if (pass == 0) {
			m_totalTime = 0;
		} else {
			m_totalTime += time;
			m_passCount = pass;
		}
	}
	
	protected void reportAverage() {
		int avg = (int)((m_totalTime + m_passCount / 2) / m_passCount);
		System.out.println("\n average time = " + avg + " ms.");
	}
	
	protected void pause() {
		for (int i = 0; i < 3; i++) {
			System.gc();
			try {
				Thread.sleep(PAUSE_TIME);
			} catch (InterruptedException ex) {}
		}
	}
	
	public void createObjectDirect(Object[] objs) {
		for (int i = 0; i < objs.length; i++) {
			objs[i] = new Object();
		}
	}
	
	public void createObjectReflect(Object[] objs) {
		try {
			Class<?> oclas = Object.class;
			for (int i = 0; i < objs.length; i++) {
				objs[i] = oclas.newInstance();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	public void createArrayDirect(int size, Object[] objs) {
		for (int i = 0; i < objs.length; i++) {
			objs[i] = new byte[size];
		}
	}
	
	public void createArrayReflect(int size, Object[] objs) {
		for (int i = 0; i < objs.length; i++) {
			objs[i] = Array.newInstance(byte.class, size);
		}
	}
	
	public void runTest(int passes, int loops) throws Exception {
		Object[] objs = new Object[loops];
		System.out.println("\nDirect Object creation:");
		for (int i = 0; i < passes; i++) {
			initTime();
			createObjectDirect(objs);
			reportTime(i);
			pause();
		}
		reportAverage();
		System.out.println("Reflection Object creation:");
		for (int i = 0; i < passes; i++) {
			initTime();
			createObjectReflect(objs);
			reportTime(i);
			pause();
		}
		reportAverage();
		System.out.println("Direct byte[8] creation:");
		for (int i = 0; i < passes; i++) {
			initTime();
			createArrayDirect(8, objs);
			reportTime(i);
			pause();
		}
		reportAverage();
		System.out.println("Reflection byte[8] creation:");
		for (int i = 0; i < passes; i++) {
			initTime();
			createArrayReflect(8, objs);
			reportTime(i);
			pause();
		}
		reportAverage();
		System.out.println("Direct byte[64] creation:");
		for (int i = 0; i < passes; i++) {
			initTime();
			createArrayDirect(64, objs);
			reportTime(i);
			pause();
		}
		reportAverage();
		System.out.println("Reflection byte[64] creation:");
		for (int i = 0; i < passes; i++) {
			initTime();
			createArrayReflect(64, objs);
			reportTime(i);
			pause();
		}
		reportAverage();
	}
	
	public static void main(String[] args) throws Exception {
		printJVM();
		TimeCreates inst = new TimeCreates();
		for (int i = 0; i < 2; i++) {
			inst.runTest(5, 500000);
		}
	}
}
/*
Java version 1.6.0_13
Java HotSpot(TM) Client VM
11.3-b02
Sun Microsystems Inc.

Direct Object creation:
 78 47 47 47 31
 average time = 43 ms.
Reflection Object creation:
 141 125 125 141 125
 average time = 129 ms.
Direct byte[8] creation:
 156 125 125 109 125
 average time = 121 ms.
Reflection byte[8] creation:
 219 219 235 219 234
 average time = 227 ms.
Direct byte[64] creation:
 297 235 219 218 218
 average time = 223 ms.
Reflection byte[64] creation:
 297 297 297 296 297
 average time = 297 ms.

Direct Object creation:
 31 31 32 31 31
 average time = 31 ms.
Reflection Object creation:
 125 125 125 125 125
 average time = 125 ms.
Direct byte[8] creation:
 47 109 109 109 109
 average time = 109 ms.
Reflection byte[8] creation:
 219 219 235 219 219
 average time = 223 ms.
Direct byte[64] creation:
 282 219 219 234 219
 average time = 223 ms.
Reflection byte[64] creation:
 313 313 312 312 313
 average time = 313 ms.
*/

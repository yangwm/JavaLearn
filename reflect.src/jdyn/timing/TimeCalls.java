
package jdyn.timing;

import java.lang.reflect.Method;

/**
 * 清单 8：方法接入性能测试代码
 * 
 * @author yangwm in Nov 23, 2009 11:14:57 AM
 */
public class TimeCalls
{
	public static final int MULTIPLIER_VALUE = 53;
	public static final int ADDITIVE_VALUE = 4;
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
		
		public void step() {
			m_value = (m_value + ADDITIVE_VALUE) * MULTIPLIER_VALUE;
		}
		
		public int step(int value) {
			return (value + ADDITIVE_VALUE) * MULTIPLIER_VALUE;
		}
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
	
	protected void reportTime(int pass, int value) {
		long time = System.currentTimeMillis() - m_start;
		System.out.print(" " + time);
		if (m_initialized) {
			if (m_match != value) {
				System.out.println("\nError - result value mismatch");
				System.exit(1);
			}
		} else {
			m_match = value;
			m_initialized = true;
		}
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
	
	private void step() {
		m_value = (m_value + ADDITIVE_VALUE) * MULTIPLIER_VALUE;
	}
	
	public int callDirectNoArgs(int loops) {
		m_value = 0;
		for (int index = 0; index < loops; index++) {
			step();
		}
		return m_value;
	}
	
	private int step(int value) {
		return (value + ADDITIVE_VALUE) * MULTIPLIER_VALUE;
	}
	
	public int callDirectArgs(int loops) {
		int value = 0;
		for (int index = 0; index < loops; index++) {
			value = step(value);
		}
		return value;
	}
	
	public int callReferenceNoArgs(int loops) {
		TimingClass timing = new TimingClass();
		for (int index = 0; index < loops; index++) {
			timing.step();
		}
		return timing.m_value;
	}
	
	public int callReferenceArgs(int loops) {
		TimingClass timing = new TimingClass();
		int value = 0;
		for (int index = 0; index < loops; index++) {
			value = timing.step(value);
		}
		return value;
	}
	
	public int callReflectNoArgs(int loops) throws Exception {
		TimingClass timing = new TimingClass();
		try {
			Method method = TimingClass.class.getMethod("step", new Class [0]);
			Object[] args = new Object[0];
			for (int index = 0; index < loops; index++) {
				method.invoke(timing, args);
			}
			return timing.m_value;
		} catch (Exception ex) {
			System.out.println("Error using reflection");
			throw ex;
		}
	}
	
	public int callReflectArgs(int loops) throws Exception {
		TimingClass timing = new TimingClass();
		try {
			Method method = TimingClass.class.getMethod
				("step", new Class [] { int.class });
			Object[] args = new Object[1];
			Object value = new Integer(0);
			for (int index = 0; index < loops; index++) {
				args[0] = value;
				value = method.invoke(timing, args);
			}
			return ((Integer)value).intValue();
		} catch (Exception ex) {
			System.out.println("Error using reflection");
			throw ex;
		}
	}
	
	public void runTest(int passes, int loops) throws Exception {
		System.out.println("\nDirect call using member field:");
		for (int i = 0; i < passes; i++) {
			initTime();
			int result = callDirectNoArgs(loops);
			reportTime(i, result);
			pause();
		}
		reportAverage();
		System.out.println("Direct call using passed value:");
		for (int i = 0; i < passes; i++) {
			initTime();
			int result = callDirectArgs(loops);
			reportTime(i, result);
			pause();
		}
		reportAverage();
		System.out.println("Call to object using member field:");
		for (int i = 0; i < passes; i++) {
			initTime();
			int result = callReferenceNoArgs(loops);
			reportTime(i, result);
			pause();
		}
		reportAverage();
		System.out.println("Call to object using passed value:");
		for (int i = 0; i < passes; i++) {
			initTime();
			int result = callReferenceArgs(loops);
			reportTime(i, result);
			pause();
		}
		reportAverage();
		System.out.println("Reflection call using member field:");
		for (int i = 0; i < passes; i++) {
			initTime();
			int result = callReflectNoArgs(loops);
			reportTime(i, result);
			pause();
		}
		reportAverage();
		System.out.println("Reflection call using passed value:");
		for (int i = 0; i < passes; i++) {
			initTime();
			int result = callReflectArgs(loops);
			reportTime(i, result);
			pause();
		}
		reportAverage();
	}
	
	public static void main(String[] args) throws Exception {
		printJVM();
		TimeCalls inst = new TimeCalls();
		for (int i = 0; i < 2; i++) {
			inst.runTest(5, 10000000);
		}
	}
}
/*
Java version 1.6.0_13
Java HotSpot(TM) Client VM
11.3-b02
Sun Microsystems Inc.

Direct call using member field:
 47 46 46 46 62
 average time = 50 ms.
Direct call using passed value:
 16 31 16 31 16
 average time = 24 ms.
Call to object using member field:
 47 47 47 47 47
 average time = 47 ms.
Call to object using passed value:
 16 31 15 32 15
 average time = 23 ms.
Reflection call using member field:
 1125 1078 1031 1047 1047
 average time = 1051 ms.
Reflection call using passed value:
 1281 1265 1219 1219 1219
 average time = 1231 ms.

Direct call using member field:
 47 62 46 46 46
 average time = 50 ms.
Direct call using passed value:
 31 16 31 16 31
 average time = 24 ms.
Call to object using member field:
 47 47 47 63 47
 average time = 51 ms.
Call to object using passed value:
 15 31 16 31 16
 average time = 24 ms.
Reflection call using member field:
 1062 1063 1062 1047 1047
 average time = 1055 ms.
Reflection call using passed value:
 1234 1235 1219 1219 1234
 average time = 1227 ms.

*/

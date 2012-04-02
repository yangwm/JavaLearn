
package jdyn.timing;

import java.lang.reflect.Field;

/**
 * 清单 7：字段接入性能测试代码
 * 
 * @author yangwm in Nov 23, 2009 10:42:50 AM
 */
public class TimeAccesses
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
	
	public int accessDirect(int loops) {
		m_value = 0;
		for (int index = 0; index < loops; index++) {
			m_value = (m_value + ADDITIVE_VALUE) * MULTIPLIER_VALUE;
		}
		return m_value;
	}
	
	public int accessReference(int loops) {
		TimingClass timing = new TimingClass();
		for (int index = 0; index < loops; index++) {
			timing.m_value = (timing.m_value + ADDITIVE_VALUE) *
				MULTIPLIER_VALUE;
		}
		return timing.m_value;
	}
	
	public int accessReflection(int loops) throws Exception {
		TimingClass timing = new TimingClass();
		try {
			Field field = TimingClass.class.getDeclaredField("m_value");
			for (int index = 0; index < loops; index++) {
				int value = (field.getInt(timing) + ADDITIVE_VALUE) *
					MULTIPLIER_VALUE;
				field.setInt(timing, value);
			}
			return timing.m_value;
		} catch (Exception ex) {
			System.out.println("Error using reflection");
			throw ex;
		}
	}
	
	public void runTest(int passes, int loops) throws Exception {
		System.out.println("\nDirect access using member field:");
		for (int i = 0; i < passes; i++) {
			initTime();
			int result = accessDirect(loops);
			reportTime(i, result);
			pause();
		}
		reportAverage();
		System.out.println("Reference access to member field:");
		for (int i = 0; i < passes; i++) {
			initTime();
			int result = accessReference(loops);
			reportTime(i, result);
			pause();
		}
		reportAverage();
		System.out.println("Reflection access to member field:");
		for (int i = 0; i < passes; i++) {
			initTime();
			int result = accessReflection(loops);
			reportTime(i, result);
			pause();
		}
		reportAverage();
	}
	
	public static void main(String[] args) throws Exception {
		printJVM();
		TimeAccesses inst = new TimeAccesses();
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

Direct access using member field:
 78 47 47 47 47
 average time = 47 ms.
Reference access to member field:
 47 47 47 47 47
 average time = 47 ms.
Reflection access to member field:
 17797 17813 17672 17672 17625
 average time = 17696 ms.

Direct access using member field:
 47 63 47 47 47
 average time = 51 ms.
Reference access to member field:
 47 47 47 47 47
 average time = 47 ms.
Reflection access to member field:
 17672 17641 17703 17750 17734
 average time = 17707 ms.

*/

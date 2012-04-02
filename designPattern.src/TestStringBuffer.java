

public class TestStringBuffer{
	public static void main(String[] args){
		String[] datas=new String[20000];
		for(int i=0;i<datas.length;i++){
			datas[i]=new String(String.valueOf(i));
		}
		String str1="Number:";
		String str2="Number:";
		long t1=stringBufferAppend(str1,datas);
		System.out.println("t1="+t1);
		long t2=stringAppend(str2,datas);
		System.out.println("t2="+t2);
		
	}
	public static long stringAppend(String str,String[] datas){
		long time1=System.nanoTime();
		for(int i=0;i<datas.length;i++){
			str=str+datas[i];
		}
		long time2=System.nanoTime();
		return time2-time1;
	}
	public static long stringBufferAppend(String str,String[] datas){
		long time1=System.nanoTime();
		StringBuffer sb=new StringBuffer(str);
		for(int i=0;i<datas.length;i++){
			sb.append(datas[i]);
		}
		str=sb.toString();
		long time2=System.nanoTime();		
		return time2-time1;
	}
}

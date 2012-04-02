package jvm.oom;

import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Case: memory leak, Task type,should remove
 * 
 * startup args: -Xms20m -Xmx20m -Xmn10m -XX:+HeapDumpOnOutOfMemoryError 
 */
public class JavaHeapSpaceCase4 {

	public static void main(String[] args){
		TaskExecutor executor=new TaskExecutor();
		executor.execute();
		for (int i = 0; i < 1000; i++) {
			Task task=new Task();
			String type=String.valueOf(System.nanoTime());
			task.setType(type);
			executor.addTaskHandler(type, new TaskHandler());
			executor.addTask(task);
		}
	}

}

class Task{
	private String type=null;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}

class TaskHandler{
	private byte[] bytes=new byte[1024*256];
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public void execute(Task task){
		;
	}
}

class TaskExecutor{
	private final Stack<Task> tasks=new Stack<Task>();
	private Map<String, TaskHandler> handlers=new ConcurrentHashMap<String, TaskHandler>();
	public void addTask(Task task){
		synchronized (tasks) {
			tasks.push(task);	
			tasks.notifyAll();
		}
	}
	public void addTaskHandler(String type,TaskHandler handler){
		handlers.put(type, handler);
	}
	public void execute(){
		new Thread(new Runnable() {
			public void run() {
				while(true){
					Task nextTask=null;
					synchronized (tasks){
						if(tasks.size()==0){
							try {
								tasks.wait();
							} 
							catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						nextTask=tasks.pop();
					}
					TaskHandler handler=handlers.get(nextTask.getType());
					handler.execute(nextTask);
//					handlers.remove(nextTask.getType());
				}
			}
		}).start();
	}
}

/*
java -Xms20m -Xmx20m -Xmn10m -XX:+HeapDumpOnOutOfMemoryError jvm.oom.JavaHeapSpaceCase4

java.lang.OutOfMemoryError: Java heap space
Dumping heap to java_pid3760.hprof ...
Heap dump file created [20082611 bytes in 0.720 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    at jvm.oom.TaskHandler.<init>(JavaHeapSpaceCase4.java:42)
    at jvm.oom.JavaHeapSpaceCase4.main(JavaHeapSpaceCase4.java:21)

*/

package coroutine.kilim.benchmark;

import java.util.concurrent.CountDownLatch;

import kilim.Mailbox;
import kilim.Pausable;
import kilim.Task;

public class Main {

	private static final int PROCESSER_COUNT=Runtime.getRuntime().availableProcessors();
	
	private static int perThreadRequests=10000;
	
	private static int request_count=PROCESSER_COUNT * perThreadRequests;
	
	private static CountDownLatch processorLatch=new CountDownLatch(request_count);
	
	public static void main(String[] args) throws Exception{
		if(args.length==1){
			perThreadRequests = Integer.valueOf(args[0]);
			request_count = PROCESSER_COUNT * perThreadRequests;
			processorLatch = new CountDownLatch(request_count);
		}
		System.out.println("=========Kilim Version=========");
		System.out.println(" Receive Task Count: "+PROCESSER_COUNT);
		System.out.println(" Requests Per Receive Task: "+perThreadRequests);
		System.out.println(" Request Counts: "+request_count);
		Main main=new Main();
		main.execute();
	}
	
	protected void execute() throws Exception{
		long beginTime=System.currentTimeMillis();
		for (int i = 0; i < PROCESSER_COUNT; i++) {
			Task task=new ReceiveTask();
			task.start();
		}
		processorLatch.await();
		long endTime=System.currentTimeMillis();
		long consumeTime=endTime-beginTime;
		System.out.println("Consume Time: "+consumeTime+" ms");
		System.out.println("TPS About: "+request_count*1000/consumeTime);
		System.out.println("=========Kilim Version=========");
		System.exit(0);
	}

	class ReceiveTask extends Task{

		public void execute() throws Pausable, Exception {
			for (int i = 0; i < perThreadRequests; i++) {
				Task processor=new ProcessorTask();
				processor.start();
			}
			Task.exit(0);
		}
		
	}
	
	class ProcessorTask extends Task{

		public void execute() throws Pausable, Exception {
			for (Integer i = 0; i < 10; i++) {
				Mailbox<Integer> mailbox=new Mailbox<Integer>(1);
				Task task=new AsyncProcessorTask(mailbox);
				task.start();
				mailbox.get();
			}
			processorLatch.countDown();
			Task.exit(0);
		}
		
	}
	
	class AsyncProcessorTask extends Task{
		
		private Mailbox<Integer> queue;
		
		public AsyncProcessorTask(Mailbox<Integer> queue){
			this.queue=queue;
		}
		
		public void execute() throws Pausable, Exception {
			StringBuilder strBuilder=new StringBuilder();
			for (int i = 0; i < 1000; i++) {
				strBuilder.append("ArrayList[");
				strBuilder.append(i);
				strBuilder.append("];");
			}
			queue.put(0);
		}
	}
	
}

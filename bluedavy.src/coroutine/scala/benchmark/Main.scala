
import scala.actors.{Actor}
import scala.actors.Actor._
import java.util.concurrent.CountDownLatch

object Main {
  
  val PROCESSER_COUNT=Runtime.getRuntime().availableProcessors()
  
  var perThreadRequests=10000
  
  var request_count=PROCESSER_COUNT * perThreadRequests
  
  var processorLatch=new CountDownLatch(request_count)
  
  def main(args : Array[String]) : Unit = {
    if(args.length==1){
		perThreadRequests = Integer.parseInt(args(0));
		request_count = PROCESSER_COUNT * perThreadRequests;
		processorLatch = new CountDownLatch(request_count);
	}
    System.setProperty("actors.maxPoolSize",(PROCESSER_COUNT*2).toString)
	println("=========Scala Version=========");
	println(" Receive Task Count: "+PROCESSER_COUNT);
	println(" Requests Per Receive Task: "+perThreadRequests);
	println(" Request Counts: "+request_count)
	val beginTime=System.currentTimeMillis();
	var i=0
	for (i<- 0 to PROCESSER_COUNT) {
		var actor=new ReceiveActor
		actor.start
		actor ! "GO"
	}
	processorLatch.await();
	val consumeTime=System.currentTimeMillis()-beginTime
	println("Consume Time: "+consumeTime+" ms")
	println("TPS About: "+request_count*1000/consumeTime)
	println("=========Scala Version=========")
	System.exit(0)
  }
  
  class ReceiveActor extends Actor{
  
	def act(){
	   react{
	       case _ => {
	    	   var i=0
			   for(i<- 0 to perThreadRequests){
			     val actor=new ProcessorActor
			     actor.start
			     actor ! "1"
			   }
			   exit
	       }
	   }
	}
  
  }
  
  class ProcessorActor extends Actor{
    
    def act(){
    	loop{
	    	react{
	    	    case command:String => {
	    	    	if(!"10".equals(command)){
			    	  val actor=new AsyncProcessorActor()
			    	  actor.start
			    	  actor ! command
			    	  react{
			    	    case result:String => {
			    	      self ! (Integer.parseInt(result)+1).toString
			    	    }
			    	  }
			    	}
	    	    	else{
	    	    	  processorLatch.countDown
	    	    	}
	    	    }
	    	}
    	}
    }
    
  }
  
}

class AsyncProcessorActor extends Actor{
	  
    def act(){
      loop{
	      react{
	          case command:String => {
	            handle(command)
	          }
	      }
      }
    }
    
    def handle(command:String){
      var strBuilder=new StringBuilder
      var i=0
      for (i<- 0 to 1000) {
    	  strBuilder.append("ArrayList[");
    	  strBuilder.append(i);
    	  strBuilder.append("];");
      }
      sender ! command
    }
    
  }

  
/*
D:\study\scalaStudy>scalac Main.scala

D:\study\scalaStudy>scala Main
=========Scala Version=========
 Receive Task Count: 2
 Requests Per Receive Task: 10000
 Request Counts: 20000
Consume Time: 68219 ms
TPS About: 293
=========Scala Version=========

D:\study\scalaStudy>

*/

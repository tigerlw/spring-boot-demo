package com.camp.akka.actor;

import com.camp.akka.exception.AkkaException;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class GreetActor extends UntypedActor
{
	

	@Override
	public void onReceive(Object msg) throws Throwable {
		// TODO Auto-generated method stub
		if(msg instanceof String)
		{
			/*System.out.println(Thread.currentThread().getName()+"|msg:"+msg);
			
			Thread.sleep(1000*20);*/
			
			if("exception".equals(msg))
			{
			
			   throw new AkkaException(String.valueOf(msg));
			}
		}
	}

}

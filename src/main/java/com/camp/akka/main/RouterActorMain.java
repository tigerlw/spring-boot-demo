package com.camp.akka.main;

import java.util.Scanner;

import com.camp.akka.actor.GreetActor;
import com.camp.akka.actor.RouterActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;

public class RouterActorMain 
{
	public static void main(String args[])
	{
		ActorSystem system = ActorSystem.create("RouterActor");
		
		ActorRef routee = system
				.actorOf(
						new RoundRobinPool(2).props(Props.create(RouterActor.class)
								.withDispatcher("akka.blocking-io-dispatcher").withMailbox("akka.bounded-mailbox")),
						"greet");
		
		long count = 0;
		
		long time = System.currentTimeMillis();
		
		while(true)
		{
		
		  long durtion = System.currentTimeMillis() - time;
		  /*if(durtion>1000*5)
		  {
			  System.out.println("durtion==============================================:"+durtion);
			  break;
		  }*/
			
		   System.out.println("in:");
		   Scanner s = new Scanner(System.in);
		   
		   String msg = s.next();
			
			count++;
			
			//System.out.println("send:"+count);
			
		    routee.tell(String.valueOf(msg), null);
		}
	}

}

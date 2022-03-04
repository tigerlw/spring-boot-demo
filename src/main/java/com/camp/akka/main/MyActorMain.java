package com.camp.akka.main;

import java.util.Scanner;

import com.camp.akka.actor.GreetActor;
import com.camp.akka.actor.MyActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class MyActorMain 
{
	/*public static void main(String args[])
	{
		ActorSystem system = ActorSystem.create("Hello");
		
		//ActorRef geetActor = system.actorOf(Props.create(GreetActor.class), "geetActor");
		
		ActorRef myActor = system.actorOf(Props.create(MyActor.class), "myActor");
		
		
		while(true)
		{
			
		   System.out.println("in:");
		   Scanner s = new Scanner(System.in);
		   
		   String msg = s.next();
			
		   myActor.tell(msg, null);
		}
		
	}*/

}

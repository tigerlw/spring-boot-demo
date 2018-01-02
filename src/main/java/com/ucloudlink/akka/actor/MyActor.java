package com.ucloudlink.akka.actor;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.UntypedActor;
import akka.japi.Function;
import scala.concurrent.duration.Duration;
import static akka.actor.SupervisorStrategy.*;

import com.ucloudlink.akka.exception.AkkaException;

public class MyActor extends UntypedActor
{
	
    private  ActorRef greet;
    
	private  Function<Throwable, Directive> func = new Function<Throwable, Directive>() {
		@Override
		public Directive apply(Throwable t) {
			if(t instanceof AkkaException)
			{
			   AkkaException exp =(AkkaException)t;
			   greet.tell(exp.getMsg(), null);
			}
			return akka.actor.SupervisorStrategy.resume();

		}
	};
    
    private  SupervisorStrategy strategy = new OneForOneStrategy(1, Duration.fromNanos(1000), func);
    
	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}
	
	public MyActor()
	{
		this.greet = getContext().actorOf(Props.create(GreetActor.class), "geetActor");
	}

	@Override
	public void onReceive(Object msg) throws Throwable {
		// TODO Auto-generated method stub
		
		if(msg instanceof String)
		{
			greet.tell(msg, getSelf());
		}
		
	}

}

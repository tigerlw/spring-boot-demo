package com.ucloudlink.akka.actor;

import com.ucloudlink.akka.exception.AkkaException;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.actor.SupervisorStrategy.Directive;
import akka.japi.Function;
import akka.routing.RoundRobinPool;
import scala.concurrent.duration.Duration;

public class RouterActor extends UntypedActor
{
	private ActorRef greet; 
	
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
	
	public RouterActor()
	{
		/*greet = getContext().actorOf(
				new RoundRobinPool(2).props(Props.create(GreetActor.class)
						.withDispatcher("akka.blocking-io-dispatcher").withMailbox("akka.bounded-mailbox")),
				"greet");*/
		
		greet = getContext().actorOf(Props.create(GreetActor.class).withDispatcher("akka.blocking-io-dispatcher")
				.withMailbox("akka.bounded-mailbox"), "greet");
	}
	
	

	@Override
	public void onReceive(Object msg) throws Throwable {
		// TODO Auto-generated method stub
		greet.tell(msg, getSelf());
	}

	public ActorRef getGreet() {
		return greet;
	}

	public void setGreet(ActorRef greet) {
		this.greet = greet;
	}

}

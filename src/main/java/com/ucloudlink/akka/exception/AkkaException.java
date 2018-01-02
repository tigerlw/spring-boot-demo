package com.ucloudlink.akka.exception;

public class AkkaException extends Exception
{
	private String msg;
	
	public AkkaException(String msg)
	{
		this.setMsg(msg);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	

}

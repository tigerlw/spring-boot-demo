package com.ucloudlink.boot.job;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class FlowDetailJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		
		JobDetail jobDetail = context.getJobDetail();
		
		System.out.println("run ======================="+jobDetail.getJobDataMap().getString("jobName"));
		
	}

}

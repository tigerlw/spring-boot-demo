package com.ucloudlink.boot.dao;

import java.util.List;

import com.ucloudlink.boot.domain.GnodeBO;



public interface GnodeDao 
{
	public boolean save(GnodeBO gnode);
	
	public List<GnodeBO> queryAll();

}

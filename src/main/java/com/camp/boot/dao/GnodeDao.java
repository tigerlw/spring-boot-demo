package com.camp.boot.dao;

import java.util.List;

import com.camp.boot.domain.GnodeBO;



public interface GnodeDao 
{
	public boolean save(GnodeBO gnode);
	
	public List<GnodeBO> queryAll();

}

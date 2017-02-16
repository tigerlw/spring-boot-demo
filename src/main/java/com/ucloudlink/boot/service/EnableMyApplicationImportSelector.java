package com.ucloudlink.boot.service;

import org.springframework.cloud.commons.util.SpringFactoryImportSelector;

public class EnableMyApplicationImportSelector extends SpringFactoryImportSelector<EnableMyApplication>{

	@Override
	protected boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}

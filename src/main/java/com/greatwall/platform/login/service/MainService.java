package com.greatwall.platform.login.service;

import java.util.List;

import com.greatwall.platform.system.dto.Resource;
import com.greatwall.platform.system.dto.User;

public interface MainService {

	public List<Resource> getResource(User user,String resType);
}

package com.zlwon.server.service.impl;

import com.zlwon.nosql.dao.TestRepository;
import com.zlwon.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试ServiceImpl
 * @author yangy
 *
 */

@Service
public class TestServiceimpl implements TestService {

	@Autowired
	private TestRepository testRepository;
}

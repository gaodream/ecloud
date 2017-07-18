package com.ecloud.deploy.model;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ecloud.deploy.common.MessageCache;

@RestController
@Deprecated
public class EventBusController {

	private static final Logger LOG = LoggerFactory.getLogger(EventBusController.class);

	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public synchronized void syncDispatchEvent(@RequestBody Map<String, Object> body, HttpServletRequest request) {
		try {
			MessageCache.addEventToQueue(body);
		} catch (Exception e) {
			LOG.error("receive event error : {}", e.getMessage());
		}
	}

}

/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.integration.samples.controlbus;

import org.apache.log4j.Logger;
import org.junit.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.message.GenericMessage;

/**
 * @author Oleg Zhurakousky
 *
 */
public class ControlBusDemoTest {
	
	private static Logger logger = Logger.getLogger(ControlBusDemoTest.class);

	@Test
	public void demoControlBus(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("/META-INF/spring/integration/ControlBusDemo-context.xml");
		MessageChannel controlChannel = ac.getBean("controlChannel", MessageChannel.class);
		PollableChannel adapterOutputChanel = ac.getBean("adapterOutputChanel", PollableChannel.class);
		logger.info("Received before adapter started: " + adapterOutputChanel.receive(1000));
		controlChannel.send(new GenericMessage<String>("@inboundAdapter.start()"));
		logger.info("Received after adapter started: " + adapterOutputChanel.receive(1000));
		controlChannel.send(new GenericMessage<String>("@inboundAdapter.stop()"));
		logger.info("Received after adapter stopped: " + adapterOutputChanel.receive(1000));
	}
}

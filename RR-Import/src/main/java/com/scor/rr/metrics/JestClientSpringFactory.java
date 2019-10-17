package com.scor.rr.metrics;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Jest Client Spring Factory
 * 
 * @author HADDINI Zakariyae
 *
 */
public class JestClientSpringFactory implements FactoryBean<JestClient>, InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(JestClientSpringFactory.class);

	private String host;

	private JestClient instance;

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("starting elasticsearch jest client");

		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder("http://" + host).multiThreaded(true).build());
		instance = factory.getObject();
	}

	@Override
	public JestClient getObject() throws Exception {
		return instance;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void destroy() throws Exception {
		logger.info("closing elasticsearch jest client");

		if (instance != null)
			instance.shutdownClient();
	}

	@Override
	public Class<?> getObjectType() {
		return JestClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}

package com.javatest.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "python")
@PropertySource("classpath:properties/config.properties")
public class ConfigProperties {

	private String pythonFilePath;

	public String getPythonFilePath() {
		return pythonFilePath;
	}

	public void setPythonFilePath(String pythonFilePath) {
		this.pythonFilePath = pythonFilePath;
	}
}
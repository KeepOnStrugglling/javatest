package com.javatest.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Configuration
@ConfigurationProperties(prefix = "python")
@PropertySource("classpath:properties/config.properties")
public class ConfigProperties {

	private String pythonFilePath;
	private String pythonExe;

	public String getPythonFilePath() {
		return pythonFilePath;
	}

	public String getPythonExe() {
		return pythonExe;
	}

	public void setPythonFilePath(String pythonFilePath) {
		this.pythonFilePath = pythonFilePath;
	}
	public void setPythonExe(String pythonExe) {
		this.pythonExe = pythonExe;
	}
}
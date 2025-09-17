package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class FirstApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(FirstApplication.class, args);

		System.out.println("Active profiles:");
		for (String p : ctx.getEnvironment().getActiveProfiles()) {
			System.out.println(" -> " + p);
		}

		String[] beans = ctx.getBeanNamesForType(MyBean.class);
		if (beans.length > 0) {
			MyBean bean = ctx.getBean(MyBean.class);
			System.out.println("Loaded bean: " + bean.label());
			System.out.println("User from properties: " + bean.username());
		} else {
			System.out.println("No MyBean defined for current profile");
		}
	}

	@Configuration
	static class MyBeanConfiguration {

		@Bean
		@Profile("dev")
		public MyBean devProfileBean(@Value("${app.user}") String username) {
			return new MyBean("Bean for DEV", username);
		}

		@Bean
		@Profile("qa")
		public MyBean qaProfileBean(@Value("${app.user}") String username) {
			return new MyBean("Bean for QA", username);
		}
	}

	public record MyBean(String label, String username) {}
}

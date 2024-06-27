package com.biluo;

import com.biluo.model.po.TDicValue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
@EnableMethodSecurity
public class DlykApllication {
	public static final Map<String, List<Object>> dictCacheMap = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(DlykApllication.class, args);
	}
}

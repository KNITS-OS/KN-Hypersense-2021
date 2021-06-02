package com.knits.smartfactory.cucumber;

import com.knits.smartfactory.SmartfactoryApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = SmartfactoryApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}

package com.example.log4j2.log4j2demo.expose.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dante on 2020-07-12.
 * @project log4j2-demo
 */
@Slf4j
@RequestMapping("/home")
@RestController
public class HomeController {

  private static final String LOGGER_WITH_CUSTOM_LAYOUT = "obfuscated.logger";
  private static Logger loggerWithCustomLayout = LogManager.getLogger(LOGGER_WITH_CUSTOM_LAYOUT);

  @GetMapping
  public String greeting() {
    log.info("Greeting from HomeController");
    loggerWithCustomLayout.info("Greeting from HomeController using loggerWithCustomLayout");
    return "hi, from HomeController";
  }

}

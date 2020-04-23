package br.brunocatao.introsecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }

  @GetMapping("/admin")
  public String admin() {
    return "admin";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/nao_autorizado")
  public String naoAutorizado() {
    return "nao_autorizado";
  }
}

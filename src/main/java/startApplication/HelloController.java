package startApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/")
  public String hello() {
    return "Hello World 10";
  }
  @GetMapping("/hello")
  public String hello1() {
    return "Hello World 10";
  }
}

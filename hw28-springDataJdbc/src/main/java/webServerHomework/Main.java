package webServerHomework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        //var context = SpringApplication.run(DbServiceDemo.class, args);

        //context.getBean("actionDemo", ActionDemo.class).action();

        SpringApplication.run(Main.class, args);
    }

}

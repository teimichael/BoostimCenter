package stu.napls.boostimcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BoostimCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoostimCenterApplication.class, args);
    }

}

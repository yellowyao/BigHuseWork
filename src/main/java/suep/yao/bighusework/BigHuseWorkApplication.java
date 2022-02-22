package suep.yao.bighusework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


@SpringBootApplication
@MapperScan(basePackages = "suep.yao.bighusework.Dao")
public class BigHuseWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigHuseWorkApplication.class, args);
    }

}

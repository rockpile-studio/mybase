package studio.rockpile.mybase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyBase implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(MyBase.class);

	public static void main(String[] args) {
		SpringApplication.run(MyBase.class, args);
		logger.info("====== 启动成功 ======");
	}

	public void run(String... args) throws Exception {
		logger.info("====== 开始项目启动后执行功能 ======");
	}
}

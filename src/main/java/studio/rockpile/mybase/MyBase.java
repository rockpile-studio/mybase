package studio.rockpile.mybase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import studio.rockpile.mybase.constant.ServiceTypeEnum;
import studio.rockpile.mybase.service.base.BaseServiceHandler;
import studio.rockpile.mybase.service.base.ServiceArgException;

@SpringBootApplication
public class MyBase {
	private static final Logger logger = LoggerFactory.getLogger(MyBase.class);

	public static void main(String[] args) {
		SpringApplication.run(MyBase.class, args);

		if (args.length < 1) {
			logger.error("未指定操作类型");
			return;
		}

		BaseServiceHandler handler = null;
		try {
			ServiceTypeEnum type = ServiceTypeEnum.getType(args[0]);
			if (type == null) {
				throw new Exception("未知操作类型：" + args[0]);
			}
			handler = ServiceTypeEnum.buildHandler(type);
			handler.perform(args);
			logger.info("服务({})处理成功", type.getKey());
		} catch (ServiceArgException e) {
			if (handler != null) {
				logger.error("服务参数异常：{}", e.getMessage());
				logger.error("\n{}", handler.usage());
			}
		} catch (Exception e) {
			logger.error("服务处理异常：{}", e);
		}
	}

}

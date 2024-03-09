package cronExpression;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author : Arjun Das
 * @since : 08-03-2024
 */
@RestController
@SpringBootApplication
public class CronExpressionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CronExpressionApplication.class, args);
	}

}

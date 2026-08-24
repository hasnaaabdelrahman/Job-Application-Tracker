package com.job.application.tracker;

import com.job.application.tracker.service.TestMailConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestMailConfig.class)
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}

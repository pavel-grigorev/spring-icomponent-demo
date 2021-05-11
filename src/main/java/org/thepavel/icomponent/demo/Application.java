/*
 * Copyright (c) 2020-2021 Pavel Grigorev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thepavel.icomponent.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thepavel.icomponent.InterfaceComponentScan;

@SpringBootApplication
@InterfaceComponentScan
public class Application implements CommandLineRunner {
	@Autowired
	EmailService emailService;

	@Override
	public void run(String... args) {
		emailService.sendConfirmation("Mr. Smith", "goo.gl", "mr.smith@gmail.com");
		emailService.sendWelcome(new UserImpl("Mrs. Smith", "mrs.smith@gmail.com"));
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailTransportBean implements EmailTransport {
  private static final Logger LOGGER = LoggerFactory.getLogger(EmailTransportBean.class);

  @Override
  public void send(String subject, String body, String... to) {
    LOGGER.info("\n==== Sending e-mail ====\nTo: {}\nSubject: {}\nBody:\n{}\n========================", to, subject, body);
  }
}

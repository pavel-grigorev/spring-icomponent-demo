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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

@Component
public class EmailSenderBean implements EmailSender {
  @Autowired
  MessageSource messageSource;

  @Autowired
  ITemplateEngine templateEngine;

  @Autowired
  EmailTransport emailTransport;

  @Override
  public void send(String subjectMessageName, String bodyTemplateName, Map<String, Object> templateParameters, String... to) {
    emailTransport.send(getSubject(subjectMessageName), getBody(bodyTemplateName, templateParameters), to);
  }

  private String getSubject(String subjectMessageName) {
    return messageSource.getMessage(subjectMessageName, null, Locale.getDefault());
  }

  private String getBody(String bodyTemplateName, Map<String, Object> parameters) {
    Context context = new Context();
    context.setVariables(parameters);

    return templateEngine.process(bodyTemplateName, context);
  }
}

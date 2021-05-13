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

package org.thepavel.icomponent.demo.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.stereotype.Component;
import org.thepavel.icomponent.demo.Client;
import org.thepavel.icomponent.demo.EmailSender;
import org.thepavel.icomponent.demo.annotations.Param;
import org.thepavel.icomponent.demo.annotations.To;
import org.thepavel.icomponent.handler.MethodHandler;
import org.thepavel.icomponent.metadata.MethodMetadata;
import org.thepavel.icomponent.metadata.ParameterMetadata;

import java.beans.Introspector;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.core.annotation.MergedAnnotation.VALUE;

@Component
public class EmailServiceMethodHandler implements MethodHandler {
  @Autowired
  EmailSender emailSender;

  @Override
  public Object handle(Object[] arguments, MethodMetadata methodMetadata) {
    // Cut off "send"
    String emailType = methodMetadata.getSourceMethod().getName().substring(4);

    String templateName = Introspector.decapitalize(emailType);
    String subjectKeyInMessageBundle = "email.subject." + templateName;

    List<ParameterMetadata> parametersMetadata = methodMetadata.getParametersMetadata();
    Map<String, Object> templateParameters = new HashMap<>();
    Set<String> emails = new HashSet<>();

    for (int i = 0; i < parametersMetadata.size(); i++) {
      MergedAnnotations parameterAnnotations = parametersMetadata.get(i).getAnnotations();
      Object parameterValue = arguments[i];

      if (parameterAnnotations.isPresent(Param.class)) {
        String parameterName = parameterAnnotations.get(Param.class).getString(VALUE);
        templateParameters.put(parameterName, parameterValue);
      }

      if (parameterAnnotations.isPresent(To.class)) {
        if (parameterValue instanceof String) {
          emails.add((String) parameterValue);
        } else if (parameterValue instanceof Client) {
          Client client = (Client) parameterValue;
          emails.add(client.getEmail());
        }
      }
    }

    emailSender.send(subjectKeyInMessageBundle, templateName, templateParameters, emails);

    return null;
  }
}

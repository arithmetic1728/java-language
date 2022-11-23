/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.language.v1;

import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceSettings;
import java.util.Map;

import org.junit.Test;

public class SampleTest {

  // Create a client by providing API key to stubsettings.
  public LanguageServiceClient createClientWithApiKey(String apiKey) throws Exception {
    LanguageServiceSettings settings = LanguageServiceSettings
        .newBuilder().setApiKey(apiKey).build();
    LanguageServiceClient client = LanguageServiceClient.create(settings);
    return client;
  }

  @Test
  public void analyzeSentimentTest() throws Exception {
    LanguageServiceClient client = createClientWithApiKey("<FILL IN THE API KEY>");

    // set the GCS Content URI path to the file to be analyzed
    Document doc =
        Document.newBuilder().setContent("123\n456").setType(Type.PLAIN_TEXT).build();
    AnalyzeEntitiesRequest request =
        AnalyzeEntitiesRequest.newBuilder()
            .setDocument(doc)
            .setEncodingType(EncodingType.UTF16)
            .build();

    AnalyzeEntitiesResponse response = client.analyzeEntities(request);

    // Print the response
    for (Entity entity : response.getEntitiesList()) {
      System.out.printf("Entity: %s\n", entity.getName());
      System.out.printf("Salience: %.3f\n", entity.getSalience());
      System.out.println("Metadata: ");
      for (Map.Entry<String, String> entry : entity.getMetadataMap().entrySet()) {
        System.out.printf("%s : %s", entry.getKey(), entry.getValue());
      }
      for (EntityMention mention : entity.getMentionsList()) {
        System.out.printf("Begin offset: %d\n", mention.getText().getBeginOffset());
        System.out.printf("Content: %s\n", mention.getText().getContent());
        System.out.printf("Type: %s\n\n", mention.getType());
      }
    }
  }
}

package com.spring.cohort.assignment.service;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AiServiceTest {

    @Autowired
    AiChatService aiService;

    @Autowired
    RagService ragService;

    @Test
    void testChatClient(){
        String response = aiService.chat("what the salary of Nagraj Hiremath", "1");
        System.out.println(response);
    }

    @Test
    void testGetEmbedding(){
        float[] embeddings = ragService.generateEmbeddings("Hello, my dog is cute");
        System.out.println();
        for(float e: embeddings){
            System.out.print(e);
        }
    }

    @Test
    void testIngestToVector(){
        ragService.ingestHpSupportDocsToVector();
    }

    @Test
    void testSearchDocuments(){
        List<Document> results = ragService.searchHrPolicyDocuments("Spring Boot");
        results.forEach(System.out::println);
    }
}

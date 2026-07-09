package com.spring.cohort.assignment.service;

import com.spring.cohort.assignment.dto.PoemResponse;
import com.spring.cohort.assignment.enums.PoemLanguage;
import com.spring.cohort.assignment.tools.StockTraderTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiChatService {

    private final ChatClient chatClient;

    private final RagService ragService;

    private final ChatMemory chatMemory;

    private final StockTraderTools stockTraderTools;

    public String chat(String message, String userId){


        return chatClient.prompt()
                .advisors(SimpleLoggerAdvisor.builder().build())
                .user(message)
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        userId
                ))
                .call()
                .content();
    }

    public PoemResponse getPoem(String topic, PoemLanguage language) {
        String systemPrompt = """
            You are a sarcastic poet that writes poems in {language}.
            Write a poem specifically about the topic: {topic}.
            """;

        return chatClient.prompt()
                .system(sp -> sp.text(systemPrompt)
                        .params(Map.of("language", language.name(), "topic", topic)))
                // Spring AI automatically injects the schema for PoemResponse here
                .call()
                .entity(PoemResponse.class);
    }

    public String getMyVibePlayList(String message) {

        String systemPrompt = """
                You are an expert Bollywood music recommender.\s
                Below is a list of curated playlists matching the user's requested vibe.
                 Use ONLY this context to recommend the playlist and its tracks.\s
                Be conversational, energetic, and match the tone of their request.

                ---
                CURATED PLAYLIST CONTEXT:
                {context}
                ---
                """;

        List<Document> contextDocuments = ragService.searchPlaylists(message);

        if (contextDocuments.isEmpty()) {
            return "I couldn't find a matching playlist for that specific vibe. Try describing your mood or activities differently!";
        }

        String context = contextDocuments.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));

        return chatClient.prompt()
                .system(sp->sp.text(systemPrompt).params(Map.of("context", context)))
                .user(message)
                .call()
                .content();
    }

    public String getEmployeeHandbookInfo(String message) {
        String systemPrompt = """
                You are an expert HR policy assistant.\s
                Below is a list of curated HR policies matching the user's requested information.
                 Use ONLY this context to recommend the policy and its details.\s
                Be conversational, informative, and match the tone of their request.

                ---
                CURATED HR POLICY CONTEXT:
                {context}
                ---
                """;

        List<Document> contextDocuments = ragService.searchHrPolicyDocuments(message);

        if (contextDocuments.isEmpty()) {
            return "I couldn't find a matching policy for that specific information. Try describing your question differently!";
        }

        String context = contextDocuments.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));

        return chatClient.prompt()
                .system(sp->sp.text(systemPrompt).params(Map.of("context", context)))
                .user(message)
                .call()
                .content();
    }

    public String stockTrader(String message) {
        String systemPrompt = """
                You are a helpful stock trading assistant, use tools u have and answer the queries.
                """;

        return chatClient.prompt()
                .advisors(a -> a.param(
                                ChatMemory.CONVERSATION_ID,
                                1))
                .system(sp->sp.text(systemPrompt))
                .user(message)
                .tools(stockTraderTools)
                .call()
                .content();
    }

    public String hpSupportAgent(String message) {

        String systemPrompt = """
                You are an expert HP support agent.\s
                Below is a list of curated HP support documents matching the user's requested information.
                 Use ONLY this context to recommend the document and its details.\s
                Be conversational, informative, and match the tone of their request.

                ---
                CURATED HP SUPPORT DOCUMENT CONTEXT:
                {context}
                ---
                """;

        List<Document> contextDocuments = ragService.searchHpSupportDocuments(message);

        if (contextDocuments.isEmpty()) {
            return "I couldn't find a matching document for that specific information. Try describing your question differently!";
        }

        String context = contextDocuments.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));

        return chatClient.prompt()
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        1))
                .system(sp->sp.text(systemPrompt).params(Map.of("context", context)))
                .user(message)
                .call()
                .content();
    }
}

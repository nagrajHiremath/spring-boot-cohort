package com.spring.cohort.assignment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RagService {

    private final EmbeddingModel embeddingModel;

    private final VectorStore vectorStore;

    @Value("classpath:hr-policy.pdf")
    Resource hrPolicyPdf;

    @Value("classpath:hp-pavillion-guide.pdf")
    Resource hpSupportPdf;

    public float[] generateEmbeddings(String texts) {
        return embeddingModel.embed(texts);
    }

    public void ingestHrPolicyToVector() {

        PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(hrPolicyPdf);
        List<Document> pages = pagePdfDocumentReader.get();

        TokenTextSplitter tokenTextSplitter = TokenTextSplitter.builder()
                .withChunkSize(200)
                .build();

        List<Document> splitDocuments = tokenTextSplitter.split(pages);

        vectorStore.add(splitDocuments);
    }

    public void ingestHpSupportDocsToVector() {

        PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(hpSupportPdf);
        List<Document> pages = pagePdfDocumentReader.get();

        TokenTextSplitter tokenTextSplitter = TokenTextSplitter.builder()
                .withChunkSize(200)
                .build();

        List<Document> splitDocuments = tokenTextSplitter.split(pages);

        vectorStore.add(splitDocuments);
    }

    public void ingestPlaylistInfoToVector(){
        var playlist1 = Map.of(
                "name", "Sufi & Spiritual Transience",
                "summary", "Deeply spiritual, transcendental, and soul-cleansing music. Dominated by qawwali rhythms, harmoniums, and towering vocal crescendos.",
                "moods", "peaceful, divine, reflective, emotional",
                "scenarios", "introspection, finding peace, late-night focus",
                "songs", "Kun Faya Kun by A.R. Rahman, Khwaja Mere Khwaja by A.R. Rahman, Arziyan by Javed Ali",
                "tags", "sufi, devotional, peaceful, calm, rahman classic, soothing"
        );

        var playlist2 = Map.of(
                "name", "The Heartbreak Monsoon",
                "summary", "Heavy, melancholic, and deeply emotional acoustic ballads. Captures the intense pain of unrequited love and separation with crying violins and painfully raw vocals.",
                "moods", "sad, heartbroken, wistful, melancholic",
                "scenarios", "driving in the rain, staring out a window, quiet loneliness",
                "songs", "Agar Tum Saath Ho by Arijit Singh, Channa Mereya by Arijit Singh, Vida Karo by A.R. Rahman",
                "tags", "sad songs, heartbreak, arijit crying, rain vibe, emotional, slow pop"
        );

        var playlist3 = Map.of(
                "name", "Adrenaline & Cinematic Fury",
                "summary", "High-octane, aggressive, and modern electronic-rock landscapes. Packed with heavily distorted synths, trap hi-hats, massive bass drops, and high-energy vocal delivery to spike your adrenaline.",
                "moods", "confident, hyped, aggressive, rebellious",
                "scenarios", "gym workout, high energy running, heavy gaming",
                "songs", "Gehra Hua by Arijit Singh & Shashwat Sachdev (Dhurandhar), Saadda Haq by Mohit Chauhan, Sher Khul Gaye by Vishal-Shekhar",
                "tags", "gym workout, hype, high energy, electronic rock, shashwat sachdev, dhurandhar ost"
        );

        var playList = List.of(playlist1, playlist2, playlist3);

        var documents = playList.stream().map(p -> {
            String embedText = String.format(
                    "PLAYLIST: %s\n" +
                            "VIBE DESCRIPTION: %s\n" +
                            "MOODS: %s\n" +
                            "IDEAL FOR: %s\n" +
                            "ARTISTS AND TRACKS: %s\n" +
                            "KEYWORDS: %s",
                    p.get("name"),
                    p.get("summary"),
                    p.get("moods"),
                    p.get("scenarios"),
                    p.get("songs"),
                    p.get("tags")
            );
            Map<String, Object> metadata = Map.of("playlist_name", p.get("name"),
                    "Song List", p.get("songs"));
            return new Document(embedText, metadata);
        }).toList();

        vectorStore.add(documents);
    }

    public List<Document> searchHrPolicyDocuments(String query) {
        return vectorStore.similaritySearch(SearchRequest.builder()
                        .query(query)
                        .topK(3)
                        .filterExpression("file_name == 'hr-policy.pdf'")
                .similarityThreshold(0.5)
                .build());
    }

    public List<Document> searchPlaylists(String query) {
        return vectorStore.similaritySearch(SearchRequest.builder()
                .query(query)
                .topK(3)
                .similarityThreshold(0.5)
                .build());
    }

    public List<Document> searchHpSupportDocuments(String query) {
        return vectorStore.similaritySearch(SearchRequest.builder()
                .query(query)
                .topK(5)
                .filterExpression("file_name == 'hp-pavillion-guide.pdf'")
                .similarityThreshold(0.5)
                .build());
    }


}

package com.spring.cohort.assignment.controller;


import com.spring.cohort.assignment.dto.PoemResponse;
import com.spring.cohort.assignment.enums.PoemLanguage;
import com.spring.cohort.assignment.service.AiChatService;
import lombok.RequiredArgsConstructor;
import org.apache.fontbox.ttf.model.Language;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class AiChatController {

    private final AiChatService aiChatService;

    @GetMapping("/poem/{topic}/{language}")
    public ResponseEntity<PoemResponse> getPoem(@PathVariable String topic, @PathVariable PoemLanguage language) {
        return ResponseEntity.ok(aiChatService.getPoem(topic, language));
    }

    @PostMapping("/vibe-playlist")
    public ResponseEntity<String> getMyVibePlayList(@RequestBody String message){
        return ResponseEntity.ok(aiChatService.getMyVibePlayList(message));
    }

    @PostMapping("/employee-handbook-bot")
    public ResponseEntity<String> getEmployeeHandbookInfo(@RequestBody String message){
        return ResponseEntity.ok(aiChatService.getEmployeeHandbookInfo(message));
    }

    @PostMapping("/id/{userId}")
    public ResponseEntity<String> chat(@RequestBody String message, @PathVariable String userId){
        return ResponseEntity.ok(aiChatService.chat(message, userId));
    }

    @PostMapping("/stock-trader")
    public ResponseEntity<String> stockTrader(@RequestBody String message){
        return ResponseEntity.ok(aiChatService.stockTrader(message));
    }

    @PostMapping("/hp-support-agent")
    public ResponseEntity<String> hpSupportAgent(@RequestBody String message){
        return ResponseEntity.ok(aiChatService.hpSupportAgent(message));
    }
}

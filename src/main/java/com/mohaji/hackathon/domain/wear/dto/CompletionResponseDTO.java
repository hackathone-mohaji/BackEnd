package com.mohaji.hackathon.domain.wear.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompletionResponseDTO {

    private String id;
    private String object;
    private Long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    private String systemFingerprint;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Choice {
        private Integer index;
        private Message message;
        private Object logprobs;
        private String finishReason;
        @Getter
        @Setter
        @NoArgsConstructor
        public static class Message {
            private String role;
            private String content;
            private String refusal;
        }
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Usage {
        private Integer promptTokens;

        private Integer completionTokens;

        private Integer totalTokens;

        private PromptTokenDetails promptTokensDetails;

        private CompletionTokenDetails completionTokensDetails;

        @Getter
        @Setter
        @NoArgsConstructor
        public static class PromptTokenDetails {
            private Integer cachedTokens;

            private Integer audioTokens;
        }
        @Getter
        @Setter
        @NoArgsConstructor
        public static class CompletionTokenDetails {

            private Integer reasoningTokens;

            private Integer audioTokens;

            private Integer acceptedPredictionTokens;

            private Integer rejectedPredictionTokens;
        }
    }
}
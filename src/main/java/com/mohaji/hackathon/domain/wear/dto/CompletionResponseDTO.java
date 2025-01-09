package com.mohaji.hackathon.domain.wear.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CompletionResponseDTO {
    private String id;
    private String object;
    private long created;
    private String model;
    private Choice[] choices;
    private Usage usage;

    @Data
    public static class Choice {
        private int index;
        private Message message;
        private String refusal;

        @Data
        public static class Message {
            private String role;
            private String content;
        }
    }

    @Data
    public static class Usage {
        @JsonProperty("prompt_tokens")
        private int promptTokens;

        @JsonProperty("completion_tokens")
        private int completionTokens;

        @JsonProperty("total_tokens")
        private int totalTokens;

        @JsonProperty("prompt_tokens_details")
        private TokenDetails promptTokensDetails;

        @JsonProperty("completion_tokens_details")
        private TokenDetails completionTokensDetails;

        @Data
        public static class TokenDetails {
            @JsonProperty("cached_tokens")
            private int cachedTokens;

            @JsonProperty("audio_tokens")
            private int audioTokens;

            @JsonProperty("accepted_prediction_tokens")
            private int acceptedPredictionTokens;

            @JsonProperty("rejected_prediction_tokens")
            private int rejectedPredictionTokens;
        }
    }
}
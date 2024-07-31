package com.TransferApp.MoneyTransfer.exception.responses;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Builder
@Data
@RequiredArgsConstructor
public class ValidationFailedResponse {
    private final List<ViolationErrors> violations = new ArrayList<>();
    private final LocalDateTime  timestamp;
    private final String message;
}

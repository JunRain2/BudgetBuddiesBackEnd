package com.prography.budgetbuddiesbackend.report.domain.expense.controller;

import com.prography.budgetbuddiesbackend.common.annotation.CurrentUserId;
import com.prography.budgetbuddiesbackend.common.response.ApiResponse;
import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.RegisterExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.UpdateExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.ExpenseId;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.ExpenseUseCase;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.command.RegisterExpenseCommand;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.command.UpdateExpenseCommand;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseUseCase expenseFacadeService;
    private final ExpenseCommandMapper mapper;

    // 지출 등록
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerExpense(
        @RequestBody @Valid RegisterExpenseRequest request,
        @CurrentUserId @RequestParam UUID userId) {

        UserId userKey = UserId.of(userId);
        RegisterExpenseCommand command = mapper.requestToRegisterExpenseCommand(request);

        expenseFacadeService.registerExpense(command, userKey);
        return ResponseEntity.ok(ApiResponse.success());
    }

    // 지출 수정
    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> updateExpense(
        @RequestBody @Valid UpdateExpenseRequest request,
        @CurrentUserId @RequestParam UUID userId) {

        UserId userKey = UserId.of(userId);
        UpdateExpenseCommand command = mapper.requestToUpdateExpenseCommand(request);

        expenseFacadeService.updateExpense(command, userKey);
        return ResponseEntity.ok(ApiResponse.success());
    }

    // 지출 삭제
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<ApiResponse<Void>> deleteExpense(@PathVariable UUID expenseId,
        @CurrentUserId @RequestParam UUID userId) {

        ExpenseId expenseKey = ExpenseId.of(expenseId);
        UserId userKey = UserId.of(userId);

        expenseFacadeService.deleteExpense(expenseKey, userKey);
        return ResponseEntity.ok(ApiResponse.success());
    }
} 
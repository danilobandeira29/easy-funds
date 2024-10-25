package com.github.danilobandeira29.easy_funds.controllers;

import com.github.danilobandeira29.easy_funds.services.TransferEnum;
import com.github.danilobandeira29.easy_funds.services.TransferService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TransferController {
    @Autowired
    TransferService transferService;

    private final Map<String, HttpStatus> errorsMap = Map.of(
            TransferEnum.TRANSFER_PAYEE_NOT_FOUND.toString(), HttpStatus.NOT_FOUND,
            TransferEnum.TRANSFER_PAYER_NOT_FOUND.toString(), HttpStatus.NOT_FOUND,
            TransferEnum.TRANSFER_SELF_TRANSFER.toString(), HttpStatus.BAD_REQUEST,
            TransferEnum.TRANSFER_PAYER_INSUFFICIENT_BALANCE.toString(), HttpStatus.BAD_REQUEST,
            TransferEnum.TRANSFER_UNAUTHORIZED.toString(), HttpStatus.BAD_REQUEST,
            TransferEnum.TRANSFER_DEPOSIT_NEGATIVE_VALUE_FOR_PAYEE.toString(), HttpStatus.BAD_REQUEST,
            TransferEnum.TRANSFER_AUTHORIZATION.toString(), HttpStatus.INTERNAL_SERVER_ERROR,
            TransferEnum.TRANSFER_AUTHORIZATION_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR
    );
    // todo
    // [ ] publish transf event
    @PostMapping("/transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "transfer done with success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDto.class),
                    examples = @ExampleObject(name = "TRANSFER_SUCCESS", value = "{\"status\":\"success\",\"data\":{\"amount\":10,\"payerId\":\"1c0a69ff-bf1d-474d-a4ed-a9247e16ba0d\",\"payeeId\":\"63015f60-9506-4b5d-ba29-8d7e944d634e\"},\"error\":null,\"code\":null}"))),
            @ApiResponse(responseCode = "404", description = "payer or payee not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDto.class),
                    examples = {
                            @ExampleObject(name = "TRANSFER_PAYER_NOT_FOUND", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"payer not found\",\"code\":\"TRANSFER_PAYER_NOT_FOUND\"}"),
                            @ExampleObject(name = "TRANSFER_PAYEE_NOT_FOUND", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"payee not found\",\"code\":\"TRANSFER_PAYEE_NOT_FOUND\"}")
                    })),
            @ApiResponse(responseCode = "400", description = "insufficient balance",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDto.class),
                            examples = {
                                    @ExampleObject(name = "TRANSFER_SELF_TRANSFER", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"cannot transfer to yourself\",\"code\":\"TRANSFER_SELF_TRANSFER\"}"),
                                    @ExampleObject(name = "TRANSFER_PAYER_INSUFFICIENT_BALANCE", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"insufficient balance\",\"code\":\"TRANSFER_PAYER_INSUFFICIENT_BALANCE\"}"),
                                    @ExampleObject(name = "TRANSFER_DEPOSIT_NEGATIVE_VALUE_FOR_PAYEE", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"cannot deposit negative value\",\"code\":\"TRANSFER_DEPOSIT_NEGATIVE_VALUE_FOR_PAYEE\"}"),
                                    @ExampleObject(name = "TRANSFER_UNAUTHORIZED", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"transfer unauthorized\",\"code\":\"TRANSFER_UNAUTHORIZED\"}")
                            })),
            @ApiResponse(responseCode = "500", description = "authorization/internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDto.class),
                    examples = {
                            @ExampleObject(name = "TRANSFER_AUTHORIZATION", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"internal server error\",\"code\":\"TRANSFER_AUTHORIZATION\"}"),
                            @ExampleObject(name = "TRANSFER_AUTHORIZATION_ERROR", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"internal server error\",\"code\":\"TRANSFER_AUTHORIZATION_ERROR\"}"),
                            @ExampleObject(name = "TRANSFER_PERSISTENCE_ERROR", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"internal server error\",\"code\":\"TRANSFER_PERSISTENCE_ERROR\"}")
                    }))
    })
    public ResponseEntity<?> transfer(@Valid @RequestBody TransferRequestDto transferDto) {
        var resp = transferService.execute(transferDto);
        if (resp.isSuccess()) {
            return ResponseEntity.ok().body(resp);
        }
        HttpStatus httpStatus = errorsMap.getOrDefault(resp.getCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(httpStatus).body(resp);
    }
}

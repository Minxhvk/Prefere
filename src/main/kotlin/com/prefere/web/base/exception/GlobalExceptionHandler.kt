package com.prefere.web.base.exception;

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@RestControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(BizException::class)
    fun handleBizException(e: BizException): ResponseEntity<ExceptionResult> {

        return ResponseEntity.status(e.baseResponseCode.statusCode)
            .body(ExceptionResult(e.baseResponseCode.statusCode, e.baseResponseCode.message))
    }

    // TODO ResponseEntityExceptionHandler Excpetion 더 잡기
    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ExceptionResult(HttpStatus.NOT_FOUND, "HttpRequestMethodNotSupportedException occurred"))
    }

}
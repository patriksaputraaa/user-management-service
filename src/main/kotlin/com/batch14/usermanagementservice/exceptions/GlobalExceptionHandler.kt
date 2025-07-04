package com.batch14.usermanagementservice.exceptions

import com.batch14.usermanagementservice.domain.dto.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleArgumentNotValidException(
        exception: MethodArgumentNotValidException
    ): ResponseEntity<BaseResponse<Any?>> {
        val errors = mutableListOf<String?>()
        exception.bindingResult.fieldErrors.forEach { // mendapatkan message error dari binding result di dto
            errors.add(it.defaultMessage)
        }
        return ResponseEntity(
            BaseResponse(
                data = errors
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(
        exception: CustomException
    ): ResponseEntity<BaseResponse<Any?>>{
        return ResponseEntity(
            BaseResponse(
                message = exception.exceptionMessage,
                status = "T",
                error = exception.data
            ),
            HttpStatus.valueOf(exception.statusCode) //exception fleksibel
        )
    }
}
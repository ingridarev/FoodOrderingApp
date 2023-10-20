package lt.techin.FoodOrderApp.api;

import lt.techin.FoodOrderApp.api.dto.ErrorDto;
import lt.techin.FoodOrderApp.api.dto.ErrorFieldDto;
import lt.techin.FoodOrderApp.api.dto.Mapper.ErrorFieldMapper;
import lt.techin.FoodOrderApp.exception.FoodOrderAppServiceDisabledException;
import lt.techin.FoodOrderApp.exception.FoodOrderAppValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(SQLException.class)
    public String handleSQLException(HttpServletRequest request, Exception ex) {
        logger.info("SQLException Occurred:: URL=" + request.getRequestURL());
        return "database_error";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occurred")
    @ExceptionHandler(IOException.class)
    public void handleIOException() {
        logger.error("IOException handler executed");
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorDto> handleDataAccessException(HttpServletRequest request, DataAccessException dataAccessException) {
        logger.error("DataAccessException: {}. Cause?: {}",
                dataAccessException.getMessage(), dataAccessException.getMostSpecificCause().getMessage());

        var errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var errorFields = List.of(
                new ErrorFieldDto("sql", dataAccessException.getMostSpecificCause().getMessage(), null)
        );
        var errorDto = new ErrorDto(request.getRequestURL().toString(),
                errorFields,
                dataAccessException.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
                request.getRequestURL().toString(),
                LocalDateTime.now());
        return ResponseEntity.internalServerError().body(errorDto);
    }

    @ExceptionHandler(FoodOrderAppValidationException.class)
    public ResponseEntity<ErrorDto> handleBloggingValidationException(HttpServletRequest request, FoodOrderAppValidationException foodOrderAppValidationException) {
        logger.error("FoodOrderAppValidationException: {}, for field: {}", foodOrderAppValidationException.getMessage(), foodOrderAppValidationException.getField());

        var errorStatus = HttpStatus.BAD_REQUEST;

        var errorFields = List.of(
                new ErrorFieldDto(foodOrderAppValidationException.getField(), foodOrderAppValidationException.getError(), foodOrderAppValidationException.getRejectedValue())
        );

        var errorDto = new ErrorDto(request.getRequestURL().toString(),
                errorFields,
                foodOrderAppValidationException.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
                request.getRequestURL().toString(),
                LocalDateTime.now());
        return ResponseEntity.badRequest().body(errorDto);
    }


    @ExceptionHandler(FoodOrderAppServiceDisabledException.class)
    public ResponseEntity<Void> handleBloggingServiceDisabledException(HttpServletRequest request, FoodOrderAppServiceDisabledException foodOrderAppServiceDisabledException) {
        logger.error("FoodOrderAppServiceDisabledException: {}", foodOrderAppServiceDisabledException.getMessage());

        var errorStatus = HttpStatus.SERVICE_UNAVAILABLE;

        return new ResponseEntity<>(errorStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException notValidException) {
        logger.error("MethodArgumentNotValidException: {}", notValidException.getMessage());

        var errorStatus = HttpStatus.BAD_REQUEST;

        var errorFields = notValidException.getBindingResult()
                .getAllErrors().stream()
                .map(ErrorFieldMapper::toErrorFieldDto)
                .collect(toList());

        var errorDto = new ErrorDto(request.getRequestURL().toString(),
                errorFields,
                notValidException.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
                request.getRequestURL().toString(),
                LocalDateTime.now());
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "We do not support this")
    @ExceptionHandler(HttpMediaTypeException.class)
    public void handleHttpMediaTypeException(HttpMediaTypeException mediaTypeException) {
        logger.error("Not supported request resulted in HttpMediaTypeException: {}", mediaTypeException.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something really bad happened")
    @ExceptionHandler(Exception.class)
    public void handleAllExceptions(Exception exception) {
        logger.error("All Exceptions handler: {}", exception.getMessage());
    }

}
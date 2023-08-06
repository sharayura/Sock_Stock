package pro.sky.test_skypro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.test_skypro.dto.SocksDto;
import pro.sky.test_skypro.service.SocksService;

/**
 * Класс методов REST API приложения для автоматизации учёта носков на складе магазина
 * @author Sharapov Yuri
 */
@RestController
@RequestMapping("/api/socks")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @Operation(summary = "Возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса.",
            parameters = {
                    @Parameter(
                            name = "color",
                            description = "цвет носков, строка",
                            in = ParameterIn.QUERY,
                            required = true,
                            example = "black"
                    ),
                    @Parameter(
                            name = "operation",
                            description = "оператор сравнения значения количества хлопка в составе носков, одно значение из: moreThan, lessThan, equal",
                            in = ParameterIn.QUERY,
                            required = true,
                            example = "moreThan"
                    ),
                    @Parameter(
                            name = "cottonPart",
                            description = "значение процента хлопка в составе носков из сравнения",
                            in = ParameterIn.QUERY,
                            required = true,
                            example = "50"
                    )
            }
            )
    @GetMapping()
    public ResponseEntity<Long> getSocksQuantity(@RequestParam String color,
                                                 @RequestParam String operation,
                                                 @RequestParam byte cottonPart) {
        Long result = socksService.getSocksQuantity(color, operation, cottonPart);
        return (result == null) ? ResponseEntity.status(HttpStatus.BAD_REQUEST).build() : ResponseEntity.ok(result);
    }

    @Operation(summary = "Регистрация прихода носков на склад",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SocksDto.class)
                    )
            ))
    @PostMapping("/income")
    public ResponseEntity<?> incomeSocks(@RequestBody SocksDto socksDto) {
        return (socksService.changeSocksQuantity(socksDto, true)) ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(summary = "Регистрация отпуска носков со склада",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SocksDto.class)
                    )
            ))
    @PostMapping("/outcome")
    public ResponseEntity<?> outcomeSocks(@RequestBody SocksDto socksDto) {
        return (socksService.changeSocksQuantity(socksDto, false)) ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

package pro.sky.test_skypro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.test_skypro.dto.SocksDto;
import pro.sky.test_skypro.service.SocksService;


@RestController
@RequestMapping("/api/socks")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @GetMapping()
    public ResponseEntity<Long> getSocksQuantity(@RequestParam String color,
                                                 @RequestParam String operation,
                                                 @RequestParam byte cottonPart) {
        Long result = socksService.getSocksQuantity(color, operation, cottonPart);
        return (result == null)? ResponseEntity.status(HttpStatus.BAD_REQUEST).build() : ResponseEntity.ok(result);
    }

    @PostMapping("/income")
    public ResponseEntity<?> incomeSocks(@RequestBody SocksDto socksDto) {
        return (socksService.changeSocksQuantity(socksDto, true)) ? ResponseEntity.status(HttpStatus.OK).build()
            : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/outcome")
    public ResponseEntity<?> outcomeSocks(@RequestBody SocksDto socksDto) {
        return (socksService.changeSocksQuantity(socksDto, false)) ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

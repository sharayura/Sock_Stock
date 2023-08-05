package pro.sky.test_skypro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}

package pro.sky.test_skypro.service;

import org.springframework.stereotype.Service;
import pro.sky.test_skypro.repository.SocksRepository;

@Service
public class SocksService {
    private final SocksRepository socksRepository;

    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    public Long getSocksQuantity(String color, String operation, byte cottonPart) {
        if (cottonPart < 0 || cottonPart > 100) {
            return null;
        }
        byte cottonPartL;
        byte cottonPartH;
        switch (operation) {
            case "moreThan" -> {
                cottonPartL = (byte) (cottonPart + 1);
                cottonPartH = (byte) 100;
            }
            case "lessThan" -> {
                cottonPartL = (byte) 0;
                cottonPartH = (byte) (cottonPart - 1);
            }
            case "equal" -> {
                cottonPartL = cottonPart;
                cottonPartH = cottonPart;
            }
            default -> {
                return null;
            }
        }
        ;
        return socksRepository.getSocksQuantity(color, cottonPartL, cottonPartH).orElse(0L);
    }
}

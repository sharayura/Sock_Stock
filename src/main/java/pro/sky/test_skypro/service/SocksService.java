package pro.sky.test_skypro.service;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.test_skypro.dto.SocksDto;
import pro.sky.test_skypro.entity.SocksEntity;
import pro.sky.test_skypro.repository.SocksRepository;

/**
 * Класс сервисных методов приложения для автоматизации учёта носков на складе магазина
 * @author Sharapov Yuri
 */
@Service
public class SocksService {
    private final SocksRepository socksRepository;
    private final String MORE_THEN = "moreThan";
    private final String LESS_THEN = "lessThan";
    private final String EQUAL = "equal";

    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    /**
     * Метод получения из БД количества носков заданного типа, находящихся на складе
     * @param color цвет носков, строка
     * @param operation оператор сравнения значения количества хлопка в составе носков, одно значение из: moreThan, lessThan, equal;
     *                  в метод репозитория передаются два граничных значения cottonPart, в пределах которых идет поиск
     * @param cottonPart значение процента хлопка в составе носков из сравнения
     * @return null если запрос составлен неправильно либо общее количество носков на складе, соответствующих переданным в параметрах критериям запроса
     */
    public Long getSocksQuantity(String color, String operation, byte cottonPart) {
        if (cottonPart < 0 || cottonPart > 100) {
            return null;
        }
        byte cottonPartL;
        byte cottonPartH;
        switch (operation) {
            case MORE_THEN -> {
                cottonPartL = (byte) (cottonPart + 1);
                cottonPartH = (byte) 100;
            }
            case LESS_THEN -> {
                cottonPartL = (byte) 0;
                cottonPartH = (byte) (cottonPart - 1);
            }
            case EQUAL -> {
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

    /**
     * Метод изменения в БД количества носков заданного типа
     * @param socksDto объект для определения типа и количества носков
     * @param income флаг, определяющий приход (true) или отпуск (false) носков
     * @return true если изменения произошли
     */
    @Transactional
    public boolean changeSocksQuantity(SocksDto socksDto, boolean income) {
        String color = socksDto.getColor();
        byte cottonPart = socksDto.getCottonPart();
        Long quantity = socksDto.getQuantity();
        if (color == null || cottonPart < 0 || quantity == null || quantity < 0) {
            return false;
        }

        SocksEntity exampleSocks = new SocksEntity();
        exampleSocks.setColor(color);
        exampleSocks.setCottonPart(cottonPart);
        SocksEntity dbSocks = socksRepository.findOne(Example.of(exampleSocks)).orElse(exampleSocks);

        if (income) {
            dbSocks.setQuantity(dbSocks.getQuantity() == null ? quantity : dbSocks.getQuantity() + quantity);
        } else {
            if (dbSocks.getQuantity() < quantity) return false;
            dbSocks.setQuantity(dbSocks.getQuantity() == null ? quantity : dbSocks.getQuantity() - quantity);
        }
        socksRepository.save(dbSocks);
        return true;
    }
}

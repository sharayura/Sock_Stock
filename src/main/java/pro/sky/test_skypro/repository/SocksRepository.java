package pro.sky.test_skypro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.test_skypro.entity.SocksEntity;

import java.util.Optional;

/**
 * Интерфейс репозитория для работы с БД приложения для автоматизации учёта носков на складе магазина
 * @author Sharapov Yuri
 */
@Repository
public interface SocksRepository extends JpaRepository<SocksEntity, Long> {
    String GET_SOCKS_QUANTITY_QUERY =
      "select sum(s.quantity) from SocksEntity as s where s.color = :color and s.cottonPart between :cottonPartL and :cottonPartH";
    @Query(value = GET_SOCKS_QUANTITY_QUERY)
    Optional<Long> getSocksQuantity(String color, byte cottonPartL, byte cottonPartH);

}

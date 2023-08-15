package pro.sky.test_skypro.dto;

/**
 * DATA-Класс для передачи типа и количества носков
 * @author Sharapov Yuri
 */
public class SocksDto {
    private String color;
    private byte cottonPart;
    private Long quantity;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public byte getCottonPart() {
        return cottonPart;
    }

    public void setCottonPart(byte cottonPart) {
        this.cottonPart = cottonPart;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}

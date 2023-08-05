package pro.sky.test_skypro.entity;

import javax.persistence.*;

@Entity
@Table(name = "socks", uniqueConstraints =
        { @UniqueConstraint(columnNames = { "color", "cotton_part" })
        })
public class SocksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String color;

    @Column(name = "cotton_part", nullable = false)
    private byte cottonPart;

    @Column(nullable = false)
    private Long quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

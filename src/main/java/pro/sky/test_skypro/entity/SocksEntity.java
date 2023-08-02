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

    @Column(nullable = false)
    private byte cotton_part;

    @Column(nullable = false)
    private long quantity;

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

    public byte getCotton_part() {
        return cotton_part;
    }

    public void setCotton_part(byte cotton_part) {
        this.cotton_part = cotton_part;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}

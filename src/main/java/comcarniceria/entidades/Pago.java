package comcarniceria.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private Date fechaPago;
    private Double total;
    private String metodoPago;
    private String numeroTarjeta;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

}

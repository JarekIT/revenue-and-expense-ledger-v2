package pl.jarekit.rael.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;


@Entity
@Table
public @Data
class Invoice {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_client_seller")
    Client clientSeller;

    @ManyToOne
    @JoinColumn(name = "id_client_buyer")
    Client clientBuyer;

    @Column
    private String type;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "date_sale")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateSale;

    @Column(name = "date_create")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreate;

    @Column
    private BigDecimal amount;

    @Column
    private String description;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate period;

    @Column
    private int category;

    @Column
    private String comment;

    @Transient
    private BigDecimal[] amountType = new BigDecimal[4];

    @ManyToOne
    @JoinColumn(name = "id_client_partner")
    private Client clientPartner;

    public Invoice() {
    }


    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", clientSeller=" + clientSeller +
                ", clientBuyer=" + clientBuyer +
                ", type='" + type + '\'' +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", dateSale=" + dateSale +
                ", dateCreate=" + dateCreate +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", period=" + period +
                ", category=" + category +
                ", comment='" + comment + '\'' +
                '}' +
                "\n";
    }

}




package com.example.userLogin.appRun;

import com.example.userLogin.appuser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    private Long id;

    private String customer;

    private String provider;

    private String aDestination;

    private String fDestination;

    @ManyToOne
    @JoinColumn(
            name = "app_user_id"
    )
    private AppUser appUser;

    public Order(String customer,
                  String provider,
                  String aDestination,
                  String fDestination,
                 AppUser appUser){
        this.customer = customer;
        this.provider = provider;
        this.aDestination = aDestination;
        this.fDestination = fDestination;
        this.appUser = appUser;
    }
}

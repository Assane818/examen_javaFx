package com.asn.data.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(exclude = "dettes", callSuper = true)
@Entity
@Table(name = "clients")
@EqualsAndHashCode(callSuper = false)
public class Client extends AbstractEntity {
    @Column(length = 25, unique = true, nullable = false)
    private String surname;
    @Column(length = 12, unique = true, nullable = false)
    private String phone;
    @Column(length = 50, nullable = false)
    private String address;
    @OneToMany(mappedBy = "client")
    private List<Commande> commandes = new ArrayList<>();



    public Client() {
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }
    

}

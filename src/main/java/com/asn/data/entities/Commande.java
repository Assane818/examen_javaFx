package com.asn.data.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"details", "client"}, callSuper = true)
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "commandes")
public class Commande extends AbstractEntity {
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false)
    private double montant;
    @Column(nullable = false, name = "montantVerser")
    private double montantVerser;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private Client client;
    @OneToMany(mappedBy = "commande", cascade = CascadeType.PERSIST)
    private List<Detail> details = new ArrayList<>();

    public Commande() {
        date = LocalDateTime.now();
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }
    
}

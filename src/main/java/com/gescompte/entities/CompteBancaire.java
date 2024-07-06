package com.gescompte.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gescompte.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER)
@JsonIgnoreProperties({"hibernateLazyInitializer"})

public abstract class CompteBancaire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private double balance;
    @Column(nullable = false)
    private String numCompte;
    @Column(nullable = false)
    private String devis = "CFA";
    @Column(nullable = false)
    private AccountStatus status;
    @Column(nullable = false)
    private Date createdAt = new Date();

    @Column(nullable = false)
    @ManyToOne
    private Client client;

    @JsonBackReference
    @OneToMany(mappedBy = "compte")
    Collection<Operations> operations = new ArrayList<>();
}

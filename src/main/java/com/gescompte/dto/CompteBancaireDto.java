package com.gescompte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompteBancaireDto {
    private String devis;
    private double balance;
    private double tauxInteret;
    private double decouvert;
    private long clientId;
}

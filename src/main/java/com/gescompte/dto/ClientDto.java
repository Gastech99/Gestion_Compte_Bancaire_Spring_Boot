package com.gescompte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String telephone;
    private String email;
    private String addresse;
}

package com.psicare.psychology.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString; // Importante

@Data
@Entity
@Table(name = "prontuarios")
public class ProntuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String queixaPrincipal;
    private String historicoFamiliar;
    private String observacoesIniciais;
    private String anotacoesGerais;

    @OneToOne
    @JoinColumn(name = "paciente_id")
    @JsonIgnore // Evita loop no JSON
    @ToString.Exclude // <--- OBRIGATÓRIO: Evita loop no Console
    private PacienteModel paciente;
}
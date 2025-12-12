package com.psicare.psychology.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.psicare.psychology.enums.Agendamento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString; // Importante

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "agendamentos")
public class AgendamentoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    private Agendamento status;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @JsonIgnore // Evita loop no JSON (envio para o front)
    @ToString.Exclude // <--- OBRIGATÓRIO: Evita loop no Console (erro 500)
    private PacienteModel paciente;
}
package com.psicare.psychology.services;

import com.psicare.psychology.dtos.CadastroRecordDto;
import com.psicare.psychology.models.AgendamentoModel;
import com.psicare.psychology.models.PacienteModel;
import com.psicare.psychology.models.ProntuarioModel;
import com.psicare.psychology.repositorys.AgendamentoRepository;
import com.psicare.psychology.repositorys.PacienteRepository;
import com.psicare.psychology.repositorys.ProntuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired private PacienteRepository pacienteRepository;
    @Autowired private ProntuarioRepository prontuarioRepository;
    @Autowired private AgendamentoRepository agendamentoRepository;

    @Transactional
    public PacienteModel cadastrarCompleto(CadastroRecordDto dto){
        // 1. Criar e Salvar Paciente
        PacienteModel pacienteModel = new PacienteModel();
        pacienteModel.setNome(dto.nome());
        pacienteModel.setTelefone(dto.telefone());
        pacienteModel.setEmail(dto.email());
        pacienteModel.setDataNascimento(dto.dataNascimento());

        // CORREÇÃO: Uso direto do enum. Remove a conversão que causava erro 500.
        pacienteModel.setStatus(dto.status());

        pacienteModel.setFrequencia(dto.frequencia());

        pacienteModel = pacienteRepository.save(pacienteModel);

        // 2. Criar e Salvar o Prontuário
        ProntuarioModel prontuarioModel = new ProntuarioModel();
        prontuarioModel.setQueixaPrincipal(dto.queixaPrincipal());
        prontuarioModel.setHistoricoFamiliar(dto.historicoFamiliar());
        prontuarioModel.setObservacoesIniciais(dto.observacoesIniciais());
        prontuarioModel.setAnotacoesGerais(dto.anotacoesGerais());
        prontuarioModel.setPaciente(pacienteModel);

        prontuarioRepository.save(prontuarioModel);

        // 3. Criar Agendamento (CORREÇÃO: Agora salva no banco e vincula corretamente)
        if (dto.dataSessao() != null && dto.horarioSessao() != null){
            AgendamentoModel agendamentoModel = new AgendamentoModel();
            agendamentoModel.setData(dto.dataSessao());
            agendamentoModel.setHora(dto.horarioSessao()); // Faltava setar a hora
            agendamentoModel.setPaciente(pacienteModel);   // Faltava vincular ao paciente

            // Defina um status padrão se necessário, ex: Agendamento.CONFIRMADO
            // agendamentoModel.setStatus(Agendamento.CONFIRMADO);

            agendamentoRepository.save(agendamentoModel); // Faltava salvar!
        }

        return pacienteModel;
    }

    // 2- Listar Todos
    public List<PacienteModel> listarTodos(){
        return pacienteRepository.findAll();
    }

    // 3 - Buscar por ID
    public Optional<PacienteModel> buscarPorId(Long id){
        return pacienteRepository.findById(id);
    }

    // 4 - Deletar
    @Transactional
    public void deletar(PacienteModel paciente){
        pacienteRepository.delete(paciente);
    }

    // 5- Atualizar (PUT)
    @Transactional
    public PacienteModel atualizar(PacienteModel pacienteExistente, CadastroRecordDto dto){
        pacienteExistente.setNome(dto.nome());
        pacienteExistente.setTelefone(dto.telefone());
        pacienteExistente.setEmail(dto.email());
        pacienteExistente.setDataNascimento(dto.dataNascimento());
        pacienteExistente.setStatus(dto.status());
        pacienteExistente.setFrequencia(dto.frequencia()); // Faltava atualizar frequência

        ProntuarioModel prontuarioModel = pacienteExistente.getProntuario();
        if (prontuarioModel == null){
            prontuarioModel = new ProntuarioModel();
            prontuarioModel.setPaciente(pacienteExistente);
        }

        prontuarioModel.setQueixaPrincipal(dto.queixaPrincipal());
        prontuarioModel.setHistoricoFamiliar(dto.historicoFamiliar());
        prontuarioModel.setObservacoesIniciais(dto.observacoesIniciais());
        prontuarioModel.setAnotacoesGerais(dto.anotacoesGerais());

        prontuarioRepository.save(prontuarioModel);
        return pacienteRepository.save(pacienteExistente);
    }

    // 6 - Atualização Parcial (PATCH)
    @Transactional
    public PacienteModel atualizarParcial(Long id, CadastroRecordDto dto){
        PacienteModel pacienteModel = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + id));

        if(dto.nome() != null) pacienteModel.setNome(dto.nome());
        if(dto.telefone() != null) pacienteModel.setTelefone(dto.telefone());
        if (dto.email() != null) pacienteModel.setEmail(dto.email());
        if (dto.dataNascimento() != null) pacienteModel.setDataNascimento(dto.dataNascimento());
        if (dto.status() != null) pacienteModel.setStatus(dto.status());
        if (dto.frequencia() != null) pacienteModel.setFrequencia(dto.frequencia());

        ProntuarioModel prontuarioModel = pacienteModel.getProntuario();
        if (prontuarioModel == null){
            prontuarioModel = new ProntuarioModel();
            prontuarioModel.setPaciente(pacienteModel);
        }

        if (dto.queixaPrincipal() != null) prontuarioModel.setQueixaPrincipal(dto.queixaPrincipal());
        if (dto.historicoFamiliar() != null) prontuarioModel.setHistoricoFamiliar(dto.historicoFamiliar());
        if (dto.observacoesIniciais() != null) prontuarioModel.setObservacoesIniciais(dto.observacoesIniciais());
        if (dto.anotacoesGerais() != null) prontuarioModel.setAnotacoesGerais(dto.anotacoesGerais());

        prontuarioRepository.save(prontuarioModel); // É bom garantir o salvamento do prontuário
        return pacienteRepository.save(pacienteModel);
    }
}
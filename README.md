API PsiCare
A API PsiCare é um sistema desenvolvido para o gerenciamento de consultas psicológicas. O projeto oferece funcionalidades abrangentes para cadastro de pacientes, controle de prontuários, atualizações de registros a cada encontro e agendamento dinâmico, visando otimizar a rotina de psicólogos.

Tecnologias Utilizadas
O projeto foi construído utilizando as seguintes tecnologias e versões:

Java: 21

Spring Boot: 3.5.8

Banco de Dados: MySQL

Documentação: SpringDoc OpenAPI (Swagger UI)

Outras Dependências: Lombok, Spring Data JPA, Validation.

Funcionalidades Principais
A API oferece endpoints para o gerenciamento completo de pacientes e suas informações clínicas:

Cadastro Completo: Criação de pacientes incluindo dados pessoais, histórico familiar, queixas principais e observações iniciais.

Agendamento: Criação automática de agendamento se data e hora forem fornecidas no cadastro.

Listagem e Busca: Listar todos os pacientes ou buscar um paciente específico por ID.

Atualização: Atualização completa (PUT) ou parcial (PATCH) dos dados cadastrais e do prontuário.

Remoção: Exclusão de registros de pacientes.

Configurações e Execução
Pré-requisitos
JDK 21 instalado.

Maven instalado.

MySQL rodando.

Configuração do Banco de Dados
A aplicação está configurada para conectar-se ao MySQL com as seguintes definições padrão:

Host: localhost

Porta do Banco: 3307

Nome do Banco: psicare

Usuário: root

Senha: 1234

Atenção: Certifique-se de criar o database psicare antes de iniciar a aplicação:

SQL

CREATE DATABASE psicare;
Rodando a Aplicação
Navegue até o diretório do projeto:

Bash

cd backend_psicare/psychology/psychology/psychology
Execute o projeto via Maven:

Bash

./mvnw spring-boot:run
A aplicação será iniciada na porta 8087 com o contexto /psicare/.

Documentação da API
A documentação interativa (Swagger UI) pode ser acessada após iniciar a aplicação através do endereço:

http://localhost:8087/psicare/swagger-ui/index.html

O arquivo JSON da especificação OpenAPI está disponível em: http://localhost:8087/psicare/v3/api-docs

Autores
Equipe de Desenvolvimento: Pedro Araujo e Lucas Anciolly

Contato: pedrol.dearaujo13@gmail.com

📄 Licença
Este projeto está licenciado sob a licença Apache 2.0.

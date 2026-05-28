# 🏥 Sistema de Agendamento Clínico

Sistema desenvolvido em Java para gerenciamento de uma clínica, com funcionalidades de cadastro de pacientes, profissionais, agendamento de consultas e fila de espera.

O projeto utiliza arquitetura orientada a objetos e persistência de dados em arquivos JSON.

---
## 👤 Pacientes
- Cadastro de pacientes
- Armazenamento automático em JSON
- Controle de prioridade
- Histórico médico
- Convênio médico

---

## 🩺 Profissionais
- Cadastro de profissionais
- Especialidade
- Valor de consulta
- Horário disponível
- Duração do atendimento

---

## 📅 Agendamentos
- Agendamento de consultas
- Verificação de conflitos:
  - Horário
  - Sala
  - Profissional
- Controle máximo de atendimentos

---

## ⏳ Fila de Espera
- Adição automática em fila quando não houver vaga
- Priorização de pacientes prioritários
- Exibição da fila em tempo real

---

## 💰 Faturamento
- Finalização de atendimento
- Controle de cobrança:
  - Particular
  - Convênio
- Cancelamento de atendimento
- Taxa de cancelamento configurável
- Prazo de isenção configurável
- Emissão de recibos

---

## 📊 Relatórios
- Relatório por profissional
- Relatório por período
- Relatório por convênio
- Relatório mensal
- Indicadores gerais:
  - Total de atendimentos
  - Receita total
  - Retornos
  - Profissional mais demandado
  - Fila de espera

---

# 🧠 Conceitos Aplicados

- Programação Orientada a Objetos (POO)
- Herança
- Polimorfismo
- Encapsulamento
- Enumerações (Enum)
- Collections Framework
- PriorityQueue
- Separação em camadas:
  - Model
  - Service
  - Repository
- Persistência de dados
- Tratamento de exceções

---

## 💾 Integração Externa

O projeto utiliza a biblioteca:

- Gson (Google JSON)

### 🔧 Função da integração:

A biblioteca Gson é utilizada para converter objetos Java em arquivos JSON e permitir a persistência de dados fora da aplicação.

Exemplo de arquivos gerados:

- pacientes.json
- profissionais.json

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Maven
- Gson
- VS Code
- PowerShell

---

## 📁 Estrutura do Projeto

src/
└── main/
└── java/
├── main/
│        └── App.java
├── model/
│          ├── Paciente.java
│          ├── Profissional.java
│          ├── Pessoa.java
│          ├── Consulta.java
|          ├── Atendimento.java
|          ├── Recibo.java
|          ├── TipoAtendimento.java
|          ├── TipoCobranca.java
│          └── Sala.java
├── service/
|           ├── AgendamentoService.java
|           ├── FaturamentoService.java
|           ├── FilaEspera.java
|           └── RelatorioService.java
└── repository/
            ├── JsonRepository.java

---

## ▶️ Como Executar o Projeto

### 1. Clonar Repositorio
```bash 
git clone 
```

### 2. Entrar na pasta do projeto
```bash 
cd Projeto_AgendamentoClinico
```

### 3. Compilar o projeto com Maven

```bash
& "C:\apache-maven-3.9.16-bin\apache-maven-3.9.16\bin\mvn.cmd" clean install
```

### 4. Eecutar o projeto com Maven
```bash
& "C:\apache-maven-3.9.16-bin\apache-maven-3.9.16\bin\mvn.cmd" exec:java "-Dexec.mainClass=main.App"
```

## Executar pelo VS Code (alternativo)
```bash
App.java
```

```bash
Run ▶
```

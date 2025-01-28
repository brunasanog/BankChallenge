
# Bank Challenge - Sistema de Gestão de Contas

Este projeto visa criar um sistema de gestão de contas bancárias simples, com funcionalidades de cadastro, login, consulta de saldo, saque, depósito e visualização de extrato. O sistema utiliza um banco de dados para armazenar informações de usuários, contas e transações.

## Funcionalidades

### 1. **Abertura de Conta**

O sistema permite que um novo usuário se cadastre utilizando o CPF como identificador único. Durante o cadastro, o sistema verifica se o CPF já está registrado no banco de dados para evitar duplicidade.

**Validações**:

- Verificar se o CPF já está cadastrado.
- Caso o CPF já exista, o sistema informa que o cliente já possui login.
- O CPF precisa ter exatos 11 digitos, sem espaços e não pode ser nulo ou vazio.
- Se o CPF for único, o cadastro é realizado com sucesso.

### 2. **Login**

Após o cadastro, o usuário pode realizar o login utilizando o CPF e a senha. O sistema valida as credenciais verificando se o CPF existe no banco de dados e se a senha corresponde à registrada.

**Validações**:

- Verificar se o CPF existe no banco de dados.
- Comparar a senha fornecida com a senha armazenada (utilizando *hashing*).

### 3. **Visualização de Saldo**

Após o login, o usuário pode visualizar o saldo da sua conta. O saldo é armazenado em uma tabela separada (`account`) e associado ao usuário via CPF.

### 4. **Realizar Saques**

O usuário pode realizar saques, desde que tenha saldo suficiente em sua conta. O sistema realiza as seguintes etapas:

- Verifica se o saldo é suficiente para o saque.
- Atualiza o saldo na tabela `account`.
- Registra a transação na tabela `transactions`.

**Validações**:

- Verificar se o saldo é suficiente.
- Verificar se o valor não é nulo ou vazio.
- Registrar a transação de saque.

### 5. **Realizar Depósitos**

Os depósitos são realizados pelos usuários autenticados. O sistema verifica se o valor do depósito é válido (maior que zero) e atualiza o saldo na tabela `account`.

**Validações**:

- Verificar se o valor do depósito é positivo.
- Registrar a transação de depósito.

### 6. **Visualização de Extrato**

O usuário pode visualizar seu extrato bancário, que contém um histórico completo de transações realizadas (saques, depósitos e transferências).

---

## Estrutura do Banco de Dados

O banco de dados é composto por três tabelas principais: `user`, `account` e `transactions`. A seguir, estão os detalhes de cada tabela.

### Tabela `user` (Usuários)

- `id`: Identificador único do usuário (PK).
- `cpf`: CPF do usuário (único).
- `name`: Nome completo do usuário.
- `email`: Email do usuário.
- `phone`: Telefone de contato.
- `birth_date`: Data de nascimento do usuário.
- `account_type`: Tipo de conta (corrente, poupança, salário).
- `password`: Senha do usuário (armazenada com *hashing*).

### Tabela `account` (Contas)

- `id`: Identificador único da conta (PK).
- `user_id`: Referência ao `id` da tabela `user` (FK).
- `balance`: Saldo atual da conta.
- `account_type`: Tipo de conta (CHECKING, SAVINGS, SALARY).

### Tabela `transactions` (Transações)

- `id`: Identificador único da transação (PK).
- `transaction_type`: Tipo de transação (saque, depósito, transferência).
- `amount`: Valor da transação.
- `transaction_date`: Data e hora da transação.
- `account_id`: Referência ao `id` da tabela `account` (para transferências).
---

## Relacionamentos no Banco de Dados

- **Usuário ↔ Conta (por tipo)**: Relacionamento 1:1 (um usuário tem uma conta).
- **Conta ↔ Transações**: Relacionamento 1:N (uma conta pode ter várias transações, como saques ou depósitos).

![Modelo MER](https://github.com/brunasanog/BankChallenge/blob/feature/final-adjustments/src/main/java/assets/Modelo%20Entidade%20Relacionamento%20(MER).png?raw=true)

![Modelo Físico](https://github.com/brunasanog/BankChallenge/blob/feature/final-adjustments/src/main/java/assets/Modelo%20F%C3%ADsico.png?raw=true)


---

## Considerações Finais

Este sistema é uma implementação simples de um banco de dados para gerenciar contas bancárias e transações, com validações de dados e um fluxo de operações bancárias. 

Este projeto foi desenvolvido por Bruna Sanog para o desafio 2 do estágio Full Cycle na empresa Compass UOL.

---
## Stack utilizada

**Back-end:** Java

---
*"O sucesso é a soma de pequenos esforços repetidos dia após dia." – **Robert Collier***

---
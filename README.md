# TestLab 📝

![Status](https://img.shields.io/badge/Status-Pronto%20-green)
![Java](https://img.shields.io/badge/Java-21-blue)
![JavaFX](https://img.shields.io/badge/UI-JavaFX-orange)
![Docker](https://img.shields.io/badge/Database-PostgreSQL-blue)

### Descrição do Projeto

**TestLab** é um sistema de desktop para ambiente acadêmico, projetado para auxiliar professores e administradores na criação, gerenciamento e geração de provas. A aplicação permite a administração completa de um banco de questões, disciplinas e usuários, culminando na geração de provas customizadas e relatórios em PDF.

A interface gráfica foi construída com JavaFX, e a aplicação segue uma arquitetura robusta com separação de camadas (View, Controller, Service, DAO) e utiliza padrões de design como Singleton, Facade e Factory Method.

---

### ✨ Funcionalidades Implementadas

* **Gerenciamento de Usuários:** CRUD completo para usuários com diferentes níveis de acesso (Administrador e Funcionário).
* **Gerenciamento de Disciplinas:** CRUD completo para Disciplinas e seus respectivos Assuntos.
* **Banco de Questões:** CRUD completo para um banco de Questões, suportando tipos dissertativas e de múltipla escolha.
* **Geração de Provas:** Funcionalidade para gerar provas aleatórias com base em critérios (disciplina, dificuldade, quantidade).
* **Relatórios em PDF:** Geração de provas formatadas em arquivos PDF, prontas para impressão.
* **Busca e Filtragem:** Interfaces de gerenciamento com busca e filtragem de dados em tempo real.

---

### 🚀 Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Interface Gráfica:** JavaFX 21
* **Build Tool:** Gradle (com Kotlin DSL)
* **Persistência de Dados:** JPA / Hibernate
* **Banco de Dados:** PostgreSQL (gerenciado via Docker Compose)
* **Segurança:** jBCrypt para hashing de senhas
* **Geração de Relatórios:** Apache PDFBox

---

### 📋 Pré-requisitos

Antes de começar, garanta que você tem os seguintes softwares instalados na sua máquina:

1.  **Java Development Kit (JDK):** Versão 21 ou superior.
2.  **Docker** e **Docker Compose:** Para executar o contêiner do banco de dados.

---

### ▶️ Como Executar Localmente

Siga os passos abaixo para configurar e executar o projeto no seu ambiente.

**1. Clone o Repositório**
```bash
git clone https://github.com/RobertLuann/TestLab.git
cd TestLab
```
**2. Ligue o banco de dados**

Na raíz do projeto, rode:
```bash
docker compose up -d
```
Espere o contêiner ser criado e verifique se está rodando corretamente:
```bash
docker ps
```
**3. Faça o build do projeto**
```bash
./gradlew build
```
Pode ser feito com auxílio de IDEs como Intellij IDEA.

**4. Rode o projeto**

Pode ser feito com IDE ou com o comando:

```bash
./gradlew run
```
Ao ser recebido pela tela de Login, utilize os usuários gerados pelo seeder:
* **Login:** gerente@testlab.com 
* **Senha:** 123456

Ou

* **Login:** funcionario@testlab.com
* **Senha:** 654321

### 🧑‍💻 Autores
####  Adriam
####  Robert
####  Renixon
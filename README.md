# Locadora de Veículos — Locadora-veiculos-JAVA

Sistema de Gestão e Simulação de Locação de Veículos desenvolvido em Java (JDK 21) com JDBC, padrão DAO e MySQL (Docker).

Este repositório contém uma aplicação de console para cadastro de clientes, listagem de veículos, simulação de locações e processamento de devoluções/pagamentos.

---

## Visão Geral / Objetivo
Sistema para gerenciamento e simulação de locação de veículos usando Threads, Java (JDK 21) e Banco de Dados MySQL.
Provides a small console application to register customers, list vehicles and simulate rentals (with concurrency) backed by a MySQL database.

## Principais Funcionalidades
- Cadastro e validação de clientes (nome, CPF, email)
- Login de usuário por CPF
- Listagem de veículos disponíveis com seus status
- Simulação de locações e devoluções com fluxo interativo via Thread (menu de console)
- Cálculo de valor total de locação, multa por atraso e processamento de pagamento
- Persistência via JDBC e scripts SQL para criação das tabelas

## Tecnologias Utilizadas
- Java 21
- Banco de Dados: MySQL (container Docker disponível em `docker-compose.yml`)
- Persistência / Conectividade: JDBC
- Concorrência: Threads e Runnable
- Lombok (anotações de redução de boilerplate)
- Log: Log4j2

## Pré-requisitos
- Java JDK 21 instalado e com `JAVA_HOME` configurado
- Maven instalado (>= 3.6)
- Docker & Docker Compose (recomendado para rodar o MySQL)
- Plugin Lombok na IDE (IntelliJ/VSCode) para evitar warnings no editor

## Como configurar o banco de dados

O projeto inclui `docker-compose.yml` para iniciar um MySQL pronto:

```bash
# Inicia o MySQL via Docker Compose (PowerShell ou Bash)
docker-compose up -d
```

O script de criação das tabelas está em `src/main/resources/schema.sql`.

Aplicar o schema ao container MySQL (exemplo PowerShell):

```powershell
# Envia o conteúdo do arquivo para o mysql dentro do container
Get-Content .\src\main\resources\schema.sql | docker exec -i locadora_mysql mysql -uroot -proot locadora_db
```

Ou usar o cliente `mysql` da máquina apontando para a porta mapeada (3307):

```bash
mysql -h 127.0.0.1 -P 3307 -u root -proot < src/main/resources/schema.sql
```

## Como executar o projeto

1. Compilar com Maven:

```bash
mvn clean package
```

2. Executar a aplicação (exemplo com dependências copiadas):

```bash
# mvn dependency:copy-dependencies -DoutputDirectory=target/dependency
# Executar (Windows):
java -cp "target/classes;target/dependency/*" com.locadora.app.LocacaoApplication
```

Ou via plugin exec do Maven:

```bash
mvn -Dexec.mainClass="com.locadora.app.LocacaoApplication" org.codehaus.mojo:exec-maven-plugin:3.1.0:java
```

Observação: a aplicação é de console/interactive prompt. Siga o menu para cadastrar clientes, logar, alugar e devolver veículos.

## Gerar Javadoc (PT / EN)

Gerar documentação padrão:

```bash
mvn javadoc:javadoc
```

Gerar Javadoc com tentativa de locale (depende da configuração do plugin):

```bash
mvn javadoc:javadoc -DoutputDirectory=target/site/apidocs-en -Dadditionalparam="-locale en_US"
mvn javadoc:javadoc -DoutputDirectory=target/site/apidocs-pt -Dadditionalparam="-locale pt_BR"
```

## Estrutura de Pacotes e Principais Classes

- `com.locadora.app`
  - `LocacaoApplication.java` — ponto de entrada (main)
- `com.locadora.conn`
  - `ConnectionFactory.java` — fábrica de conexões JDBC
- `com.locadora.domain`
  - `Cliente.java`, `Veiculo.java`, `Locacao.java`, `Status.java`
- `com.locadora.exception`
  - Exceções customizadas: `ClienteInformationException`, `VeiculoInformationException`, `LocacaoInfomationException`
- `com.locadora.repository`
  - Repositórios e implementações JDBC: `*Repository*.java`
- `com.locadora.services`
  - Regras de negócio: `ClienteServices`, `VeiculoService`, `LocacaoServices`, `Projeto`
- `com.locadora.threads`
  - `GerenciadorDeTask.java` — menu interativo (Runnable)

Arquivos importantes:

- `src/main/resources/schema.sql` — script SQL para criar tabelas
- `docker-compose.yml` — arranque opcional do MySQL em container
- `pom.xml` — configuração Maven (Java 21, dependências)

## Boas práticas e melhorias sugeridas
- Substituir `Optional.get()` sem checagem por `orElseThrow` ou verificação `isPresent/isEmpty`
- Remover imports não usados e tratar avisos do analisador
- Criar um "fat-jar" com `maven-shade-plugin` para facilitar execução
- Adicionar testes unitários e integração (DB em memória ou container)

## Contribuidores
<Victor Guimarães> — Desenvolvedor principal


## Licença
Ver o arquivo `LICENSE` no repositório.

---

Se quiser que eu também gere os Javadocs (PT/EN) agora ou atualize o `pom.xml` para automatizar a geração, diga qual opção prefere que eu execute.

# Sistema de Eleição 
## Descrição do Projeto

O *Sistema de Eleição* é uma aplicação desenvolvida utilizando *Spring Boot* no back-end e *Angular* no front-end, que permite gerenciar um sistema de eleições com partidos e candidatos. A aplicação oferece funcionalidades de criação, edição, listagem e remoção de partidos e candidatos, além de gravação e recuperação de dados. O sistema foi projetado com foco em modularidade, utilizando boas práticas como injeção de dependência e separação de responsabilidades.

### Tecnologias Utilizadas

- *Back-end*: Spring Boot, Java Streams, Lambdas, REST API
- *Front-end*: Angular, Angular Material
- *Persistência*: Gravação e leitura de dados via arquivos (planejado para evoluir para banco de dados)
- *Testes*: JUnit (para testes unitários)

## Funcionalidades Implementadas

### Partidos

1. Criar novo partido com nome, número e CNPJ.
2. Listar todos os partidos cadastrados.
3. Editar dados de um partido existente.
4. Remover partido pelo nome.
5. Validações para garantir que não existam partidos com nomes, números ou CNPJs duplicados.

### Candidatos

1. Criar novo candidato com nome, número, CPF e partido associado.
2. Listar todos os candidatos cadastrados.
3. Editar dados de um candidato existente.
4. Remover candidato pelo nome.
5. Validações para garantir que não existam candidatos com nomes, números ou CPFs duplicados.

### Gravação e Recuperação de Dados

1. Salvar dados de partidos e candidatos em arquivos.
2. Recuperar os dados salvos na inicialização do sistema.

### Utilização de Java Streams e Expressões Lambda

- Várias operações no sistema utilizam Streams e Lambdas para otimizar filtragens, buscas e manipulações de dados.

### Interface de Usuário (Angular)

1. Interface gráfica para gerenciar partidos e candidatos, com suporte a operações de CRUD.
2. Design responsivo utilizando Angular Material.

## Funcionalidades Futuras

1. Implementação de um banco de dados para armazenar partidos e candidatos de forma mais robusta.

### Suporte a Eleições

- Implementar um módulo de eleição que permita aos usuários cadastrados votarem nos candidatos e apurar os resultados.
 

**LiterAlura - Biblioteca Digital 📚
O LiterAlura é uma aplicação de console desenvolvida em Java que permite o gerenciamento de um catálogo literário através do consumo da API Gutendex. O projeto foca em persistência de dados relacional e manipulação de arquivos JSON.

**🚀 Funcionalidades
O sistema oferece um menu interativo com as seguintes capacidades:

Busca Web Dinâmica: Pesquisa livros pelo título diretamente na API Gutendex e salva os resultados (livro e autor) no banco de dados local.

Gestão de Autores:

Listagem de todos os autores registrados no banco.

Filtro de autores vivos em um determinado ano (utilizando consultas JPQL personalizadas).

Catálogo de Livros:

Listagem completa de livros salvos.

Busca por título dentro do acervo local.

Estatísticas por Idioma: Conta a quantidade de livros registrados filtrando por siglas de idiomas (ex: pt, en, fr).

**🛠️ Tecnologias Utilizadas
Java 21: Versão utilizada no desenvolvimento.

Spring Boot 4.0.3: Framework base para a aplicação.

Spring Data JPA: Para abstração da camada de persistência e consultas ao banco.

PostgreSQL: Banco de dados relacional para armazenamento persistente.

Jackson: Biblioteca para conversão de JSON em objetos Java (DTOs/Records).

**📁 Estrutura do Projeto
model: Contém as entidades JPA (Livro, Autor) e os Records para mapeamento do JSON (DadosLivro, DadosAutor, Resultados).

repository: Interfaces que estendem JpaRepository para operações de CRUD e queries customizadas.

services: Classes responsáveis pelo consumo da API via HttpClient e conversão de dados.

principal: Classe Principal.java que gerencia a lógica do menu e interação com o usuário.

**🌐 Integração com a API Gutendex
O LiterAlura consome dados da Gutendex, uma API pública que fornece informações sobre mais de 70.000 livros gratuitos do Projeto Gutenberg.

Como funciona o consumo:
Endpoint de Busca: A aplicação utiliza o endpoint https://gutendex.com/books/?search= para localizar obras específicas pelo título digitado pelo utilizador.

Processamento de JSON: O corpo da resposta (JSON) é capturado pela classe ConsumoApi através do HttpClient do Java.

Mapeamento de Dados:

O JSON é desserializado para o record Resultados, que contém uma lista de DadosLivro.

Cada DadosLivro contém uma lista de DadosAutor, capturando nome e datas de nascimento/falecimento.

Conversão para Entidades: Após o mapeamento, os dados são convertidos de Records (DTOs) para as Entidades JPA Livro e Autor, permitindo a persistência automática no PostgreSQL através dos Repositories.

Exemplo de Resposta Mapeada:
A aplicação extrai e organiza os seguintes campos do JSON original:

title ➔ Título do livro.

authors ➔ Nome e datas do autor.

languages ➔ Lista de siglas de idiomas.

download_count ➔ Total de downloads registados.

**🔧 Configuração e Instalação
Pré-requisitos
JDK 21 instalado.

PostgreSQL rodando localmente ou em container.

ariáveis de Ambiente
O projeto utiliza variáveis no application.properties para segurança. Configure as seguintes variáveis no seu sistema ou IDE:

DB_HOST: Endereço do seu banco 

DB_USER: Seu usuário do PostgreSQL.

DB_PASSWORD: Sua senha do PostgreSQL.

Execução
Você pode utilizar o Maven Wrapper incluído no projeto para rodar a aplicação:

Bash
./mvnw spring-boot:run

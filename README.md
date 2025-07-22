# 📚 Literalura

Projeto desenvolvido durante o curso de Desenvolvimento Back-end em Java, uma iniciativa da ONE do Tech Foundation — uma parceria entre Oracle Next Education e Alura.

O projeto tem como objetivo consumir dados da API [Gutendex](https://gutendex.com/), que reúne obras literárias, permitindo ao usuário buscar livros e autores, armazenar esses dados em banco de dados e realizar consultas posteriormente.

##  Tecnologias Utilizadas

- Java 17
- Spring Boot
- JPA + Hibernate
- PostgreSQL
- Maven
- API Rest (Gutendex)
- IntelliJ IDEA

---

## 🎯 Funcionalidades

A aplicação é executada via terminal (linha de comando) e permite:

-  Buscar livros por título (consultando a API Gutendex e armazenando no banco de dados)
-  Listar todos os livros registrados
-  Listar autores registrados (com datas de nascimento e falecimento)
-  Buscar autores vivos em determinado ano
-  Filtrar livros por idioma (pt, en, es, fr)

---

## 🗃️ Estrutura do Projeto

- `model/`: contém as entidades `Livro` e `Autor`, além dos DTOs para conversão dos dados da API.
- `service/`: classes de consumo de API e conversão dos dados para entidades.
- `repository/`: interface `LivroRepository` estende `JpaRepository` para operações no banco de dados.
- `principal/`: contém a classe `Principal` com a lógica de interação com o usuário via terminal.
- `LiteraluraApplication`: classe principal que executa o projeto.

---

## Exemplo da Aplicação em Execução

Ao iniciar a aplicação é apresentado o menu com as opções:

1 - Buscar livro por título
2 - Listar livros registrados
3 - Listar autores registrados
4 - Listar autores vivos em determinado ano
5 - Listar livros em um determinado idioma

### Opção 1 - Buscar livro por título
É solicitado que o usuário digite o nome do livro:

<img width="573" height="450" alt="image" src="https://github.com/user-attachments/assets/27eae1a0-1ca3-40d5-8ab9-e7de3f4cc0a4" />

- Se o livro foir encontrado na API, os dados são exibidos e o livro é salvo no banco de dados.
- Caso já exista, o sistema apenas apresenta as informações.

<img width="1686" height="451" alt="image" src="https://github.com/user-attachments/assets/98b6c3e9-8f68-4272-88c4-6dbe4f33465e" />

---

### Opção 2 - Listar livros registrados
Exibe todos os livros cadastrados, mostrando:
- Título
- Nome do autor
- Idioma
- Número de downloads

<img width="823" height="609" alt="image" src="https://github.com/user-attachments/assets/a3c1aa37-0858-4302-98c8-b4c175dc3aa4" />

---

### Opção 3 - Listar autores registrados

É listados todos os autores cadastrados no banco de dados com o seus devidos dados

<img width="796" height="682" alt="image" src="https://github.com/user-attachments/assets/a4d1a6df-ae31-4724-b278-3d101384a78d" />

---

### Opção 4 - Autores vivos em determinado ano
Ao escolher a opção 4 é apresentado a mensagem para o usuário digitar o ano em que deseja saber quais autores estavam vivos no ano

<img width="619" height="656" alt="image" src="https://github.com/user-attachments/assets/586644aa-870e-4ff9-bcc1-7022df24c490" />

---

### Opção 5 - Buscar livros por idioma
Ao escolher a opção 5 é apresentado um novo menu para que o usuário escolha entre os idiomas: pt (português), fr (frânces) e inglês (en)

<img width="903" height="690" alt="image" src="https://github.com/user-attachments/assets/09daa549-40d1-4ffc-b0a0-f10a3d0af25d" />

---

## 📄 Licença

Projeto desenvolvido para fins educacionais — todos os direitos reservados a Alura e Oracle Next Education.




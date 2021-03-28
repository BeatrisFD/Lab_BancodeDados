CREATE TABLE Aluno(
Codigo_Aluno INT NOT NULL,
Nome VARCHAR(100) NOT NULL
PRIMARY KEY(Codigo_Aluno)
)

CREATE TABLE Atividade(
Codigo INT NOT NULL,
Descricao VARCHAR(100) NOT NULL,
IMC FLOAT NOT NULL
PRIMARY KEY(Codigo)
)

CREATE TABLE Atividadesaluno(
Codigo_Aluno INT NOT NULL,
Altura FLOAT NOT NULL,
Peso FLOAT NOT NULL,
IMC FLOAT NOT NULL,
Atividade INT NOT NULL
PRIMARY KEY(Codigo_Aluno)
FOREIGN KEY (Codigo_Aluno) REFERENCES Aluno(Codigo_Aluno))


CREATE PROCEDURE sp_alunoatividades(@Codigo_Aluno INT, @Nome VARCHAR(100), @Altura FLOAT, @Peso FLOAT)
AS
	DECLARE
			@cod1 int,
			@cod2 int,
			@IMC float,
			@Atividade int
	

		SET @cod1 = (select a.Nome, b.Altura, b.Peso from Aluno as a, Atividadesaluno as b 
		where Nome = @Nome and a.Codigo_Aluno = b.Codigo_Aluno)
		SET @cod2 = (select a.Codigo_Aluno, b.Altura, b.Peso from Aluno as a, Atividadesaluno as b 
		where a.Codigo_Aluno = b.Codigo_Aluno)
	
	if (@Codigo_Aluno = null or @cod1 = @Nome)
		SET @IMC = (@Peso / (@Altura*@Altura))
		INSERT INTO Aluno values (@Codigo_Aluno, @Nome)
		INSERT INTO Atividadesaluno values (@Codigo_Aluno, @Altura, @Peso, @IMC, (select Atividade from Atividadesaluno as a, Atividade as b where @IMC < b.IMC and a.Atividade = b.Codigo))
	
	if (@Nome = null or @Codigo_Aluno = @cod2)
		SET @IMC = (@Peso / (@Altura*@Altura))
		Update Atividadesaluno set @Codigo_Aluno = Codigo_Aluno, @Altura = Altura,
		@Peso = Peso, @IMC = IMC, @Atividade = Atividade
		where @Codigo_Aluno = Codigo_Aluno

drop PROCEDURE sp_alunoatividades

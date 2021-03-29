USE Queries_Dinamicas

CREATE TABLE Produto(
Codigo INT NOT NULL,
Nome VARCHAR(100) NOT NULL,
Valor FLOAT NOT NULL
PRIMARY KEY(Codigo)
)

CREATE TABLE Entrada(
Codigo_Transacao INT NOT NULL,
Codigo_Produto INT NOT NULL,
Quantidade INT NOT NULL,
Valor_Total FLOAT NOT NULL
PRIMARY KEY(Codigo_Transacao)
FOREIGN KEY (Codigo_Produto) REFERENCES Produto(Codigo))
 
CREATE TABLE Saida(
Codigo_Transacao INT NOT NULL,
Codigo_Produto INT NOT NULL,
Quantidade INT NOT NULL,
Valor_Total FLOAT NOT NULL
PRIMARY KEY(Codigo_Transacao)
FOREIGN KEY (Codigo_Produto) REFERENCES Produto(Codigo))

Select * from Entrada
Select * from Saida
Select * from Produto

CREATE PROCEDURE sp_insereproduto(@Codigo_Produto INT, @Valor_Total FLOAT,
@Quantidade INT, @tipo VARCHAR(1), @Nome VARCHAR(100), @Valor FLOAT)
AS
	DECLARE 
			@tip VARCHAR(1),
			@tabela VARCHAR(10),
			@query VARCHAR(MAX),
			@erro VARCHAR(MAX)
		
	IF(@tipo ='e')
		SET @tabela = 'Entrada'
	else 
		SET @tabela = 'Saida'

	SET @query = 'INSERT INTO '+@tabela+' VALUES ('+CAST(@Codigo_Produto AS VARCHAR(5))+','+CAST(@Quantidade AS VARCHAR(5))+','+CAST(@Valor_Total AS VARCHAR(5))+')'
	PRINT @query

	BEGIN TRY
		INSERT INTO Produto VALUES (@Codigo_Produto, @Nome, @Valor)
		EXEC (@query)
	END TRY

	BEGIN CATCH
		SET @erro = ERROR_MESSAGE()
		IF (@erro LIKE '%primary%')
		BEGIN
			RAISERROR('ID produto duplicado', 16, 1)
		END
		ELSE
		BEGIN
			RAISERROR('Erro de processamento', 16, 1)
		END
	END CATCH

EXEC sp_insereproduto 1003, 100.0, 2, 's', 'p', 10.0

DROP PROCEDURE sp_insereproduto

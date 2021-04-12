CREATE DATABASE LBD_AV1 
GO
USE  LBD_AV1

CREATE TABLE times(
codigo_time		INT PRIMARY KEY,
nome_time		VARCHAR(30)	NOT NULL,
cidade			VARCHAR(50)	NOT NULL,
estadio			VARCHAR(50)	NOT NULL)

CREATE TABLE grupos(
grupo			CHAR(1)	NOT NULL,
codigo_time		INT	NOT NULL
PRIMARY			KEY (grupo,codigo_time),
FOREIGN KEY	(codigo_time) REFERENCES times(codigo_time))

CREATE TABLE jogos(
codigo_timeA	 INT NOT NULL,
codigo_timeB	INT NOT NULL,
gols_timeA		INT,
gols_timeB		INT,
data_jogo		DATE
PRIMARY KEY(codigo_timeA,codigo_timeB,data_jogo),
FOREIGN KEY	(codigo_timeA) REFERENCES times(codigo_time),
FOREIGN KEY	(codigo_timeB) REFERENCES times(codigo_time))

--INSERT DOS TIMES
INSERT INTO times VALUES
(1,'Botafogo-SP','Ribeirão Preto','	Santa Cruz'),
(2,'Bragantino','Bragança Paulista','Nabi Abi Chedid'),
(3,'Corinthians',' São Paulo','Arena Corinthians'),
(4,'Ferroviária',' Araraquara','Fonte Luminosa'),
(5,'Guarani','Campinas','Brinco de Ouro da Princesa'),
(6,'Ituano','Itu','Novelli Júnior'),
(7,'Mirassol','Mirassol','José Maria de Campos Maia'),
(8,'Novorizontino',' Novo Horizonte','Jorge Ismael de Biasi'),
(9,'Oeste',' Barueri','Arena Barueri'),
(10,'Palmeiras','São Paulo','Allianz Parque'),
(11,'Ponte Preta','Campinas','Moisés Lucarelli'),
(12,'Red Bull Brasil','Campinas','Moisés Lucarelli'),
(13,'Santos','Santos','Vila Belmiro'),
(14,'São Bento','Sorocaba','Walter Ribeiro'),
(15,'São Caetano','São Caetano do Sul','Anacletto Campanella'),
(16,'São Paulo','São Paulo','Morumbi')

SELECT times.nome_time FROM times  LEFT  JOIN grupos  ON ( times.codigo_time = grupos.codigo_time) WHERE grupos.codigo_time is NULL


--PROCEDURE
CREATE PROCEDURE sp_gerarGrupos
AS
BEGIN
	DECLARE @quatroGrandes	TABLE (cod_time INT)
	DECLARE @gruposChar		TABLE(id INT , grupoChar CHAR(1))
	DECLARE @times			TABLE(cod_time INT)
	DECLARE @sizeTable	INT,
			@rand		INT,
			@grupoSize	INT,
			@grupoPosicao	INT,
			@grupoChar	CHAR(1)
	INSERT INTO @quatroGrandes VALUES (3),(10),(13),(16)
	INSERT INTO @gruposChar VALUES(1,'A'),(2,'B'),(3,'C'),(4,'D')
	SET @grupoPosicao = 1

	SELECT @sizeTable = COUNT(cod_time) FROM @quatroGrandes
	WHILE(@sizeTable > 0)
	BEGIN
		SELECT TOP 1  @rand = cod_time FROM @quatroGrandes	ORDER BY NEWID()
		SELECT @grupoChar = grupoChar FROM @gruposChar WHERE id = @sizeTable
		INSERT INTO grupos VALUES (@grupoChar,@rand)
		DELETE FROM @quatroGrandes WHERE cod_time = @rand
		SET @sizeTable = @sizeTable - 1
	END

	INSERT INTO @times SELECT times.codigo_time FROM times  LEFT  JOIN grupos  ON ( times.codigo_time = grupos.codigo_time) 
	WHERE grupos.codigo_time is NULL

	SELECT @sizeTable = COUNT(cod_time) FROM @times
	WHILE(@sizeTable > 0)
	BEGIN 
	SET @grupoSize = 4
		WHILE(@grupoSize > 1)
		BEGIN 
			SELECT TOP 1  @rand = cod_time FROM @times	ORDER BY NEWID()
			SELECT @grupoChar = grupoChar FROM @gruposChar WHERE id = @grupoPosicao
			INSERT INTO grupos VALUES (@grupoChar,@rand)
			DELETE FROM @times WHERE cod_time = @rand
			SET @sizeTable = @sizeTable - 1
			SET @grupoSize = @grupoSize - 1 
		END
		SET @grupoPosicao = @grupoPosicao + 1
	END
END

EXEC sp_gerarGrupos

select times.nome_time,grupos.grupo from grupos,times WHERE times.codigo_time = grupos.codigo_time


DELETE FROM grupos

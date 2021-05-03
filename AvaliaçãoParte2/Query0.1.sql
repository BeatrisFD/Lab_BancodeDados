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

SELECT * FROM GRUPOS

DELETE FROM grupos

CREATE  TRIGGER t_Times on times
FOR INSERT,UPDATE,DELETE
AS 
BEGIN
	DECLARE @count INT

	SET @count = 0
	SELECT @count = COUNT(TIMES.codigo_time) FROM times

	if((SELECT COUNT(deleted.codigo_time) FROM  deleted) = 0)
		BEGIN
			SET @count -=1
		END
	IF(@count>=16)
		BEGIN
			ROLLBACK
		END
END

CREATE TRIGGER t_grupos ON grupos
FOR INSERT,DELETE
AS
BEGIN
	declare @count int

	SET @count = 0

	SELECT @count = COUNT(grupos.codigo_time) FROM grupos

	if((SELECT COUNT(deleted.codigo_time) FROM  deleted) = 0)
		BEGIN
			SET @count -=1
		END
	IF(@count>=16)
	BEGIN
		ROLLBACK
	END
END

CREATE TRIGGER t_rodadas ON jogos
FOR INSERT,DELETE
AS
BEGIN
	declare @count int

	SET @count = 0

	SELECT @count = COUNT(jogos.codigo_timeA) FROM jogos
	
	if((SELECT COUNT(deleted.codigo_timeA) FROM  deleted) = 0)
		BEGIN
			SET @count -=1
		END
	IF(@count>=96)
	BEGIN
		ROLLBACK
	END
END

		SELECT * FROM jogos


CREATE FUNCTION fn_buscar_qtde_empates(@cod_time INT)
RETURNS INT 
AS
BEGIN
	DECLARE @count INT
	SET @count = 0

	SELECT @count=COUNT(jogos.codigo_timeA) FROM jogos WHERE (jogos.codigo_timeA = @cod_time OR jogos.codigo_timeB = @cod_time) AND jogos.gols_timeA=jogos.gols_timeB
	RETURN @count
END


CREATE FUNCTION fn_buscar_qtde_vitorias(@cod_time INT)
RETURNS INT
AS
BEGIN
	DECLARE @count INT,
			@count2 INT
	SET @count = 0
	SET @count2=0

	SELECT @count=COUNT(jogos.codigo_timeA) FROM jogos WHERE (jogos.codigo_timeA = @cod_time) AND jogos.gols_timeA>jogos.gols_timeB
	SELECT @count2=COUNT(jogos.codigo_timeA) FROM jogos WHERE (jogos.codigo_timeB = @cod_time ) AND jogos.gols_timeA<jogos.gols_timeB

	RETURN (@count+@count2)
END

CREATE FUNCTION fn_buscar_qtde_derrotas(@cod_time INT)
RETURNS INT
AS
BEGIN
	DECLARE @count INT,
			@count2 INT
	SET @count = 0
	SET @count2=0

	SELECT @count=COUNT(jogos.codigo_timeA) FROM jogos WHERE (jogos.codigo_timeA = @cod_time) AND jogos.gols_timeA<jogos.gols_timeB
	SELECT @count2=COUNT(jogos.codigo_timeA) FROM jogos WHERE (jogos.codigo_timeB = @cod_time ) AND jogos.gols_timeA>jogos.gols_timeB

	RETURN (@count+@count2)
END

CREATE FUNCTION fn_buscar_qtde_jogos(@cod_time INT)
RETURNS INT
AS
BEGIN
	DECLARE @count INT
	SELECT @count= COUNT(JOGOS.codigo_timeA) FROM jogos WHERE jogos.codigo_timeA = @cod_time AND gols_timeA IS NOT NULL
	RETURN  @count
END

CREATE FUNCTION fn_mostrar_tabela_grupo(@grupo CHAR(1))
RETURNS @table TABLE(
nome_time varchar(30),
num_jogos_disputados INT,
num_vitorias INT,
num_empates INT,
num_derrotas INT,
gols_marcadados INT,
gols_sofridos INT,
saldo_gols INT,
pontos INT
)
AS
BEGIN
	DECLARE @num_jogos_disputados INT,
			@num_vitorias	INT,
			@num_empates INT,
			@num_derrotas INT,
			@gols_marcados INT,
			@gols_sofridos INT,
			@saldo_gols INT,
			@pontos INT,
			@codigo_time INT,
			@nome_time VARCHAR(50)

	DECLARE cursor_busca CURSOR FOR SELECT DISTINCT t.nome_time,t.codigo_time  FROM jogos j INNER JOIN grupos g
	ON (g.codigo_time = j.codigo_timeA OR g.codigo_time = j.codigo_timeB) INNER JOIN times t ON (g.codigo_time = t.codigo_time)
	WHERE g.grupo = @grupo

	OPEN cursor_busca
	FETCH NEXT FROM cursor_busca INTO @nome_time,@codigo_time
	--VERIFICAR SE HOUVE REGISTRO
    WHILE @@FETCH_STATUS = 0
	BEGIN
		SET @num_vitorias = dbo.fn_buscar_qtde_vitorias(@codigo_time)
		SET @num_empates = dbo.fn_buscar_qtde_empates(@codigo_time)
		SET @num_derrotas = dbo.fn_buscar_qtde_derrotas(@codigo_time)
		SET @num_jogos_disputados = @num_derrotas+@num_empates+@num_vitorias

		SELECT @gols_marcados = SUM(jogos.gols_timeA),@gols_sofridos = SUM(JOGOS.gols_timeB) FROM jogos WHERE jogos.codigo_timeA = @codigo_time AND gols_timeA IS NOT NULL AND gols_timeB IS NOT NULL
		SELECT @gols_marcados += SUM(jogos.gols_timeB),@gols_sofridos += SUM(JOGOS.gols_timeA) FROM jogos WHERE jogos.codigo_timeB = @codigo_time AND gols_timeA IS NOT NULL AND gols_timeB IS NOT NULL

		SET @saldo_gols = @gols_marcados - @gols_sofridos

		SET @pontos = (3*@num_vitorias)+(1*@num_empates)

		INSERT INTO @table VALUES
		(@nome_time,@num_jogos_disputados,@num_vitorias,@num_empates,@num_derrotas,@gols_marcados,@gols_sofridos,@saldo_gols,@pontos)

		FETCH NEXT FROM cursor_busca INTO @nome_time,@codigo_time
	END
	CLOSE cursor_busca
    DEALLOCATE cursor_busca
    RETURN
END

CREATE FUNCTION fn_mostrar_tabela_geral()
RETURNS @table TABLE(
nome_time varchar(30),
num_jogos_disputados INT,
num_vitorias INT,
num_empates INT,
num_derrotas INT,
gols_marcadados INT,
gols_sofridos INT,
saldo_gols INT,
pontos INT
)
AS
BEGIN
	DECLARE @num_jogos_disputados INT,
			@num_vitorias	INT,
			@num_empates INT,
			@num_derrotas INT,
			@gols_marcados INT,
			@gols_sofridos INT,
			@saldo_gols INT,
			@pontos INT,
			@codigo_time INT,
			@nome_time VARCHAR(50)

	DECLARE cursor_busca CURSOR FOR SELECT DISTINCT t.nome_time,t.codigo_time  FROM jogos j  
	INNER JOIN times t ON (t.codigo_time = j.codigo_timeA OR t.codigo_time = j.codigo_timeB)


	OPEN cursor_busca
	FETCH NEXT FROM cursor_busca INTO @nome_time,@codigo_time
	--VERIFICAR SE HOUVE REGISTRO
    WHILE @@FETCH_STATUS = 0
	BEGIN
		SET @num_vitorias = dbo.fn_buscar_qtde_vitorias(@codigo_time)
		SET @num_empates = dbo.fn_buscar_qtde_empates(@codigo_time)
		SET @num_derrotas = dbo.fn_buscar_qtde_derrotas(@codigo_time)
		SET @num_jogos_disputados = @num_derrotas+@num_empates+@num_vitorias

		SELECT @gols_marcados = SUM(jogos.gols_timeA),@gols_sofridos = SUM(JOGOS.gols_timeB) FROM jogos WHERE jogos.codigo_timeA = @codigo_time AND gols_timeA IS NOT NULL AND gols_timeB IS NOT NULL
		SELECT @gols_marcados += SUM(jogos.gols_timeB),@gols_sofridos += SUM(JOGOS.gols_timeA) FROM jogos WHERE jogos.codigo_timeB = @codigo_time AND gols_timeA IS NOT NULL AND gols_timeB IS NOT NULL

		SET @saldo_gols = @gols_marcados - @gols_sofridos

		SET @pontos = (3*@num_vitorias)+(1*@num_empates)

		INSERT INTO @table VALUES
		(@nome_time,@num_jogos_disputados,@num_vitorias,@num_empates,@num_derrotas,@gols_marcados,@gols_sofridos,@saldo_gols,@pontos)

		FETCH NEXT FROM cursor_busca INTO @nome_time,@codigo_time
	END
	CLOSE cursor_busca
    DEALLOCATE cursor_busca
    RETURN
END


CREATE FUNCTION fn_gerarQuartas()
RETURNS @table TABLE(
nome_timeA Varchar(50),
nome_timeB varchar(50))
AS
BEGIN
	DECLARE @nome_timeLA VARCHAR(50),
			@nome_timeVA VARCHAR(50),
			@nome_timeLB VARCHAR(50),
			@nome_timeVB VARCHAR(50),
			@nome_timeLC VARCHAR(50),
			@nome_timeVC VARCHAR(50),
			@nome_timeLD VARCHAR(50),
			@nome_timeVD VARCHAR(50)

			DECLARE cursor_grupoA CURSOR FOR SELECT nome_time FROM dbo.fn_mostrar_tabela_grupo('A') ORDER BY pontos DESC ,num_vitorias DESC ,gols_marcadados DESC,saldo_gols DESC
			OPEN cursor_grupoA
			FETCH NEXT FROM cursor_grupoA INTO @nome_timeLA
			FETCH NEXT FROM cursor_grupoA INTO @nome_timeVA

			CLOSE cursor_grupoA
			DEALLOCATE cursor_grupoA

			DECLARE cursor_grupoB CURSOR FOR SELECT nome_time FROM dbo.fn_mostrar_tabela_grupo('B') ORDER BY pontos DESC ,num_vitorias DESC ,gols_marcadados DESC,saldo_gols DESC
			OPEN cursor_grupoB
			FETCH NEXT FROM cursor_grupoB INTO @nome_timeLB
			FETCH NEXT FROM cursor_grupoB INTO @nome_timeVB

			CLOSE cursor_grupoB
			DEALLOCATE cursor_grupoB

			DECLARE cursor_grupoC CURSOR FOR SELECT nome_time FROM dbo.fn_mostrar_tabela_grupo('C') ORDER BY pontos DESC ,num_vitorias DESC ,gols_marcadados DESC,saldo_gols DESC
			OPEN cursor_grupoC
			FETCH NEXT FROM cursor_grupoC INTO @nome_timeLC
			FETCH NEXT FROM cursor_grupoC INTO @nome_timeVC

			CLOSE cursor_grupoC
			DEALLOCATE cursor_grupoC

			DECLARE cursor_grupoD CURSOR FOR SELECT nome_time FROM dbo.fn_mostrar_tabela_grupo('D') ORDER BY pontos DESC ,num_vitorias DESC ,gols_marcadados DESC,saldo_gols DESC
			OPEN cursor_grupoD
			FETCH NEXT FROM cursor_grupoD INTO @nome_timeLD
			FETCH NEXT FROM cursor_grupoD INTO @nome_timeVD

			CLOSE cursor_grupoD
			DEALLOCATE cursor_grupoD

			INSERT INTO @table VALUES
			(@nome_timeLA,@nome_timeVD),
			(@nome_timeLD,@nome_timeVA),
			(@nome_timeLB,@nome_timeVC),
			(@nome_timeLC,@nome_timeVB)

			RETURN
END




SELECT * FROM jogos

 SELECT DISTINCT t.nome_time,t.codigo_time  FROM jogos j  
	INNER JOIN times t ON (t.codigo_time = j.codigo_timeA OR t.codigo_time = j.codigo_timeB)

	SELECT * FROM fn_mostrar_tabela_geral() ORDER BY pontos DESC ,num_vitorias,gols_marcadados,saldo_gols

	SELECT SUM(jogos.gols_timeA), SUM(JOGOS.gols_timeB) FROM jogos WHERE jogos.codigo_timeB = 1 AND gols_timeA IS NOT NULL AND gols_timeB IS NOT NULL
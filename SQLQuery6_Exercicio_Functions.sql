CREATE TABLE Funcionario(
Codigo INT NOT NULL,
Nome VARCHAR(100) NOT NULL,
Salario FLOAT NOT NULL
PRIMARY KEY(Codigo)
)

CREATE TABLE Dependente(
Codigo_Funcionario INT NOT NULL,
Nome_Dependente varchar(100) NOT NULL,
Salario_Dependente FLOAT NOT NULL
FOREIGN KEY (Codigo_Funcionario) REFERENCES Funcionario(Codigo))

CREATE FUNCTION fn_funcdepend()
RETURNS @table TABLE (
Nome_Funcionario		VARCHAR(100),
Nome_Dependente			VARCHAR(100),
Salario_Funcionario		FLOAT,
Salario_Dependente		FLOAT
)
AS
BEGIN
	INSERT INTO @table (Nome_Funcionario, Nome_Dependente, Salario_Funcionario, Salario_Dependente)
		SELECT f.Nome, d.Nome_Dependente, f.Salario, d.Salario_Dependente FROM Funcionario as f, Dependente as d where f.Codigo = d.Codigo_Funcionario
	RETURN
END

Select * from fn_funcdepend()

CREATE FUNCTION fn_salarios(@cod int)
RETURNS FLOAT 
AS 
BEGIN
	DECLARE
			@sal	Float,
			@dep	Float,
			@func	Float
	IF (@cod != null)
	BEGIN
		SET @dep = (Select Salario_Dependente from Dependente where @cod = Codigo_Funcionario)
		SET @func = (Select Salario from Funcionario where @cod = Codigo)
		select @dep = Salario_Dependente, @func = Salario from Dependente, Funcionario where @cod = Codigo
		SET @sal = @dep + @func
	END
	RETURN (@sal)
END

drop function fn_salarios

Select dbo.fn_salarios(1) as sal

------------------------------------------------------------------------------------------------------

CREATE TABLE Cliente(
CPF INT NOT NULL,
Nome VARCHAR(100) NOT NULL,
Telefone VARCHAR(11),
Email VARCHAR(100)
PRIMARY KEY(CPF)
)

CREATE TABLE Produto(
Codigo INT NOT NULL,
Nome varchar(100) NOT NULL,
Descricao varchar(100) NOT NULL,
Valor_Unitario FLOAT NOT NULL
PRIMARY KEY(Codigo))

CREATE TABLE Venda(
CPF_Cliente INT NOT NULL,
Codigo_Produto INT NOT NULL,
Quantidade INT NOT NULL,
Dataa date NOT NULL,
FOREIGN KEY (CPF_Cliente) REFERENCES Cliente(CPF),
FOREIGN KEY (Codigo_Produto) REFERENCES Produto(Codigo))

CREATE FUNCTION fn_clienteproduto()
RETURNS @table TABLE (
Nome_Cliente		VARCHAR(100),
Nome_Produto		VARCHAR(100),
Quantidade			INT,
Valor_Total			Float
)
AS
BEGIN
	INSERT INTO @table (Nome_Cliente, Nome_Produto, Quantidade, Valor_Total)
		SELECT c.Nome, p.Nome, v.Quantidade, p.Valor_Unitario
		FROM Cliente as c, Produto as p, Venda as v where c.CPF = v.CPF_Cliente and v.Codigo_Produto = p.Codigo
	RETURN
END

Select * from fn_clienteproduto()

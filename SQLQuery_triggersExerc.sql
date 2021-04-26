/*
- Após a inserção de cada linha na tabela venda, 10% do total deverá ser transformado em pontos.
- Se o cliente ainda não estiver na tabela de pontos, deve ser inserido automaticamente após sua primeira compra
- Se o cliente atingir 1 ponto, deve receber uma mensagem dizendo que ganhou
*/

CREATE DATABASE exerciciotriggers
go
USE exerciciotriggers

CREATE TABLE cliente(
codigo INT NOT NULL,
nome VARCHAR(100)
PRIMARY KEY(codigo))
 
CREATE TABLE venda(
codigo_venda INT not null,
codigo_cliente int not null,
valor_total DECIMAL(7,2)
PRIMARY KEY(codigo_venda)
FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo))
 
CREATE TABLE pontos(
codigo_cliente INT NOT NULL,
total_pontos DECIMAL(7,2)
FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo))

INSERT INTO cliente VALUES
(1, 'Fulano'),
(2, 'Ze'),
(3, 'Beltrano')
 
INSERT INTO venda VALUES
(1, 1, 199.99),
(2, 1, 499.99),
(3, 2, 99.89),
(4, 2, 99.89),
(5, 3, 62.47),
(6, 3, 59.90)
 
INSERT INTO pontos VALUES
(1, 19.99),
(1, 49.99)
INSERT INTO pontos VALUES
(2, 9.99),
(2, 9.99)
INSERT INTO pontos VALUES
(3, 6.25),
(3, 5.99)

CREATE TRIGGER t_protegeproduto ON venda
FOR DELETE
AS 
BEGIN
	ROLLBACK TRANSACTION --Desfaz a última transação
	RAISERROR('Não é permitido apagar os produtos', 16, 1)
END

CREATE TRIGGER t_protegevenda ON venda
FOR update
AS 
BEGIN
	ROLLBACK TRANSACTION --Desfaz a última transação
	RAISERROR('Não é permitido alterar a venda', 16, 1)
	Select c.nome, v.valor_total from venda as v, cliente as c 
		where v.codigo_cliente = c.codigo & v.codigo_cliente = (SELECT max(codigo_venda) FROM venda)
END


/*Fazer um algoritmo que leia 3 valores e retorne se os valores formam um triângulo e se ele é
isóceles, escaleno ou equilátero.
Condições para formar um triângulo
	Nenhum valor pode ser = 0
	Um lado não pode ser maior que a soma dos outros 2.
*/
declare @A as int
declare @B as int
declare @C as int
declare @cont as int
set @A = 4
set @B = 4
set @C = 4

if @C > @A + @B OR @A > @B + @C OR @B > @A + @C
    PRINT 'NAO FORMAM TRIANGULO'

else if @A != @B AND @A != @C AND @B != @C 
    PRINT 'TRIANGULO ESCALENO'

else if @A = @B AND @A = @C
	PRINT 'TRIANGULO EQUILATERO'

else if @A = @B OR @A = @C OR @B = @C
	PRINT 'TRIANGULO ISOSCELES'


 -- Fazer um algoritmo que leia 1 número e mostre se são múltiplos de 2,3,5 ou nenhum deles
declare @Z as int
set @Z = 55
if @Z % 2 = 0
	PRINT 'múltiplo de 2'
else if @Z % 3 = 0
	PRINT 'múltiplos de 3'
else if @Z % 5 = 0
	PRINT 'múltiplos de 5'
else 
	PRINT 'nenhum deles'

 -- Fazer um algoritmo que leia 3 número e mostre o maior e o menor
declare @F as int
declare @G as int
declare @H as int
declare @maior as int
declare @menor as int
set @F = 4
set @G = 5
set @H = 6

set @maior = @F
if @G > @maior
	SELECT @maior = @G
if @H > @maior
	SELECT @maior = @H
	PRINT @maior

set @menor = @F
if @G < @menor
	SELECT @menor = @G
if @H < @menor
	SELECT @menor = @H
	PRINT @menor

/*
Fazer um algoritmo que calcule os 15 primeiros termos da série
1,1,2,3,5,8,13,21,...
E calcule a soma dos 15 termos
*/
WITH sequencia_fibonacci(fibonacci_nro, proximo_nro) AS (
	-- consulta âncora
	SELECT 0, 1
	UNION ALL
	-- consulta recursiva
	SELECT proximo_nro, fibonacci_nro = fibonacci_nro + proximo_nro
	FROM sequencia_fibonacci
	WHERE fibonacci_nro < 300

)SELECT SUM(fibonacci_nro) as Soma FROM sequencia_fibonacci;


-- Fazer um algorimto que separa uma frase, colocando todas as letras em maiúsculo e em minúsculo
SELECT Upper('frase de exemplo') AS maiusculo

SELECT lower('FRASE DE EXEMPLO') as minusculo

-- Fazer um algoritmo que inverta uma palavra
SELECT Reverse('exemplo') AS inverso

-- Verificar o palindromo
DECLARE @string NVARCHAR(255);
SET @string = 'aaa';

SELECT CASE WHEN @string=REVERSE(@string)THEN 'eh palindromo'
            ELSE 'nao eh palindromo' 
            END  

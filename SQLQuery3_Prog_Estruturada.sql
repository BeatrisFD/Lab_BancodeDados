/*Fazer um algoritmo que leia 3 valores e retorne se os valores formam um tri�ngulo e se ele �
is�celes, escaleno ou equil�tero.
Condi��es para formar um tri�ngulo
	Nenhum valor pode ser = 0
	Um lado n�o pode ser maior que a soma dos outros 2.
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


 -- Fazer um algoritmo que leia 1 n�mero e mostre se s�o m�ltiplos de 2,3,5 ou nenhum deles
declare @Z as int
set @Z = 53
if @Z % 2 = 0
	PRINT 'm�ltiplo de 2'
else if @Z % 3 = 0
	PRINT 'm�ltiplos de 3'
else if @Z % 5 = 0
	PRINT 'm�ltiplos de 5'
else 
	PRINT 'nenhum deles'

 -- Fazer um algoritmo que leia 3 n�mero e mostre o maior e o menor
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

set @menor = @F
if @G < @maior
	SELECT @menor = @G
if @H < @maior
	SELECT @menor = @H
	PRINT @maior
	PRINT @menor

/*
Fazer um algoritmo que calcule os 15 primeiros termos da s�rie
1,1,2,3,5,8,13,21,...
E calcule a soma dos 15 termos
*/


-- Fazer um algorimto que separa uma frase, colocando todas as letras em mai�sculo e em min�sculo


-- Fazer um algoritmo que inverta uma palavra


-- Verificar palindromo


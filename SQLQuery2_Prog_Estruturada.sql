SELECT DBO.CPF_VALIDO('42952898782')

CREATE FUNCTION CPF_VALIDO(@CPF VARCHAR(11))
RETURNS CHAR(1)
AS
BEGIN
  DECLARE @INDICE INT,
          @SOMA INT,
          @DIG1 INT,
          @DIG2 INT,
          @CPF_TEMP VARCHAR(11),
          @DIGITOS_IGUAIS CHAR(1),
          @RESULTADO CHAR(1)
          
  SET @RESULTADO = 'N'

  SET @CPF_TEMP = SUBSTRING(@CPF,1,1)

  SET @INDICE = 1
  SET @DIGITOS_IGUAIS = 'S'

  WHILE (@INDICE <= 11)
  BEGIN
    IF SUBSTRING(@CPF,@INDICE,1) <> @CPF_TEMP
      SET @DIGITOS_IGUAIS = 'N'
    SET @INDICE = @INDICE + 1
  END;

  IF @DIGITOS_IGUAIS = 'N'
  BEGIN
    SET @SOMA = 0
    SET @INDICE = 1
    WHILE (@INDICE <= 9)
    BEGIN
      SET @Soma = @Soma + CONVERT(INT,SUBSTRING(@CPF,@INDICE,1)) * (11 - @INDICE);
      SET @INDICE = @INDICE + 1
    END

    SET @DIG1 = 11 - (@SOMA % 11)

    IF @DIG1 > 9
      SET @DIG1 = 0;

    SET @SOMA = 0
    SET @INDICE = 1
    WHILE (@INDICE <= 10)
    BEGIN
      SET @Soma = @Soma + CONVERT(INT,SUBSTRING(@CPF,@INDICE,1)) * (12 - @INDICE);
      SET @INDICE = @INDICE + 1
    END

    SET @DIG2 = 11 - (@SOMA % 11)

    IF @DIG2 > 9
      SET @DIG2 = 0;

    IF (@DIG1 = SUBSTRING(@CPF,LEN(@CPF)-1,1)) AND (@DIG2 = SUBSTRING(@CPF,LEN(@CPF),1))
      SET @RESULTADO = 'S'
    ELSE
      SET @RESULTADO = 'N'
  END
  RETURN @RESULTADO
END

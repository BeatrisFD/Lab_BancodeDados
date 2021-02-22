create database Aula02_ExercicioUnion
go
use Aula02_ExercicioUnion

create table Curso (
Codigo_Curso int primary key not null,
Nome varchar(70) not null,
Sigla varchar(10) not null
)

create table Aluno (
Ra char(7) primary key not null,
Nome varchar(250) not null,
Codigo_Curso int not null,
foreign key (Codigo_Curso) references Curso(Codigo_Curso)
)

create table Palestrante (
Codigo_Palestrante int primary key identity not null,
Nome varchar(250) not null,
Empresa varchar(100) not null
)

create table Palestra (
Codigo_Palestra int primary key identity not null,
Titulo varchar(max) not null,
Carga_Horaria int not null,
aData datetime not null,
Codigo_Palestrante int not null,
foreign key (Codigo_Palestrante) references Palestrante(Codigo_Palestrante)
)

create table Alunos_Inscritos (
Ra char(7) not null,
Codigo_Palestra int not null,
foreign key (Codigo_Palestra) references Palestra(Codigo_Palestra),
foreign key (Ra) references Aluno(Ra),
primary key (Ra, Codigo_Palestra)
)

create table Nao_Alunos(
RG varchar(9) not null,
Orgao_Exp char(5) not null,
Nome varchar(250) not null,
primary key (RG, Orgao_Exp)
)

create table Nao_Alunos_Inscritos (
Codigo_Palestra int not null,
RG varchar(9) not null,
Orgao_Exp char(5) not null,
foreign key (RG, Orgao_Exp) references Nao_Alunos(RG, Orgao_Exp),
foreign key (Codigo_Palestra) references Palestra(Codigo_Palestra),
primary key (Codigo_Palestra, RG, Orgao_Exp)
)

create view v_inscritos as
select ai.Ra as num_doc, a.Nome, ai.Codigo_Palestra from Aluno a, Alunos_Inscritos ai
where a.Ra = ai.Ra
union
select na.RG + na.Orgao_Exp as num_doc, na.Nome, nai.Codigo_Palestra from Nao_Alunos na, Nao_Alunos_Inscritos nai
where na.RG = nai.RG

Select * from v_inscritos
order by Nome

create view v_palestras as
select v.*, Titulo, pl.Nome as Nome_Palestrante, Carga_Horaria, aData as Data 
from Palestra p, Palestrante pl, v_inscritos v
where pl.Codigo_Palestrante = p.Codigo_Palestrante and p.Codigo_Palestra = v.Codigo_Palestra

Select * from v_palestras
where Codigo_Palestra = 1
order by Nome
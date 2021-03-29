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


Aluno
123456 	maria	1
218784 	jaque	4
231980 	ze	3
274639 	danilo	3
285783 	fernando	5
293787 	karol	6
456780 	joao	2
723671 	hugo	2
840023 	raquel	4
891273 	gabriel	1
923787 	emili	6
987123 	leonardo	5
NULL	NULL	NULL


Alunos_Inscritos
123456 	1
218784 	1
231980 	3
274639 	3
285783 	9
293787 	9
456780 	10
723671 	10
840023 	1
891273 	3
923787 	9
987123 	10
NULL	NULL


Curso
1	analise e d. de sistemas	ads
2	logistica	log
3	comercio exterior	comex
4	educacao fisica	edf
5	enfermagem	enf
6	gestao empresarial	gemp
NULL	NULL	NULL


Nao_Alunos
511238790	SP   	Caio
546643542	SP   	beatris
578964235	SP   	Iasmin
645978526	SP   	Damiao
678945631	SP   	Lucas
691235468	SP   	Gabriela
756952365	SP   	Paloma
NULL	NULL	NULL


Nao_Alunos_Inscritos
1	546643542	SP   
1	645978526	SP   
3	578964235	SP   
3	678945631	SP   
9	691235468	SP   
9	756952365	SP   
10	511238790	SP   
10	546643542	SP   
NULL	NULL	NULL


Palestra
1	link corp	2	2021-04-10 00:00:00.000	1
3	Google 	4	2021-04-20 00:00:00.000	2
9	SAP	3	2021-04-27 00:00:00.000	4
10	Ambev Tech	2	2021-05-03 00:00:00.000	5
NULL	NULL	NULL	NULL	NULL


Palestrante
1	Luciano	link
2	Talita	Google
4	Luana	SAP
5	Eduardo	Ambev Tech
NULL	NULL	NULL
-

create Database exercicios

use exercicios


DROP TABLE CONSULTA;
DROP TABLE PACIENTE;
DROP TABLE MEDICO;
DROP TABLE AMBULATORIO;


--SET SEARCH_PATH TO DEMO_CLINICA;

--COMPLETAR A CRIACAO DA TABELA AMBULATORIO

CREATE TABLE AMBULATORIO (
NUM_A INT,
ANDAR  NUMERIC(3) NOT NULL,
CAPACIDADE SMALLINT NOT NULL

PRIMARY KEY (NUM_A),
);


CREATE TABLE MEDICO (
COD_M INT,
NOME VARCHAR(40) NOT NULL,
IDADE SMALLINT NOT NULL,
ESPECIALIDADE CHAR(20),
CIDADE VARCHAR(30),
NUM_A INT,

PRIMARY KEY (COD_M),
FOREIGN KEY (NUM_A) REFERENCES AMBULATORIO(NUM_A)
);

CREATE TABLE PACIENTE(
COD_P INT,
NOME VARCHAR(40) NOT NULL,
IDADE SMALLINT NOT NULL,
CIDADE VARCHAR(30),
PRIMARY KEY (COD_P));

CREATE TABLE CONSULTA(
COD_M INT,
COD_P INT,
DATA DATE,
HORA TIME,

PRIMARY KEY (COD_M, DATA, HORA),
FOREIGN KEY (COD_M) REFERENCES MEDICO(COD_M),
FOREIGN KEY (COD_P) REFERENCES PACIENTE(COD_P)
);


--COMPLETAR A INICIALIZACAO DA TABELA AMBULATORIO

INSERT INTO AMBULATORIO VALUES
(1, 1, 30),
(2,1,50),
(3,2,40),
(4,2,25),
(5,2,55),
(6,2,10),
(7,2,10); 


INSERT INTO MEDICO VALUES 
(1,'Joao',40,'ortopedista', 'Florianopolis', 1); 
INSERT INTO MEDICO VALUES 
(2,'Maria',42,'oftalmologista', 'Blumenau', 2);
INSERT INTO MEDICO VALUES 
(3,'Pedro',51,'pediatra', 'Sao Jose', 2); 
INSERT INTO MEDICO VALUES 
(4,'Carlos',28,'ortopedista', 'Florianopolis', 4); 
INSERT INTO MEDICO VALUES 
(5,'Marcia',33,'neurologista', 'Florianopolis', 3); 
INSERT INTO MEDICO VALUES 
(6,'Pedrinho',38,'infectologista', 'Blumenau', 1); 
INSERT INTO MEDICO VALUES 
(7,'Mariana',39,'infectologista', 'Florianopolis', NULL);
INSERT INTO MEDICO VALUES 
(8,'Roberta',50,'neurologista', 'Joinville', 5); 

INSERT INTO MEDICO VALUES 
(9,'Vanusa',39,'aa', 'Curitiba', NULL);
INSERT INTO MEDICO VALUES 
(10,'Valdo',50,'aa', 'Curitiba', NULL); 


INSERT INTO PACIENTE VALUES 
(1,'Clevi',60,'Florianopolis'); 
INSERT INTO PACIENTE VALUES 
(2,'Cleusa',25,'Palhoca'); 
INSERT INTO PACIENTE VALUES 
(3,'Alberto',45,'Palhoca'); 
INSERT INTO PACIENTE VALUES 
(4,'Roberta',44,'Sao Jose'); 
INSERT INTO PACIENTE VALUES 
(5,'Fernanda',3,'Sao Jose'); 
INSERT INTO PACIENTE VALUES 
(6,'Luanda',2,'Florianopolis'); 
INSERT INTO PACIENTE VALUES 
(7,'Manuela',69,'Florianopolis'); 
INSERT INTO PACIENTE VALUES 
(8,'Caio',45,'Florianopolis'); 
INSERT INTO PACIENTE VALUES 
(9,'Andre',83,'Florianopolis'); 
INSERT INTO PACIENTE VALUES 
(10,'Andre',21,'Florianopolis'); 


INSERT INTO CONSULTA VALUES (1,3,  '2000/06/12','14:00'); 
INSERT INTO CONSULTA VALUES (4,3,  '2000/06/13','9:00'); 
INSERT INTO CONSULTA VALUES (2,9,  '2000/06/14','14:00'); 
INSERT INTO CONSULTA VALUES (7,5,  '2000/06/12','10:00'); 
INSERT INTO CONSULTA VALUES (8,6,  '2000/06/19','13:00'); 
INSERT INTO CONSULTA VALUES (5,1,  '2000/06/20','13:00'); 
INSERT INTO CONSULTA VALUES (6,8,  '2000/06/20','20:30'); 
INSERT INTO CONSULTA VALUES (6,2,  '2000/06/15','11:00'); 
INSERT INTO CONSULTA VALUES (6,4,  '2000/06/15','14:00'); 
INSERT INTO CONSULTA VALUES (7,2,  '2000/06/10','19:30'); 



--Modifique a cidade dos médicos de Blumenau para Biguaçu. 

UPDATE MEDICO
SET CIDADE = 'Blumenau'
WHERE CIDADE = 'Biguacu'

--Modifique a cidade dos médicos de Biguaçu para Blumenau .

UPDATE MEDICO
SET CIDADE = 'Biguacu'
WHERE CIDADE = 'Blumenau'

--Modifique a capacidade do ambulatório 1 para 35

UPDATE AMBULATORIO
SET CAPACIDADE = 35
WHERE NUM_A = 1



--Remova todos os médicos de Curidba. 

DELETE
FROM MEDICO
WHERE CIDADE = 'Curitiba'


--Remova todos os ambulatórios com capacidade 10.

DELETE
FROM AMBULATORIO
WHERE CAPACIDADE = 10


--Obter os médicos de Florianópolis. 

SELECT *
FROM MEDICO
WHERE CIDADE = 'Florianopolis'

--Obter o código do médico com nome Marcia.

SELECT COD_M
FROM MEDICO
WHERE NOME = 'Marcia'


--Obter as especialidades dos médicos de Florianópolis, eliminando as repedções. 

SELECT DISTINCT ESPECIALIDADE
FROM MEDICO


--Obter a data da consulta do paciente Caio. 

SELECT DATA
FROM CONSULTA C
JOIN PACIENTE P
ON P.COD_P = C.COD_P
WHERE P.NOME = 'Caio'



--Obter os nomes dos pacientes do médico Pedrinho

SELECT P.NOME
FROM MEDICO M
JOIN CONSULTA C
ON M.COD_M = C.COD_M
JOIN PACIENTE P
ON C.COD_P = P.COD_P
WHERE M.NOME = 'Pedrinho'


--Obter os nomes dos médicos que tem consulta marcada e as datas das suas consultas  

SELECT DISTINCT M.NOME
FROM MEDICO M
RIGHT JOIN CONSULTA C
ON M.COD_M = C.COD_M


--Obter os nomes dos médicos infectologistas e o andar do ambulatório em que eles atendem. 

SELECT M.NOME, A.ANDAR
FROM MEDICO M
LEFT JOIN AMBULATORIO A
ON M.NUM_A = A.	NUM_A
WHERE M.ESPECIALIDADE = 'Infectologista'

--Obter os nomes dos pacientes que tem consulta marcada no ambulatório 2. 

SELECT P.NOME
FROM PACIENTE P
JOIN CONSULTA C
ON P.COD_P = C.COD_P
JOIN MEDICO M
ON C.COD_M = M.COD_M
WHERE M.NUM_A = '2'


--Obter o nome de cada médico e respecdvos nomes dos pacientes que tem consulta com ele (caso o médico não tenha nenhuma consulta, o nome do paciente deve ser prenchido com null). 

SELECT M.NOME, P.NOME
FROM MEDICO M
FULL JOIN CONSULTA C
ON M.COD_M = C.COD_M
LEFT JOIN PACIENTE P
ON C.COD_P = P.COD_P

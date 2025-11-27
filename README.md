## TRABAJO PRACTICO COMPILADORES
Entrega de TP3 Y TP4, con reentrega del TP1

### Grupo 15 - Integrantes:
* Lopez Milagros
* Arriaga Maria Eugenia
* Rizzalli Bianca

### Proyecto:
Este projecto contiene:
- Compilador en Java
- Informe del TP1 y TP2 (Debido a la reentrega)
- Informe del TP2 y TP4


### Instrucciones para la ejecución del programa:

1. En una terminal clonar el repositorio ingresando: 
```bash
cd /tu/carpeta
git clone https://github.com/lopezmilagros/TPE_Compiladores.git
```
2. Ir a la carpeta donde se encuentra el .jar
```bash
cd out/artifacts/Compiladores_jar/
```
3. Ejecutar el compilador
```bash
java -jar Compiladores.jar
```
-- Nota: En caso de querer ejecutar BYACC, se debera anañadir en la clase autogenerada ParserVaL la linea
```bash
package AnalizadorSintactico
```
al  comienzo del archivo, ya que no se agrega sola, y necesita estar en ese packete para poder compilar.
## Descripción del Proyecto

Este proyecto corresponde a la entrega final del trabajo especial de la materia Compiladores, Ingenieria en Sistemas.
Consiste en la implementación completa de un compilador escrito en Java, diseñado para procesar un lenguaje de alto nivel definido por la cátedra.

El compilador realiza todas las etapas clásicas del proceso de compilación:

- Análisis léxico
- Análisis sintáctico
- Análisis semántico
- Generación de código Assembler para arquitectura Pentium de 32 bits (salida: archivos.asm)

Además, incorpora un sistema de manejo de errores que detecta y reporta fallas tanto en tiempo de compilación (léxicas, sintácticas y semánticas) como en tiempo de ejecución (por ejemplo, división por cero), cumpliendo con las especificaciones provistas por la cátedra.

La ejecución del compilador se realiza a partir de un archivo de entrada. El repositorio incluye múltiples test de ejemplo: casos correctos, casos con errores léxicos, semánticos, errores de ejecución y también permite ingresar cualquier archivo .txt adicional para compilar desde la consola.
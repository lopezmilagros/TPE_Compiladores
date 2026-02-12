.586
.MODEL flat, stdcall
option casemap:none

include \masm32\include\windows.inc
include \masm32\include\user32.inc
include \masm32\include\kernel32.inc
include \masm32\include\masm32.inc
includelib \masm32\lib\user32.lib
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\masm32.lib

.DATA
IMPRESIONES DB 20 dup(0)
FORMATO db "%d",0
_MAIN_SUMA_A DD ?
_MAIN_Y DD ?
_MAIN_SUMA_B DD ?
_MAIN_SUMA_J DD ?
_MAIN_H DD ?
msj1 db "Buen dia, buenas tardes, buenas noches! Soy el programa 1", 0
@AUX1 DQ 8.0E+2
@AUX2 DD ?
msj2 db "Valor de suma: ", 0
@AUX3 DQ 1.1
@AUX4 DD ?
msj3 db "ERROR: La suma ha exedido el rango del tipo utilizado", 0
@AUX5 DD ?

.CODE
START:

; impresion de mensajes
invoke MessageBox, NULL, addr msj1, addr msj1, MB_OK
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; cargar operandos en registros
MOV EAX,_MAIN_H
FLD @AUX1 ; cargo dfloat a la pila
FISTP @AUX2 ; convierto a ulong y lo guardo en aux
MOV EBX, @AUX2

; asignacion
MOV _MAIN_H, EBX

; cargar operandos en registros
MOV EAX,_MAIN_Y
MOV EBX,7

; asignacion
MOV _MAIN_Y, EBX

; cargar operandos en registros
MOV EAX,_MAIN_SUMA_A
MOV EBX,_MAIN_H

; asignacion de parametros
MOV _MAIN_SUMA_A, EBX

; cargar operandos en registros
MOV EAX,_MAIN_SUMA_B
MOV EBX,_MAIN_H

; asignacion de parametros
MOV _MAIN_SUMA_B, EBX

; llamado a funcion
CALL MAIN_SUMA

; cargar operandos en registros
MOV EAX,_MAIN_H
MOV EBX,_MAIN_SUMA_A

; asignacion copia-valor-resultado al retornar 
MOV _MAIN_H, EBX

; cargar operandos en registros
MOV EAX,_MAIN_H
; asignacion del retorno de la funcion
MOV EBX, @AUX5

; asignacion
MOV _MAIN_H, EBX

; impresion de mensajes
invoke MessageBox, NULL, addr msj2, addr msj2, MB_OK
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; impresion de mensajes
MOV EAX, _MAIN_H
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; impresion de mensajes
FLD @AUX3 ; cargo dfloat a la pila
FISTP @AUX4 ; convierto a ulong y lo guardo en aux
MOV EAX, _1.1
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
JMP FIN

; comienza MAIN:SUMA-------------------------
MAIN_SUMA:

; cargar operandos en registros
MOV EAX,_MAIN_SUMA_B
MOV EBX,_MAIN_SUMA_A

; suma
ADD EAX, EBX
JC ERROR1
MOV @AUX5, EAX

; cargar operandos en registros
MOV EAX,_MAIN_SUMA_J
MOV EBX,@AUX5

; asignacion
MOV _MAIN_SUMA_J, EBX
RET

; manejo de errores
ERROR1:
invoke MessageBox, NULL, addr msj3, addr msj3, MB_OK
JMP FIN

FIN:
 invoke ExitProcess, 0
END START

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
_MAIN_DENTROM DD ?
_MAIN_Y DD ?
_MAIN_IMPRIMIR_X DD ?
_MAIN_FUNCION4_H DD ?
_MAIN_FUNCION_DENTRO DD ?
_MAIN_IMPRIMIR2_DENTRO DD ?
_MAIN_X DD ?
_MAIN_FUNCION_FUNCION3_Y DD ?
_MAIN_FUNCION_Y DD ?
_MAIN_FUNCION_FUNCION3_DENTRO2 DD ?
_MAIN_IMPRIMIR2_X DD ?
msj1 db "ERROR: La suma ha exedido el rango del tipo utilizado", 0
@AUX1 DD ?
msj2 db "hola", 0

.CODE
START:

; cargar operandos en registros
MOV EAX,_MAIN_X
MOV EBX,6

; asignacion
MOV _MAIN_X, EBX

; cargar operandos en registros
MOV EAX,_MAIN_Y
MOV EBX,6

; asignacion
MOV _MAIN_Y, EBX

; cargar operandos en registros
MOV EAX,_MAIN_Y
MOV EBX,10

; asignacion de parametros
MOV _MAIN_FUNCION_Y, EBX

; llamado a funcion
CALL MAIN_FUNCION

; cargar operandos en registros
MOV EAX,_MAIN_DENTROM
; asignacion del retorno de la funcion
MOV EBX, @AUX1

; asignacion
MOV _MAIN_DENTROM, EBX

; impresion de mensajes
MOV EAX, _MAIN_DENTROM
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; cargar operandos en registros
MOV EAX,_MAIN_X
MOV EBX,_MAIN_X

; asignacion de parametros
MOV _MAIN_IMPRIMIR2_X, EBX

; llamado a funcion
CALL MAIN_IMPRIMIR2

; cargar operandos en registros
MOV EAX,_MAIN_X
; asignacion del retorno de la funcion
MOV EBX, @AUX1

; asignacion
MOV _MAIN_X, EBX

; impresion de mensajes
MOV EAX, _MAIN_X
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
JMP FIN

; comienza MAIN:FUNCION:FUNCION3-------------------------
MAIN_FUNCION_FUNCION3:

; cargar operandos en registros
MOV EAX,_MAIN_FUNCION_DENTRO
MOV EBX,_MAIN_X

; suma
ADD EAX, EBX
JC ERROR1
MOV @AUX1, EAX

; cargar operandos en registros
MOV EAX,_MAIN_FUNCION_FUNCION3_Y
MOV EBX,@AUX1

; asignacion
MOV _MAIN_FUNCION_FUNCION3_Y, EBX

; cargar operandos en registros
MOV EAX,_MAIN_FUNCION_FUNCION3_Y
MOV EBX,_MAIN_FUNCION_FUNCION3_Y

MOV @AUX1, EBX
RET

; comienza MAIN:IMPRIMIR2-------------------------
MAIN_IMPRIMIR2:

; impresion de mensajes
MOV EAX, _MAIN_IMPRIMIR2_X
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; cargar operandos en registros
MOV EAX,_MAIN_IMPRIMIR2_X
MOV EBX,_MAIN_IMPRIMIR2_X

MOV @AUX1, EBX
RET

; comienza MAIN:FUNCION-------------------------
MAIN_FUNCION:

; cargar operandos en registros
MOV EAX,_MAIN_FUNCION_DENTRO
MOV EBX,3

; asignacion
MOV _MAIN_FUNCION_DENTRO, EBX

; impresion de mensajes
MOV EAX, _MAIN_FUNCION_DENTRO
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; cargar operandos en registros
MOV EAX,_MAIN_FUNCION_DENTRO
MOV EBX,_MAIN_FUNCION_DENTRO

MOV @AUX1, EBX
RET

; comienza MAIN:FUNCION4-------------------------
MAIN_FUNCION4:

; impresion de mensajes
invoke MessageBox, NULL, addr msj2, addr msj2, MB_OK

; cargar operandos en registros
MOV EAX,_MAIN_FUNCION4_H
MOV EBX,_MAIN_FUNCION4_H

MOV @AUX1, EBX
RET

; comienza MAIN:IMPRIMIR-------------------------
MAIN_IMPRIMIR:

; impresion de mensajes
MOV EAX, _MAIN_IMPRIMIR_X
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; cargar operandos en registros
MOV EAX,_MAIN_IMPRIMIR_X
MOV EBX,_MAIN_IMPRIMIR_X

MOV @AUX1, EBX
RET

; manejo de errores
ERROR1:
invoke MessageBox, NULL, addr msj1, addr msj1, MB_OK
JMP FIN

FIN:
 invoke ExitProcess, 0
END START

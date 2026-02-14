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
_MAIN_Z DD ?
_MAIN_Y DD ?
_MAIN_X DD ?
msj1 db "inicio", 0
msj2 db "imprimio el 1.1", 0
msj3 db "ERROR: La multiplicacion ha exedido el rango del tipo utilizado", 0
@AUX1 DD ?
msj4 db "ERROR: La suma ha exedido el rango del tipo utilizado", 0
@AUX2 DD ?
msj5 db "ERROR: No se puede realizar la division por cero", 0
@AUX3 DD ?
msj6 db "ERROR: La resta ha exedido el rango del tipo utilizado", 0
@AUX4 DD ?
msj7 db "ERROR: No es posible convertir dfloat negativo '-1.2' a entero sin signo", 0
msj8 db "ERROR: No es posible convertir dfloat negativo '-1.2' a entero sin signo", 0
msj9 db "ERROR: La multiplicacion ha exedido el rango del tipo utilizado", 0
@AUX5 DD ?

.CODE
START:

; impresion de mensajes
invoke MessageBox, NULL, addr msj1, addr msj1, MB_OK
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; impresion de mensajes
invoke MessageBox, NULL, addr msj2, addr msj2, MB_OK
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; cargar operandos en registros
MOV EAX,_MAIN_X
MOV EBX,10

; asignacion
MOV _MAIN_X, EBX

; cargar operandos en registros
MOV EAX,2
MOV EBX,_MAIN_X

; multiplicacion
MUL EBX
JC ERROR1
MOV @AUX1, EAX

; cargar operandos en registros
MOV EAX,@AUX1
MOV EBX,1

; suma
ADD EAX, EBX
JC ERROR2
MOV @AUX2, EAX

; cargar operandos en registros
MOV EAX,_MAIN_X
MOV EBX,2

; division
CMP EBX, 0
JZ ERROR3
MOV EDX, 0
DIV EBX
MOV @AUX3, EAX

; cargar operandos en registros
MOV EAX,@AUX2
MOV EBX,@AUX3

; resta
SUB EAX, EBX
JC ERROR4
MOV @AUX4, EAX

; cargar operandos en registros
MOV EAX,_MAIN_Y
MOV EBX,@AUX4

; asignacion
MOV _MAIN_Y, EBX

; cargar operandos en registros
MOV EAX,_MAIN_X
JMP ERROR5
; asignacion
MOV _MAIN_X, EBX

; cargar operandos en registros
JMP ERROR6
MOV EBX,_MAIN_X

; multiplicacion
MUL EBX
JC ERROR7
MOV @AUX5, EAX

; cargar operandos en registros
MOV EAX,_MAIN_Y
MOV EBX,@AUX5

; asignacion
MOV _MAIN_Y, EBX

; impresion de mensajes
MOV EAX, _MAIN_X
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; impresion de mensajes
MOV EAX, _MAIN_Y
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
JMP FIN

; manejo de errores
ERROR1:
invoke MessageBox, NULL, addr msj3, addr msj3, MB_OK
JMP FIN
ERROR2:
invoke MessageBox, NULL, addr msj4, addr msj4, MB_OK
JMP FIN
ERROR3:
invoke MessageBox, NULL, addr msj5, addr msj5, MB_OK
JMP FIN
ERROR4:
invoke MessageBox, NULL, addr msj6, addr msj6, MB_OK
JMP FIN
ERROR5:
invoke MessageBox, NULL, addr msj7, addr msj7, MB_OK
JMP FIN
ERROR6:
invoke MessageBox, NULL, addr msj8, addr msj8, MB_OK
JMP FIN
ERROR7:
invoke MessageBox, NULL, addr msj9, addr msj9, MB_OK
JMP FIN

FIN:
 invoke ExitProcess, 0
END START

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
@AUX1 DQ 1.1
@AUX2 DD ?
msj2 db "ERROR: La suma ha exedido el rango del tipo utilizado", 0
@AUX3 DD ?
msj3 db "ERROR: La multiplicacion ha exedido el rango del tipo utilizado", 0
@AUX4 DD ?
msj4 db "ERROR: La resta ha exedido el rango del tipo utilizado", 0
@AUX5 DD ?
msj5 db "ERROR: No se puede realizar la division por cero", 0
@AUX6 DD ?

.CODE
START:

; impresion de mensajes
invoke MessageBox, NULL, addr msj1, addr msj1, MB_OK
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; impresion de mensajes
FLD @AUX1 ; cargo dfloat a la pila
FISTP @AUX2 ; convierto a ulong y lo guardo en aux
MOV EAX, _1.1
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; cargar operandos en registros
MOV EAX,_MAIN_X
MOV EBX,10

; asignacion
MOV _MAIN_X, EBX

; cargar operandos en registros
MOV EAX,_MAIN_X
MOV EBX,1

; suma
ADD EAX, EBX
JC ERROR1
MOV @AUX3, EAX

; cargar operandos en registros
MOV EAX,2
MOV EBX,@AUX3

; multiplicacion
MUL EBX
JC ERROR2
MOV @AUX4, EAX

; cargar operandos en registros
MOV EAX,_MAIN_X
MOV EBX,@AUX4

; resta
SUB EAX, EBX
JC ERROR3
MOV @AUX5, EAX

; cargar operandos en registros
MOV EAX,@AUX5
MOV EBX,2

; division
CMP EBX, 0
JZ ERROR4
MOV EDX, 0
DIV EBX
MOV @AUX6, EAX

; cargar operandos en registros
MOV EAX,_MAIN_Y
MOV EBX,@AUX6

; asignacion
MOV _MAIN_Y, EBX

; impresion de mensajes
MOV EAX, _MAIN_Y
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
JMP FIN

; manejo de errores
ERROR1:
invoke MessageBox, NULL, addr msj2, addr msj2, MB_OK
JMP FIN
ERROR2:
invoke MessageBox, NULL, addr msj3, addr msj3, MB_OK
JMP FIN
ERROR3:
invoke MessageBox, NULL, addr msj4, addr msj4, MB_OK
JMP FIN
ERROR4:
invoke MessageBox, NULL, addr msj5, addr msj5, MB_OK
JMP FIN

FIN:
 invoke ExitProcess, 0
END START

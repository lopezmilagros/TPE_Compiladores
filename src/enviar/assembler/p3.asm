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
msj1 db "ERROR: No es posible convertir dfloat negativo '-1.2' a entero sin signo", 0
msj2 db "ERROR: No es posible convertir dfloat negativo '-1.2' a entero sin signo", 0
msj3 db "ERROR: La multiplicacion ha exedido el rango del tipo utilizado", 0
@AUX1 DD ?

.CODE
START:

; cargar operandos en registros
MOV EAX,_MAIN_X
JMP ERROR1

; asignacion
MOV _MAIN_X, EBX

; cargar operandos en registros
JMP ERROR2
MOV EBX,_MAIN_X

; multiplicacion
MUL EBX
JC ERROR3
MOV @AUX1, EAX

; cargar operandos en registros
MOV EAX,_MAIN_Y
MOV EBX,@AUX1

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
invoke MessageBox, NULL, addr msj1, addr msj1, MB_OK
JMP FIN
ERROR2:
invoke MessageBox, NULL, addr msj2, addr msj2, MB_OK
JMP FIN
ERROR3:
invoke MessageBox, NULL, addr msj3, addr msj3, MB_OK
JMP FIN

FIN:
 invoke ExitProcess, 0
END START

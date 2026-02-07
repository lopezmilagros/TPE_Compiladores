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
msj2 db "ERROR: La resta ha exedido el rango del tipo utilizado", 0
@AUX1 DD ?

.CODE
START:

; impresion de mensajes
invoke MessageBox, NULL, addr msj1, addr msj1, MB_OK
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; cargar operandos en registros
MOV EAX,_MAIN_X
MOV EBX,10

; asignacion
MOV _MAIN_X, EBX

; cargar operandos en registros
MOV EAX,1
MOV EBX,_MAIN_X

; resta
SUB EAX, EBX
JC ERROR1
MOV @AUX1, EAX

; cargar operandos en registros
MOV EAX,_MAIN_Y
MOV EBX,@AUX1

; asignacion
MOV _MAIN_Y, EBX

; impresion de mensajes
MOV EAX, _MAIN_Y
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; impresion de mensajes
MOV EAX, _MAIN_X
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
JMP FIN

; manejo de errores
ERROR1:
invoke MessageBox, NULL, addr msj2, addr msj2, MB_OK
JMP FIN

FIN:
 invoke ExitProcess, 0
END START

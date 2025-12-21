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
_MAIN_N DD ?
_MAIN_ACC DD ?
msj1 db "Bienvenido al programa 4!", 0
msj2 db "ERROR: La suma ha exedido el rango del tipo utilizado", 0
@AUX1 DD ?
msj3 db "ERROR: La resta ha exedido el rango del tipo utilizado", 0
@AUX2 DD ?
msj4 db "Suma de 1..10: ", 0

.CODE
START:

; impresion de mensajes
invoke MessageBox, NULL, addr msj1, addr msj1, MB_OK

; cargar operandos en registros
MOV EAX,_MAIN_N
MOV EBX,10

; asignacion
MOV _MAIN_N, EBX

; cargar operandos en registros
MOV EAX,_MAIN_ACC
MOV EBX,0

; asignacion
MOV _MAIN_ACC, EBX
LABEL0:

; cargar operandos en registros
MOV EAX,0
MOV EBX,_MAIN_N

CMP EAX, EBX
JA LABEL1

; cargar operandos en registros
MOV EAX,_MAIN_N
MOV EBX,_MAIN_ACC

; suma
ADD EAX, EBX
JC ERROR1
MOV @AUX1, EAX

; cargar operandos en registros
MOV EAX,_MAIN_ACC
MOV EBX,@AUX1

; asignacion
MOV _MAIN_ACC, EBX

; cargar operandos en registros
MOV EAX,1
MOV EBX,_MAIN_N

; resta
SUB EAX, EBX
JC ERROR2
MOV @AUX2, EAX

; cargar operandos en registros
MOV EAX,_MAIN_N
MOV EBX,@AUX2

; asignacion
MOV _MAIN_N, EBX
JMP LABEL0
LABEL1:

; impresion de mensajes
invoke MessageBox, NULL, addr msj4, addr msj4, MB_OK

; impresion de mensajes
MOV EAX, _MAIN_ACC
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

FIN:
 invoke ExitProcess, 0
END START

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
_MAIN_LAMBDA0_V DD ?
msj1 db "Programa 6: lambdas y if", 0
msj2 db "Chequeando V...", 0
msj3 db "V es cero", 0
msj4 db "V es chico", 0
msj5 db "V es grande", 0
msj6 db "Valor V: ", 0

.CODE
START:

; impresion de mensajes
invoke MessageBox, NULL, addr msj1, addr msj1, MB_OK

; cargar operandos en registros
MOV EAX,_MAIN_LAMBDA0_V
MOV EBX,3

; asignacion de parametros
MOV _MAIN_LAMBDA0_V, EBX

; llamado a funcion
CALL MAIN_LAMBDA0
JMP FIN

; comienza MAIN:LAMBDA0-------------------------
MAIN_LAMBDA0:

; impresion de mensajes
invoke MessageBox, NULL, addr msj2, addr msj2, MB_OK

; cargar operandos en registros
MOV EAX,0
MOV EBX,_MAIN_LAMBDA0_V

CMP EAX, EBX
JE LABEL0

; impresion de mensajes
invoke MessageBox, NULL, addr msj3, addr msj3, MB_OK
LABEL0:

; cargar operandos en registros
MOV EAX,5
MOV EBX,_MAIN_LAMBDA0_V

CMP EAX, EBX
JB LABEL1

; impresion de mensajes
invoke MessageBox, NULL, addr msj4, addr msj4, MB_OK
JMP LABEL2
LABEL1:

; impresion de mensajes
invoke MessageBox, NULL, addr msj5, addr msj5, MB_OK
JMP LABEL3
LABEL2:
LABEL3:

; impresion de mensajes
invoke MessageBox, NULL, addr msj6, addr msj6, MB_OK

; impresion de mensajes
MOV EAX, _MAIN_LAMBDA0_V
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
RET

; manejo de errores

FIN:
 invoke ExitProcess, 0
END START

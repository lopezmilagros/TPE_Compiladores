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
_MAIN_LAMBDA0_A DD ?
_MAIN_LAMBDA1_A DD ?
msj1 db "Espero que tengas un lindo dia! soy el programa 2", 0
msj2 db "Primera lambda", 0
msj3 db "else", 0
msj4 db "Segunda lambda", 0

.CODE
START:

; impresion de mensajes
invoke MessageBox, NULL, addr msj1, addr msj1, MB_OK

; cargar operandos en registros
MOV EAX,_MAIN_LAMBDA0_A
MOV EBX,3

; asignacion de parametros
MOV _MAIN_LAMBDA0_A, EBX

; llamado a funcion
CALL MAIN_LAMBDA0

; cargar operandos en registros
MOV EAX,_MAIN_LAMBDA1_A
MOV EBX,3

; asignacion de parametros
MOV _MAIN_LAMBDA1_A, EBX

; llamado a funcion
CALL MAIN_LAMBDA1
JMP FIN

; comienza MAIN:LAMBDA0-------------------------
MAIN_LAMBDA0:

; cargar operandos en registros
MOV EAX,1
MOV EBX,_MAIN_LAMBDA0_A

CMP EAX, EBX
JA LABEL0

; impresion de mensajes
invoke MessageBox, NULL, addr msj2, addr msj2, MB_OK
JMP LABEL1
LABEL0:

; impresion de mensajes
invoke MessageBox, NULL, addr msj3, addr msj3, MB_OK
LABEL1:
RET

; comienza MAIN:LAMBDA1-------------------------
MAIN_LAMBDA1:

; impresion de mensajes
invoke MessageBox, NULL, addr msj4, addr msj4, MB_OK

; impresion de mensajes
MOV EAX, _MAIN_LAMBDA1_A
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
RET

; manejo de errores

FIN:
 invoke ExitProcess, 0
END START

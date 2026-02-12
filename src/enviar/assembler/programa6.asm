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
_MAIN_X DD ?
msj1 db "Programa 7: control combinado", 0
msj2 db "Entré al if", 0
msj3 db "X llego a 3", 0
msj4 db "X distinto de 3", 0
msj5 db "ERROR: La suma ha exedido el rango del tipo utilizado", 0
@AUX1 DD ?
msj6 db "No entro al if", 0
msj7 db "Fin programa 7", 0

.CODE
START:

; impresion de mensajes
invoke MessageBox, NULL, addr msj1, addr msj1, MB_OK
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; cargar operandos en registros
MOV EAX,_MAIN_X
MOV EBX,1

; asignacion
MOV _MAIN_X, EBX

; cargar operandos en registros
MOV EAX,4
MOV EBX,_MAIN_X

CMP EAX, EBX
JB LABEL4

; impresion de mensajes
invoke MessageBox, NULL, addr msj2, addr msj2, MB_OK
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
LABEL0:

; cargar operandos en registros
MOV EAX,6
MOV EBX,_MAIN_X

CMP EAX, EBX
JB LABEL3

; cargar operandos en registros
MOV EAX,3
MOV EBX,_MAIN_X

CMP EAX, EBX
JE LABEL1

; impresion de mensajes
invoke MessageBox, NULL, addr msj3, addr msj3, MB_OK
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
JMP LABEL2
LABEL1:

; impresion de mensajes
invoke MessageBox, NULL, addr msj4, addr msj4, MB_OK
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
LABEL2:

; impresion de mensajes
MOV EAX, _MAIN_X
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; cargar operandos en registros
MOV EAX,1
MOV EBX,_MAIN_X

; suma
ADD EAX, EBX
JC ERROR1
MOV @AUX1, EAX

; cargar operandos en registros
MOV EAX,_MAIN_X
MOV EBX,@AUX1

; asignacion
MOV _MAIN_X, EBX
JMP LABEL0
LABEL3:
JMP LABEL5
LABEL4:

; impresion de mensajes
invoke MessageBox, NULL, addr msj6, addr msj6, MB_OK
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
LABEL5:

; impresion de mensajes
invoke MessageBox, NULL, addr msj7, addr msj7, MB_OK
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
JMP FIN

; manejo de errores
ERROR1:
invoke MessageBox, NULL, addr msj5, addr msj5, MB_OK
JMP FIN

FIN:
 invoke ExitProcess, 0
END START

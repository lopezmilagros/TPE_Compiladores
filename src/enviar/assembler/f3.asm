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
_MAIN_SUMAR_PY DD ?
_MAIN_SUMAR_PX DD ?
_MAIN_X DD ?
msj1 db "ERROR: La suma ha exedido el rango del tipo utilizado", 0
@AUX1 DD ?
msj2 db "ERROR: La suma ha exedido el rango del tipo utilizado", 0
@AUX2 DD ?
msj3 db "ERROR: La suma ha exedido el rango del tipo utilizado", 0
@AUX3 DD ?

.CODE
START:

; cargar operandos en registros
MOV EAX,_MAIN_X
MOV EBX,6

; asignacion
MOV _MAIN_X, EBX

; cargar operandos en registros
MOV EAX,_MAIN_Y
MOV EBX,20

; asignacion
MOV _MAIN_Y, EBX

; cargar operandos en registros
MOV EAX,_MAIN_SUMAR_PX
MOV EBX,_MAIN_X

; asignacion de parametros
MOV _MAIN_SUMAR_PX, EBX

; cargar operandos en registros
MOV EAX,_MAIN_SUMAR_PY
MOV EBX,_MAIN_Y

; asignacion de parametros
MOV _MAIN_SUMAR_PY, EBX

; llamado a funcion
CALL MAIN_SUMAR

; cargar operandos en registros
MOV EAX,_MAIN_Z
; asignacion del retorno de la funcion
MOV EBX, @AUX3

; asignacion
MOV _MAIN_Z, EBX

; impresion de mensajes
MOV EAX, _MAIN_Z
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
JMP FIN

; comienza MAIN:SUMAR-------------------------
MAIN_SUMAR:

; impresion de mensajes
MOV EAX, _MAIN_X
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; impresion de mensajes
MOV EAX, _MAIN_Y
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; cargar operandos en registros
MOV EAX,1
MOV EBX,_MAIN_SUMAR_PX

; suma
ADD EAX, EBX
JC ERROR1
MOV @AUX1, EAX

; cargar operandos en registros
MOV EAX,_MAIN_SUMAR_PX
MOV EBX,@AUX1

; asignacion
MOV _MAIN_SUMAR_PX, EBX

; cargar operandos en registros
MOV EAX,1
MOV EBX,_MAIN_SUMAR_PY

; suma
ADD EAX, EBX
JC ERROR2
MOV @AUX2, EAX

; cargar operandos en registros
MOV EAX,_MAIN_SUMAR_PY
MOV EBX,@AUX2

; asignacion
MOV _MAIN_SUMAR_PY, EBX

; impresion de mensajes
MOV EAX, _MAIN_SUMAR_PX
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; impresion de mensajes
MOV EAX, _MAIN_SUMAR_PY
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; cargar operandos en registros
MOV EAX,_MAIN_SUMAR_PY
MOV EBX,_MAIN_SUMAR_PX

; suma
ADD EAX, EBX
JC ERROR3
MOV @AUX3, EAX
RET

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

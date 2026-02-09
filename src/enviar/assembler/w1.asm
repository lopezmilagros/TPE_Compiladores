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
_MAIN_FACTORIAL_TOTAL DD ?
_MAIN_FACTORIAL_NUM DD ?
_MAIN_FACTORIAL_I DD ?
_MAIN_FACTORIAL_RESFACT DD ?
_MAIN_FACTORIAL_RES DD ?
_MAIN_FACTORIAL_N DD ?
msj1 db "ERROR: La suma ha exedido el rango del tipo utilizado", 0
@AUX1 DD ?
msj2 db "ERROR: La multiplicacion ha exedido el rango del tipo utilizado", 0
@AUX2 DD ?

.CODE
START:

; comienza MAIN:FACTORIAL-------------------------
MAIN_FACTORIAL:

; cargar operandos en registros
MOV EAX,_MAIN_FACTORIAL_I
MOV EBX,3

; asignacion
MOV _MAIN_FACTORIAL_I, EBX

; cargar operandos en registros
MOV EAX,_MAIN_FACTORIAL_TOTAL
MOV EBX,1

; asignacion
MOV _MAIN_FACTORIAL_TOTAL, EBX
LABEL0:

; cargar operandos en registros
MOV EAX,_MAIN_FACTORIAL_N
MOV EBX,_MAIN_FACTORIAL_I

CMP EAX, EBX
JB 
; cargar operandos en registros
MOV EAX,1
MOV EBX,_MAIN_FACTORIAL_I

; suma
ADD EAX, EBX
JC ERROR1
MOV @AUX1, EAX

; cargar operandos en registros
MOV EAX,_MAIN_FACTORIAL_I
MOV EBX,@AUX1

; asignacion
MOV _MAIN_FACTORIAL_I, EBX

; cargar operandos en registros
MOV EAX,_MAIN_FACTORIAL_I
MOV EBX,_MAIN_FACTORIAL_TOTAL

; multiplicacion
MUL EBX
JC ERROR2
MOV @AUX2, EAX

; cargar operandos en registros
MOV EAX,_MAIN_FACTORIAL_TOTAL
MOV EBX,@AUX2

; asignacion
MOV _MAIN_FACTORIAL_TOTAL, EBX
RET

; cargar operandos en registros
MOV EAX,_MAIN_FACTORIAL_RESFACT
MOV EBX,3

; asignacion
MOV _MAIN_FACTORIAL_RESFACT, EBX

; cargar operandos en registros
MOV EAX,_MAIN_FACTORIAL_NUM
MOV EBX,5

; asignacion
MOV _MAIN_FACTORIAL_NUM, EBX

; impresion de mensajes
MOV EAX, _MAIN_FACTORIAL_RES
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK

; manejo de errores
ERROR1:
invoke MessageBox, NULL, addr msj1, addr msj1, MB_OK
JMP FIN
ERROR2:
invoke MessageBox, NULL, addr msj2, addr msj2, MB_OK
JMP FIN

FIN:
 invoke ExitProcess, 0
END START

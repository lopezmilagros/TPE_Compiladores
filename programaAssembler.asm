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
_MAIN_FUNC2_Y DD ?
_MAIN_FUNC2_K DD ?
_MAIN_H DD ?
@AUX1 DQ ?@AUX2 DD ?@AUX3 DQ ?@AUX4 DD ?msj1 db "La suma ha exedido el rango del tipo utilizado", 0
@AUX5 DD ?

.CODE
START:

; cargar operandos en registros
FLD @AUX1
FISTP @AUX2
MOV EAX, @AUX2
FLD @AUX3
FISTP @AUX4
MOV EBX, @AUX4

ADD EAX, EBX
JC ERROR1
MOV @AUX5, EAX

; cargar operandos en registros
MOV EAX,_MAIN_H
MOV EBX,@AUX5

MOV _MAIN_H, EBX

; cargar operandos en registros
MOV EAX,_MAIN_FUNC2_K
MOV EBX,_MAIN_H

MOV _MAIN_FUNC2_K, EBX
CALL FUNC2

; cargar operandos en registros
MOV EAX,_MAIN_H
MOV EBX,_MAIN_FUNC2_K

MOV _MAIN_H, EBX

; cargar operandos en registros
MOV EAX,_MAIN_H
MOV EBX, _MAIN_FUNC2_Y

MOV _MAIN_H, EBX

MAIN_FUNC2:

; cargar operandos en registros
MOV EAX,_MAIN_FUNC2_Y
MOV EBX,3

MOV _MAIN_FUNC2_Y, EBX
RET

JMP FIN
; manejo de errores
ERROR1:
invoke MessageBox, NULL, addr msj1, addr msj1, MB_OK
JMP FIN

FIN:
 invoke ExitProcess, 0
END START

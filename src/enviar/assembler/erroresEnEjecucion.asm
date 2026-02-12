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
_MAIN_DIVISION DD ?
_MAIN_DIVISOR DD ?
_MAIN_DIVIDENDO DD ?
@AUX1 DQ 0.0
@AUX2 DD ?
msj1 db "ERROR: No se puede realizar la division por cero", 0
@AUX3 DD ?
msj2 db "La division se ejecuto correctamente", 0

.CODE
START:

; cargar operandos en registros
MOV EAX,_MAIN_DIVIDENDO
MOV EBX,10

; asignacion
MOV _MAIN_DIVIDENDO, EBX

; cargar operandos en registros
MOV EAX,_MAIN_DIVISOR
FLD @AUX1 ; cargo dfloat a la pila
FISTP @AUX2 ; convierto a ulong y lo guardo en aux
MOV EBX, @AUX2

; asignacion
MOV _MAIN_DIVISOR, EBX

; cargar operandos en registros
MOV EAX,_MAIN_DIVIDENDO
MOV EBX,_MAIN_DIVISOR

; division
CMP EBX, 0
JZ ERROR1
MOV EDX, 0
DIV EBX
MOV @AUX3, EAX

; cargar operandos en registros
MOV EAX,_MAIN_DIVISION
MOV EBX,@AUX3

; asignacion
MOV _MAIN_DIVISION, EBX

; impresion de mensajes
invoke MessageBox, NULL, addr msj2, addr msj2, MB_OK
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
JMP FIN

; manejo de errores
ERROR1:
invoke MessageBox, NULL, addr msj1, addr msj1, MB_OK
JMP FIN

FIN:
 invoke ExitProcess, 0
END START

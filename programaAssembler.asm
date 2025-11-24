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
_MAIN_X DD 7
msj0 db "Hola soy un programa muy lindo!!", 0
msj1 db "X es menor", 0
msj2 db "X es mayor", 0

.CODE
START:
invoke MessageBox, NULL, addr msj0, addr msj0, MB_OK
MOV EAX,_MAIN_X
CMP EAX,5
JA LABEL0
invoke MessageBox, NULL, addr msj1, addr msj1, MB_OK
MOV EAX, _MAIN_X
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
JMP LABEL1
LABEL0:
invoke MessageBox, NULL, addr msj2, addr msj2, MB_OK
MOV EAX, _MAIN_X
invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX
invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK
LABEL1:

FIN:
 invoke ExitProcess, 0
END START

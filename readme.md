Integrantes
202410855-Juan Diego Ramirez Garzon
202310826-Julian Roberto Ramirez Alemán

Se tiene un archivo Main, en el cual se lee el archivo .txt (esta en la carpeta de resources) donde se pega la gramatica que se quiere evaluar.

Además de esto tenemos un Lexer que descompone todo en un ARRAY de tokens, se agrega un token al final vacio para simbolizar el fin de la gramatica. En el paser se revisa la gramatica token por token reconociendo los posibles inicios de procedimientos.

Cada token tiene el valor y el tipo, se imprimen al momento de salir del Lexer (simplemente para confirmar lo que se esta leyendo).
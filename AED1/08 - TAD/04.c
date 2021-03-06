#include <stdio.h>
#include <stdlib.h>

typedef struct matriz{
  int lin;
  int col;
  float** v;
} Matriz;

Matriz *mat_cria(int m, int n);
void mat_libera(Matriz *mat);
float mat_acessa(Matriz *mat, int i, int j);
void mat_atribui(Matriz *mat, int i, int j, float v);
int mat_linhas(Matriz *mat);
int mat_colunas(Matriz *mat);
Matriz *transposta(Matriz *mat);

void main()
{
    int l, c;
    Matriz *matrix;
    Matriz *matrixTransp;
    float valor;

    scanf("%d%d", &l, &c);
    
    matrix = mat_cria(l, c);

    for (int i = 0; i < l; i++) {
        for (int j = 0; j < c; j++) {
            scanf("%f", &valor);
            mat_atribui(matrix, i, j, valor);
        }
    }

    matrixTransp = transposta(matrix);

    for (int i = 0; i < matrixTransp->lin; i++) {
        for (int j = 0; j < matrixTransp->col; j++) {
            printf("%.2f ", mat_acessa(matrixTransp, i, j));
        }
        printf("\n");
    }
    

    mat_libera(matrix);
    mat_libera(matrixTransp);
}

Matriz *mat_cria(int m, int n) {
    Matriz *mat = (Matriz *) malloc(sizeof(Matriz));
    if(mat == NULL){
        exit(1);
    }
    mat->lin = m;
    mat->col = n;
    float **v = (float **) malloc(m*sizeof(float*));
    for (int i = 0; i < m; i++) {
        v[i] = (float*) malloc(n*sizeof(float));
    }
    for (int i = 0; i < m; i++){
        if (v[i] == NULL){
            exit(1);
        }
    }
    mat->v = v;
    return mat;
} 

void mat_libera(Matriz *mat) {
    int lin = mat->lin;
    for (int i = 0; i < lin; i++) {
        free(mat->v[i]);
    }
    free(mat);
}

void mat_atribui(Matriz *mat, int i, int j, float v) {
    mat->v[i][j] = v;
}

float mat_acessa(Matriz *mat, int i, int j) {
    return mat->v[i][j];
}

int mat_linhas(Matriz *mat) {
    return mat->lin;
}

int mat_colunas(Matriz *mat) {
    return mat->col;
}

Matriz *transposta(Matriz *mat) {
    Matriz *transp = mat_cria(mat->col, mat->lin);

    for(int i = 0; i < mat->lin; i++) {
        for (int j = 0; j < mat->col; j++) {
            transp->v[j][i] = mat->v[i][j];
        } 
    }
    return transp;
}
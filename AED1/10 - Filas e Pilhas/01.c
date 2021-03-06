#include <stdlib.h>
#include <stdio.h>
// #include "pilha.h"

typedef struct pilha Pilha;
Pilha *pilha_cria();
void pilha_push(Pilha *p, float v);
float pilha_pop(Pilha *p);
int pilha_vazia(Pilha *p);
void pilha_libera(Pilha *p);
float pilha_imprime(Pilha *p);

typedef struct pilha
{
    int n;
    int dim;
    float *vetor;
} Pilha;

Pilha *pilha_cria()
{
    Pilha *novaPilha = (Pilha *)malloc(sizeof(Pilha));
    novaPilha->dim = 1;
    novaPilha->vetor = malloc(novaPilha->dim * sizeof(float));
    novaPilha->n = 0;
    return novaPilha;
}

void pilha_push(Pilha *p, float v)
{
    if (p->n == p->dim)
    {
        p->dim = 2 * p->dim;
        p->vetor = realloc(p->vetor, p->dim * sizeof(float));
    }
    p->vetor[p->n] = v;
    p->n = p->n + 1;
}

int pilha_vazia(Pilha *p)
{
    return (p->n == 0);
}

float pilha_pop(Pilha *p)
{
    if (pilha_vazia(p))
    {
        exit(1);
    }
    p->n = p->n - 1;
    float v = p->vetor[p->n];
    return v;
}

void pilha_libera(Pilha *p)
{
    free(p->vetor);
    free(p);
}

float pilha_imprime(Pilha *p)
{
    if(pilha_vazia(p)){
        printf("*");
        exit(1);
    }
    for (int i = p->n-1; i >= 0; i--)
    {
        printf("%.2f\n", p->vetor[i]);
    }
}

int main()
{
    int N;
    int opcao;
    float v;
    Pilha *pilha = pilha_cria();

    scanf("%d", &N);
    for (int i = 0; i < N; i++)
    {
        scanf("%d", &opcao);
        switch (opcao)
        {
        case 0:
            pilha_pop(pilha);
            break;
        case 1:
            scanf("%f", &v);
            pilha_push(pilha, v);
        default:
            break;
        }
    }
    pilha_imprime(pilha);
    pilha_libera(pilha);
}
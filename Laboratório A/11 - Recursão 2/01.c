#include <stdio.h>
#include <stdlib.h>
#include <math.h>

double sqrtN(int x, double x0, double e);

int main(void)
{
    int N;
    int x;
    double x0, e;

    scanf("%d", &N);
    for (int i = 0; i < N; i++)
    {
        scanf("%d %lf %lf", &x, &x0, &e);
        printf("%.5f\n", sqrtN(x, x0, e));
    }
}
double sqrtN(int x, double x0, double e)
{
    double newX0;
    if (abs(x0 * x0 - x) <= e)
    {
        return x0;
    }
    else
    {
        newX0 = (x0 * x0 + x) / (2.0 * x0);
        return sqrtN(x, newX0, e);
    }
}
#include "rvs.h"
#include <stdio.h>



void
vs_prog_1(char *host)
{
    CLIENT *clnt;
    int  *result_1;
    char *zeroize_1_arg;
    
    
#ifndef	DEBUG
    clnt = clnt_create (host, VS_PROG, VS_VERS, "udp");
//    printf("Connected to server\n");
    
    if (clnt == NULL) {
        clnt_pcreateerror (host);
        exit (1);
    }
#endif	/* DEBUG */
    
    result_1 = zeroize_1((void*)&zeroize_1_arg, clnt);
    if (result_1 == (int *) NULL) {
        clnt_perror (clnt, "call failed");
    }
    
    
    else if(*result_1 == 1){
        printf("The system is reset!\n");
    }
    else if(*result_1==0){
        printf("Reset failed\n");
    }
    else{
        printf("result=%d\n",*result_1);
    }
#ifndef	DEBUG
    clnt_destroy (clnt);
#endif	 /* DEBUG */
}


int
main (int argc, char *argv[])
{
    char *host;
    
    if (argc < 2) {
        printf ("usage: %s server_ip_address\n", argv[0]);
        exit (1);
    }
    host = argv[1];
    vs_prog_1 (host);
    exit (0);
}

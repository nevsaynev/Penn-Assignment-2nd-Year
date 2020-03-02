/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "rvs.h"


void
vs_prog_1(char *host)
{
	CLIENT *clnt;
	
	vote  votefor_1_arg;
	char * *result_4;
	char *listcandidates_1_arg;


#ifndef	DEBUG
	clnt = clnt_create (host, VS_PROG, VS_VERS, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */


	result_4 = listcandidates_1((void*)&listcandidates_1_arg, clnt);
	if (result_4 == (char **) NULL) {
		clnt_perror (clnt, "call failed");
	}

    
    else {
        printf("%s",*result_4);
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

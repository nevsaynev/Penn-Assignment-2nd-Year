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
	int  *result_1;
	char *zeroize_1_arg;
	int  *result_2;
	int  addvoter_1_arg;
	int  *result_3;
	vote  votefor_1_arg;
	char * *result_4;
	char *listcandidates_1_arg;
	int  *result_5;
	char * votecount_1_arg;

#ifndef	DEBUG
	clnt = clnt_create (host, VS_PROG, VS_VERS, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */

	result_1 = zeroize_1((void*)&zeroize_1_arg, clnt);
	if (result_1 == (int *) NULL) {
		clnt_perror (clnt, "call failed");
	}
	result_2 = addvoter_1(&addvoter_1_arg, clnt);
	if (result_2 == (int *) NULL) {
		clnt_perror (clnt, "call failed");
	}
	result_3 = votefor_1(&votefor_1_arg, clnt);
	if (result_3 == (int *) NULL) {
		clnt_perror (clnt, "call failed");
	}
	result_4 = listcandidates_1((void*)&listcandidates_1_arg, clnt);
	if (result_4 == (char **) NULL) {
		clnt_perror (clnt, "call failed");
	}
	result_5 = votecount_1(&votecount_1_arg, clnt);
	if (result_5 == (int *) NULL) {
		clnt_perror (clnt, "call failed");
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
		printf ("usage: %s server_host\n", argv[0]);
		exit (1);
	}
	host = argv[1];
	vs_prog_1 (host);
exit (0);
}
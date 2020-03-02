CC = gcc
CFLAGS = -g -DRPC_SVC_FG
RPCGEN_FLAG = -C

all: vote_server vote-zero-rpc  add-voter-rpc vote-rpc list-candidates-rpc vote-count-rpc

vote-zero-rpc: vote-zero-rpc.o rvs_clnt.o rvs_xdr.o
	$(CC) -o vote-zero-rpc vote-zero-rpc.o rvs_clnt.o rvs_xdr.o -lnsl

add-voter-rpc: add-voter-rpc.o rvs_clnt.o rvs_xdr.o
	$(CC) -o add-voter-rpc add-voter-rpc.o rvs_clnt.o rvs_xdr.o -lnsl

vote-rpc: vote-rpc.o rvs_clnt.o rvs_xdr.o
	$(CC) -o vote-rpc vote-rpc.o rvs_clnt.o rvs_xdr.o -lnsl

vote_server: rvs_server.o rvs_svc.o  rvs_xdr.o
	$(CC) -o rvs_server rvs_server.o rvs_svc.o  rvs_xdr.o

list-candidates-rpc: list-candidates-rpc.o rvs_clnt.o rvs_xdr.o
	$(CC) -o list-candidates-rpc list-candidates-rpc.o rvs_clnt.o rvs_xdr.o -lnsl 

vote-count-rpc: vote-count-rpc.o rvs_clnt.o rvs_xdr.o
	$(CC) -o vote-count-rpc vote-count-rpc.o rvs_clnt.o rvs_xdr.o -lnsl

rvs_server.o: rvs_server.c rvs.h
	$(CC) $(CFLAGS) -c rvs_server.c

vote-zero-rpc.o: vote-zero-rpc.c rvs.h
	$(CC) $(CFLAGS) -c vote-zero-rpc.c

add-voter-rpc.o: add-voter-rpc.c rvs.h
	$(CC) $(CFLAGS) -c add-voter-rpc.c

vote-rpc.o: vote-rpc.c rvs.h
	$(CC) $(CFLAGS) -c vote-rpc.c

list-candidates-rpc.o: list-candidates-rpc.c rvs.h
	$(CC) $(CFLAGS) -c list-candidates-rpc.c

vote-count-rpc.o: vote-count-rpc.c rvs.h
	$(CC) $(CFLAGS) -c vote-count-rpc.c

rvs_svc.o: rvs_svc.c rvs.h
	$(CC) $(CFLAGS) -c rvs_svc.c

rvs_clnt.o: rvs_clnt.c rvs.h
	$(CC) $(CFLAGS) -c rvs_clnt.c

rvs_xdr.o: rvs_xdr.c rvs.h
	$(CC) $(CFLAGS) -c rvs_xdr.c

rvs.h rvs_clnt.c rvs_svc.c rvs_xdr.c:	rvs.x
	rpcgen $(RPCGEN_FLAG) rvs.x

clean:
	rm -f vote-zero-rpc vote-zero-rpc.o add-voter-rpc add-voter-rpc.o vote-rpc vote-rpc.o list-candidates-rpc list-candidates-rpc.o vote-count-rpc vote-count-rpc.o rvs_server rvs_server.o rvs_clnt.* rvs_svc.* rvs.h

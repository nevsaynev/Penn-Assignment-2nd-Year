const TRUE = 1;
const FALSE =0;

const OK = 1;
const EXISTS = 0;
const ERROR = -1;

const NEW = 1;
const NOTAVOTER = -1;
const ALREADYVOTED = -2;

const MAX_C = 5;
const MAX_V = 100;
const MAX_NAME =30;
const MAX_LINE = 50;
const MAX_BUFF = 2048;


struct vote{
char can_name[MAX_NAME];
int voter_id;
};


program VS_PROG
{
version VS_VERS
{
int zeroize(void) = 1;
int addvoter(int) = 2;
int votefor(vote) = 3;
string listcandidates(void) = 4;
int votecount(string) = 5;
} = 1;
} = 0x30090949;











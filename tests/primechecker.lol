HAI 1.2

HOW DUZ I isOver YR test AN YR number
	I HAS A testsq ITZ PRODUKT OF test AN test
	
	FOUND YR DIFFRINT testsq AN SMALLR OF testsq AN number
IF U SAY SO

HOW DUZ I isPrime YR number
	BOTH SAEM number AN 1, O RLY?
	YA RLY
		FOUND YR FAIL
	OIC

	BOTH SAEM number AN 2, O RLY?
	YA RLY
		FOUND YR WIN
	OIC
	
	I HAS A test ITZ 0
	
	IM IN YR primeTestLoop UPPIN YR test TIL isOver SUM OF test AN 2 number
		
		I HAS A reste ITZ MOD OF number AN SUM OF test AN 2
		
		BOTH SAEM reste AN 0, O RLY?
		YA RLY, FOUND YR FAIL
		OIC
	IM OUTTA YR primeTestLoop
	
	FOUND YR WIN
IF U SAY SO

I HAS A number
VISIBLE "Enter a number: "
GIMMEH number

isPrime number
O RLY?
YA RLY, VISIBLE "Yep, it's prime\n"
NO WAI, VISIBLE "Nope, it's not prime\n"
OIC

KTHXBYE

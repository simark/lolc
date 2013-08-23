HAI 1.2

HOW DUZ I selectConversion
	I HAS A choice
	I HAS A whatever ITZ 0
	
	VISIBLE "Choose an option: \n"
	VISIBLE "  (1) C to F\n"
	VISIBLE "  (2) F to C\n"
	VISIBLE "  (3) C to K\n"
	VISIBLE "  (4) K to C\n"
	VISIBLE "  (5) K to F\n"
	VISIBLE "  (6) F to K\n"
	VISIBLE "  (7) quit\n"	
	
	IM IN YR selectloop UPPIN YR whatever
		GIMMEH choice
		
		I HAS A biggrThanMin  ITZ BOTH SAEM choice AN BIGGR OF choice AN 1
		I HAS A smallrThanMax ITZ BOTH SAEM choice AN SMALLR OF choice AN 7
		
		BOTH OF biggrThanMin AN smallrThanMax, O RLY?
		YA RLY
			FOUND YR choice
		OIC
		
		VISIBLE "Invalid choice\n"
		
	IM OUTTA YR selectloop
	
IF U SAY SO

I HAS A quit ITZ FAIL
I HAS A whatever ITZ 0

BTW On pourrait mettre TIL quit, mais je veux tester WILE et NOT :)
IM IN YR mainloop UPPIN YR whatever WILE NOT quit
	
	I HAS A unitfrom
	I HAS A unitto
	I HAS A from
	I HAS A to
	
	selectConversion, WTF?
		OMG 1
			BTW C -> F

			VISIBLE "Enter a temperature in celsius: "
			GIMMEH from
			
			unitfrom R "celsius"
			unitto R "fahrenheit"
			
			to R from
			to R PRODUKT OF to AN 9
			to R QUOSHUNT OF to AN 5
			to R SUM OF to AN 32
			
			GTFO
		OMG 2
			BTW F -> C

			VISIBLE "Enter a temperature in fahrenheit: "
			GIMMEH from
			
			unitfrom R "fahrenheit"
			unitto R "celsius"
			
			to R from
			to R DIFF OF to AN 32
			to R PRODUKT OF to AN 5
			to R QUOSHUNT OF to AN 9
			
			GTFO
		OMG 3
			BTW C -> K

			VISIBLE "Enter a temperature in celsius: "
			GIMMEH from
			
			unitfrom R "celsius"
			unitto R "kelvin"
			
			to R from
			to R SUM OF to AN 273
			
			GTFO
		OMG 4
			BTW K -> C

			VISIBLE "Enter a temperature in kelvin: "
			GIMMEH from
			
			unitfrom R "kelvin"
			unitto R "celsius"
			
			to R from
			to R DIFF OF to AN 273
			
			GTFO
		OMG 5
			BTW K -> F

			VISIBLE "Enter a temperature in kelvin: "
			GIMMEH from
			
			unitfrom R "kelvin"
			unitto R "fahrenheit"
			
			to R from
			to R DIFF OF to AN 273
			to R PRODUKT OF to AN 9
			to R QUOSHUNT OF to AN 5
			to R SUM OF to AN 32
			
			GTFO
		OMG 6
			BTW F -> K

			VISIBLE "Enter a temperature in fahrenheit: "
			GIMMEH from
			
			unitfrom R "fahrenheit"
			unitto R "kelvin"
			
			to R from
			to R DIFF OF to AN 32
			to R PRODUKT OF to AN 5
			to R QUOSHUNT OF to AN 9
			to R SUM OF to AN 273
			
			GTFO
		OMGWTF
			quit R WIN
			GTFO
	OIC
	
	NOT BOTH SAEM quit AN WIN, O RLY?
	YA RLY
		VISIBLE SMOOSH from AN " " AN unitfrom AN " is " AN to AN " " AN unitto AN "\n" MKAY
	OIC

IM OUTTA YR mainloop

VISIBLE "Thanks for using LOLconverter 2.3 RC2\n"

KTHXBYE

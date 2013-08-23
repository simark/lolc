HAI 1.2

BTW prompts the user to enter a number between min and max (inclusive)
BTW
BTW @param tries The number of the try
BTW @param min Minimum allowed value
BTW @param max Maximum allowed value
BTW
BTW @return The number entered by the user, which is >= min and <= max

HOW DUZ I prompt YR tries AN YR min AN YR max
	BTW fill guess with an invalid value
	I HAS A guess ITZ DIFF OF min AN 1
	I HAS A whatever

	BTW ask until we have a value in the [min,max] range
	IM IN YR promptloop UPPIN YR whatever
		VISIBLE SMOOSH "Try " AN tries AN " Choose a number between " AN min AN " and " AN max AN "\n" MKAY
		GIMMEH guess
		
		I HAS A biggrThanMin  ITZ BOTH SAEM guess AN BIGGR OF guess AN min
		I HAS A smallrThanMax ITZ BOTH SAEM guess AN SMALLR OF guess AN max
		
		BOTH OF biggrThanMin AN smallrThanMax, O RLY?
		YA RLY
			FOUND YR guess
		OIC
		
		VISIBLE SMOOSH "Your choice " AN guess AN " is not valid.\n" MKAY
	IM OUTTA YR promptloop
IF U SAY SO



I HAS A target ITZ 5
I HAS A guess
I HAS A tries ITZ 1

IM IN YR guessloop UPPIN YR tries

	BTW prompt for a 
	guess R prompt tries 1 20
	
	DIFFRINT guess AN BIGGR OF guess AN target
	O RLY?
	YA RLY, VISIBLE SMOOSH guess AN " is too low.\n" MKAY
	MEBBE DIFFRINT guess AN SMALLR OF guess AN target
		VISIBLE SMOOSH guess AN " is too high.\n" MKAY
	NO WAI
		GTFO
	OIC
IM OUTTA YR guessloop

VISIBLE "Hooray !\n"
VISIBLE SMOOSH "It took " AN tries AN " tries\n" MKAY

KTHXBYE

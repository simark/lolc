testdir=tests

tests=$testdir/*.lol

green="\033[01;32m"
red="\033[01;31m"
colorstop="\033[0m"
mkdir -p png

grammardir=ca/polymtl/lol/grammar

dotOutputClass="$grammardir/VisiteurDOTOutput"
prettyPrintClass="$grammardir/VisiteurAffichageCoquet"

for t in $tests; do

	dotout=$(java ${dotOutputClass} < $t 2>/dev/null)
	ret=$?
	
	filename=$(basename $t)
	testname=${filename%.lol}
	
	if [ $ret -eq 0 ]; then
		# Générer le png
		dot -Tpng -o png/$testname.png <<< $dotout
		echo -e "Test de VisiteurDOTOutput sur $t: ${green}GOOD${colorstop} (java ${dotOutputClass} < $t)"
		
	else
		echo -e "Test de VisiteurDOTOutput sur $t: ${red}NOPE${colorstop} (java ${dotOutputClass} < $t)"
	fi

done

mkdir -p coquetpng
mkdir -p coquet

for t in $tests; do
	filename=$(basename $t)
	testname=${filename%.lol}
	
	java ${prettyPrintClass} < $t 2>/dev/null > coquet/$testname.lol
	ret=$?
	
	if [ $ret -ne 0 ]; then
		echo -e "$t: VisiteurAffichageCoquet ${red}failed${colorstop}"
		continue
	fi
	
	coquetdot=$(java ${dotOutputClass}  <coquet/$testname.lol 2>/dev/null)
	ret=$?
	
	if [ $ret -ne 0 ]; then
		echo -e "$t: VisiteurDOTOutput after VisiteurAffichageCoquet ${red}failed${colorstop}"
		continue
	fi
	
	# Générer le png
	dot -Tpng -o coquetpng/$testname.png <<< $coquetdot
	
	# Comparer les deux png
	cmp -s coquetpng/$testname.png png/$testname.png
	ret=$?
	
	if [ $ret -eq 0 ]; then
		echo -e "Test de VisiteurAffichageCoquet sur $t: ${green}GOOD${colorstop} (java ${prettyPrintClass} < $t)"
		
	else
		echo -e "Test de VisiteurAffichageCoquet sur $t: ${red}NOPE${colorstop} (java ${prettyPrintClass} < $t)"
	fi

done

package ca.polymtl.lol;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.grammar.LOLPublic;
import ca.polymtl.lol.grammar.LOLVisitException;
import ca.polymtl.lol.grammar.ParseException;
import ca.polymtl.lol.grammar.SimpleNode;
import ca.polymtl.lol.grammar.VisiteurGenExecStruct;

public class LOLexec {

	/**
	 * @param args
	 * @throws ParseException
	 * @throws LOLVisitException
	 * @throws LOLRuntimeEx
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws ParseException, LOLVisitException, LOLRuntimeEx,
			FileNotFoundException {
		
		String filename = null;
		boolean debug = false;
		
		for (String arg : args) {
			if (arg.startsWith("-")) {
				if (arg.equals("-d")) {
					debug = true;
				} else {
					System.err.println("Unknown option " + arg);
					usage();
				}
			} else {
				if (filename == null) {
					filename = arg;
				} else {
					System.err.println("Name already specified as " + filename);
					usage();
				}
			}
			
		}

		if (filename == null) {
			usage();
		}

		if (!debug) {
			System.err.close();
		}

		FileInputStream fis = new FileInputStream(filename);

		LOLPublic lol = new LOLPublic(fis);

		SimpleNode start = lol.Start();

		VisiteurGenExecStruct vis = new VisiteurGenExecStruct();
		Program prg = (Program) start.jjtAccept(vis, null);

		prg.debugPrint();

		prg.run();
	}

	public static void usage() {
		System.err.println("Usage: LOLexec program.lol");
		System.exit(1);
	}
}

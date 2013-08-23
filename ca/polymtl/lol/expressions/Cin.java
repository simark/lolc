package ca.polymtl.lol.expressions;

import static ca.polymtl.lol.Utils.tabs;

import java.io.BufferedReader;
import java.io.IOException;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.StringValue;
import ca.polymtl.lol.types.Value;

public class Cin implements Expression {

	String name;

	public Cin(String name) {
		super();
		this.name = name;
	}

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		BufferedReader inputReader = context.getInputReader();

		String readLine;

		try {
			readLine = inputReader.readLine();
		} catch (IOException e) {
			readLine = "";
		}

		context.getCurrentScope().assignExistingVariable(name, new StringValue(readLine));

		return null;
	}

	@Override
	public void debugPrint(int indent) {
		tabs(indent);
		System.err.println("Console input to var " + name);
	}

}

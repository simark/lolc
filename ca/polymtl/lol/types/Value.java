package ca.polymtl.lol.types;

public abstract class Value {
	
	public abstract StringValue toStringValue();
	public abstract IntegerValue toIntegerValue();
	public abstract NullValue toNullValue();
	public abstract BoolValue toBoolValue();
	
	public abstract boolean eq(StringValue sv);
	public abstract boolean eq(IntegerValue iv);
	public abstract boolean eq(NullValue nv);
	public abstract boolean eq(BoolValue bv);
	public abstract boolean eq(Value v);
}

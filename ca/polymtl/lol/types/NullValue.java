package ca.polymtl.lol.types;

public class NullValue extends Value {

	@Override
	public StringValue toStringValue() {
		return new StringValue();
	}

	@Override
	public IntegerValue toIntegerValue() {
		return new IntegerValue();
	}

	@Override
	public NullValue toNullValue() {
		return this;
	}

	@Override
	public BoolValue toBoolValue() {
		return new BoolValue();
	}

	@Override
	public boolean eq(StringValue sv) {
		return false;
	}

	@Override
	public boolean eq(IntegerValue iv) {
		return false;
	}

	@Override
	public boolean eq(NullValue nv) {
		return true;
	}

	@Override
	public boolean eq(BoolValue bv) {
		return false;
	}

	@Override
	public boolean eq(Value v) {
		return v.eq(this);
	}
}

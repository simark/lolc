package ca.polymtl.lol.types;

public class IntegerValue extends Value {

	final int value;

	public IntegerValue() {
		value = 0;
	}

	public IntegerValue(int value) {
		this.value = value;
	}

	@Override
	public StringValue toStringValue() {
		return new StringValue(Integer.toString(value));
	}

	@Override
	public IntegerValue toIntegerValue() {
		return this;
	}

	@Override
	public NullValue toNullValue() {
		return new NullValue();
	}

	@Override
	public BoolValue toBoolValue() {
		return new BoolValue(value != 0);
	}

	@Override
	public String toString() {
		return "IntegerValue = " + value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public boolean eq(StringValue sv) {
		return sv.eq(this);
	}

	@Override
	public boolean eq(IntegerValue iv) {
		return iv.value == this.value;
	}

	@Override
	public boolean eq(NullValue nv) {
		return nv.eq(this);
	}

	@Override
	public boolean eq(BoolValue bv) {
		return bv.eq(this);
	}

	@Override
	public boolean eq(Value v) {
		return v.eq(this);
	}
}

package ca.polymtl.lol.types;

public class BoolValue extends Value {
	final boolean value;
	
	public BoolValue() {
		value = false;
	}
	
	public BoolValue(boolean value) {
		this.value = value;
	}
	
	public boolean getValue() {
		return value;
	}

	@Override
	public StringValue toStringValue() {
		if (value) {
			return new StringValue("WIN");
		} else {
			return new StringValue("FAIL");
		}
	}

	@Override
	public IntegerValue toIntegerValue() {
		if (value) {
			return new IntegerValue(1);
		} else {
			return new IntegerValue(0);
		}
	}

	@Override
	public NullValue toNullValue() {
		return new NullValue();
	}

	@Override
	public BoolValue toBoolValue() {
		return this;
	}

	@Override
	public boolean eq(StringValue sv) {
		return this.value == sv.toBoolValue().getValue();
	}

	@Override
	public boolean eq(IntegerValue iv) {
		return this.value == iv.toBoolValue().getValue();
	}

	@Override
	public boolean eq(NullValue nv) {
		return nv.eq(this);
	}

	@Override
	public boolean eq(BoolValue bv) {
		return this.value == bv.value;
	}

	@Override
	public boolean eq(Value v) {
		return v.eq(this);
	}

	@Override
	public String toString() {
		return "BoolValue = " + Boolean.toString(value);
	}
}

package ca.polymtl.lol.types;

public class StringValue extends Value {
	final String value;

	public StringValue() {
		value = "";
	}
	
	public StringValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public StringValue toStringValue() {
		return this;
	}

	@Override
	public IntegerValue toIntegerValue() {
		int ret;
		
		try {
			ret = Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			ret = 0;
		}
		
		return new IntegerValue(ret); 
	}

	@Override
	public NullValue toNullValue() {
		return new NullValue();
	}

	@Override
	public BoolValue toBoolValue() {
		return new BoolValue(value.length() > 0);
	}
	
	@Override
	public String toString() {
		return "StringValue = " + value;
	}

	@Override
	public boolean eq(StringValue sv) {
		return this.value.equals(sv.value);
	}

	@Override
	public boolean eq(IntegerValue iv) {
		return this.value.equals(iv.toStringValue().value);
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

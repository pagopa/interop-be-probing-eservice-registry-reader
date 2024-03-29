package it.pagopa.interop.probing.eservice.registry.reader.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EserviceState {

	ACTIVE("ACTIVE"),

	INACTIVE("INACTIVE");

	private String value;

	EserviceState(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static EserviceState fromValue(String value) {
		for (EserviceState b : EserviceState.values()) {
			if (b.value.equals(value)) {
				return b;
			}
		}
		throw new IllegalArgumentException("Unexpected value '" + value + "'");
	}
}

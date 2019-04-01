package red.semipro.domain.enums;

public enum VenueArrangementStatus {

	APPLY(1),
	NOT_APPLY(2);

	private int value;

	VenueArrangementStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static VenueArrangementStatus valueOf(final int value) {
		VenueArrangementStatus[] statuses = VenueArrangementStatus.values();
		for (VenueArrangementStatus status : statuses) {
			if (status.getValue() == value) {
				return status;
			}
		}
		return null;
	}
}

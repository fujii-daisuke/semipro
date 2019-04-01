package red.semipro.domain.enums;

public enum ShootingEditStatus {

	APPLY(1),
	NOT_APPLY(2);

	private int value;

	ShootingEditStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static ShootingEditStatus valueOf(final int value) {
		ShootingEditStatus[] statuses = ShootingEditStatus.values();
		for (ShootingEditStatus status : statuses) {
			if (status.getValue() == value) {
				return status;
			}
		}
		return null;
	}
}

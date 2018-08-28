package de.hardcorepvp.manager;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class PunishmentManager {

	//Tabelle

	public void getPunishmentData(UUID uniqueId, Consumer<PunishmentData> consumer) {

	}

	public class PunishmentData {

		//Aus Tabelle löschen, wenn nicht mehr gepunished!
		//UUID cache
		//Archiv anlegen!
		private UUID uniqueId;
		private String banReason;
		private String muteReason;
		private long banTime;
		private long banTimestamp;
		private long muteTime;
		private long muteTimestamp;

		public PunishmentData(UUID uniqueId, String banReason, String muteReason, long banTime, long banTimestamp, long muteTime, long muteTimestamp) {
			this.uniqueId = uniqueId;
			this.banReason = banReason;
			this.muteReason = muteReason;
			this.banTime = banTime;
			this.banTimestamp = banTimestamp;
			this.muteTime = muteTime;
			this.muteTimestamp = muteTimestamp;
		}

		public UUID getUniqueId() {
			return uniqueId;
		}

		public String getBanReason() {
			return banReason;
		}

		public String getMuteReason() {
			return muteReason;
		}

		public long getBanTime() {
			return banTime;
		}

		public long getBanTimestamp() {
			return banTimestamp;
		}

		public long getMuteTime() {
			return muteTime;
		}

		public long getMuteTimestamp() {
			return muteTimestamp;
		}

		public boolean isTemporarilyBanned() {
			return this.banTime > 0L;
		}

		public boolean isTemporarilyMuted() {
			return this.muteTime > 0L;
		}
	}

	public class PunishmentDataArchive {

		private UUID uniqueId;
		private List<PunishmentData> punishmentArchive;

		public PunishmentDataArchive(UUID uniqueId, List<PunishmentData> punishmentArchive) {
			this.uniqueId = uniqueId;
			this.punishmentArchive = punishmentArchive;
		}

		public UUID getUniqueId() {
			return uniqueId;
		}

		public List<PunishmentData> getPunishmentArchive() {
			return punishmentArchive;
		}
	}
}

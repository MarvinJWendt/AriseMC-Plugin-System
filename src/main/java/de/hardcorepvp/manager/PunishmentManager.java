package de.hardcorepvp.manager;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class PunishmentManager {

	//Tabelle

	public void setBanned(UUID uniqueId, BanData banData, Consumer<Boolean> consumer) {

	}

	public void getBanData(UUID uniqueId, Consumer<BanData> consumer) {

	}

	public void deleteBanData(UUID uniqueId, Consumer<Boolean> consumer) {

	}

	public boolean setBannedSync(UUID uniqueId, BanData banData) {

	}

	public BanData getBanDataSync(UUID uniqueId) {

	}

	public boolean deleteBanDataSync(UUID uniqueId) {

	}

	public void setMuted(UUID uniqueId, MuteData muteData, Consumer<Boolean> consumer) {

	}

	public void getMuteData(UUID uniqueId, Consumer<MuteData> consumer) {

	}

	public void deleteMuteData(UUID uniqueId, Consumer<Boolean> consumer) {

	}

	public boolean setMutedSync(UUID uniqueId, MuteData muteData) {

	}

	public MuteData getMuteDataSync(UUID uniqueId) {

	}

	public boolean deleteMuteDataSync(UUID uniqueId) {

	}

	public static class MuteData {

		private UUID uniqueId;
		private String muteReason;
		private String mutedBy;
		private long muteTime;
		private long muteTimestamp;

		public MuteData(UUID uniqueId, String muteReason, String mutedBy, long muteTime, long muteTimestamp) {
			this.uniqueId = uniqueId;
			this.muteReason = muteReason;
			this.mutedBy = mutedBy;
			this.muteTime = muteTime;
			this.muteTimestamp = muteTimestamp;
		}

		public UUID getUniqueId() {
			return uniqueId;
		}

		public String getMuteReason() {
			return muteReason;
		}

		public String getMutedBy() {
			return mutedBy;
		}

		public long getMuteTime() {
			return muteTime;
		}

		public long getMuteTimestamp() {
			return muteTimestamp;
		}

		public boolean isTemporarilyMuted() {
			return this.muteTime > 0L;
		}
	}

	public static class BanData {

		private UUID uniqueId;
		private String banReason;
		private String bannedBy;
		private long banTime;
		private long banTimestamp;

		public BanData(UUID uniqueId, String banReason, String bannedBy, long banTime, long banTimestamp) {
			this.uniqueId = uniqueId;
			this.banReason = banReason;
			this.bannedBy = bannedBy;
			this.banTime = banTime;
			this.banTimestamp = banTimestamp;
		}

		public UUID getUniqueId() {
			return uniqueId;
		}

		public String getBanReason() {
			return banReason;
		}

		public String getBannedBy() {
			return bannedBy;
		}

		public long getBanTime() {
			return banTime;
		}

		public long getBanTimestamp() {
			return banTimestamp;
		}

		public boolean isTemporarilyBanned() {
			return this.banTime > 0L;
		}
	}

	public static class MuteHistory {

		private UUID uniqueId;
		private List<MuteData> muteArchive;

		public MuteHistory(UUID uniqueId, List<MuteData> muteArchive) {
			this.uniqueId = uniqueId;
			this.muteArchive = muteArchive;
		}

		public UUID getUniqueId() {
			return uniqueId;
		}

		public List<MuteData> getMuteArchive() {
			return muteArchive;
		}
	}

	public static class BanHistory {

		private UUID uniqueId;
		private List<BanData> banArchive;

		public BanHistory(UUID uniqueId, List<BanData> banArchive) {
			this.uniqueId = uniqueId;
			this.banArchive = banArchive;
		}

		public UUID getUniqueId() {
			return uniqueId;
		}

		public List<BanData> getBanArchive() {
			return banArchive;
		}
	}
}

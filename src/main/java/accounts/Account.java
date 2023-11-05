package accounts;

import org.json.JSONObject;

import net.dv8tion.jda.api.entities.Member;

public class Account {
	public String id;
	public Member member;

	public int level;
	public long experience;
	public long totalExperience;

	public TrustFactor trustLevel;
	public Permissions permission;

	public Account(String id, int level, long experience, long totalExperience,
			TrustFactor trustLevel, Permissions permission) {
		this.id = id;
		this.level = level;
		this.experience = experience;
		this.totalExperience = totalExperience;
		this.trustLevel = trustLevel;
		this.permission = permission;
	}

	// Method to generate every account
	public JSONObject toJson() {
		// Generate a JSONObject with all the properties
		JSONObject result = new JSONObject();
		result.put("id", this.id);
		result.put("level", this.level);
		result.put("experience", this.experience);
		result.put("totalExperience", this.totalExperience);
		result.put("permission", this.permission);
		result.put("version", "1");

		return result;
	}
}

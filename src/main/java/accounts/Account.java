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

	public boolean canDrop;

	public Account(String id, Member member, int level, long experience, long totalExperience,
			TrustFactor trustLevel, Permissions permission, boolean canDrop) {
		this.id = id;
		this.member = member;
		this.level = level;
		this.experience = experience;
		this.totalExperience = totalExperience;
		this.trustLevel = trustLevel;
		this.permission = permission;
		this.canDrop = canDrop;
	}

	public JSONObject toJson() {
		// Generate a JSONObject with all the properties
		JSONObject result = new JSONObject();
		result.put("id", this.id);
		result.put("level", this.level);
		result.put("experience", this.experience);
		result.put("totalExperience", this.totalExperience);
		result.put("trustFactor", this.trustLevel);
		result.put("permission", this.permission);
		result.put("canDrop", this.canDrop);
		return result;
	}
}

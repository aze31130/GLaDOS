package utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import java.util.List;
import accounts.Constants;
import accounts.Permissions;
import glados.GLaDOS;

public class PermissionsUtils {
	/*
	 * Returns true if the given member has enough privileges
	 */
	public static Boolean canExecute(Member member, Permissions requiredPermission) {
		if (requiredPermission == Permissions.NONE)
			return true;

		if (member.getId().equals(Constants.OwnerId))
			return true;

		GLaDOS g = GLaDOS.getInstance();
		List<Role> roles = member.getRoles();

		Permissions actualPermission = Permissions.NONE;
		for (Role role : roles)
			if (role.getId().equals(g.roleModerator))
				actualPermission = Permissions.MODERATOR;

		for (Role role : roles)
			if (role.getId().equals(g.roleAdministrator))
				actualPermission = Permissions.ADMINISTRATOR;

		return actualPermission.level >= requiredPermission.level;
	}
}

package utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import java.util.List;
import accounts.Permission;
import glados.GLaDOS;

public class PermissionsUtils {
	/*
	 * Returns true if the given member has enough privileges
	 */
	public static Boolean canExecute(Member member, Permission requiredPermission) {
		if (requiredPermission == Permission.NONE)
			return true;

		if (member.getId().equals(GLaDOS.getInstance().ownerId))
			return true;

		GLaDOS g = GLaDOS.getInstance();
		List<Role> roles = member.getRoles();

		Permission actualPermission = Permission.NONE;
		for (Role role : roles)
			if (role.getId().equals(g.roleModerator))
				actualPermission = Permission.MODERATOR;

		for (Role role : roles)
			if (role.getId().equals(g.roleAdministrator))
				actualPermission = Permission.ADMINISTRATOR;

		return actualPermission.level >= requiredPermission.level;
	}
}

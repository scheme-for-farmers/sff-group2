package com.lti.repository;

import com.lti.entity.Admin;

public interface AdminRepository {
	public long addOrUpdateAdmin(Admin admin);
	public Admin fetchAdminByEmailAndPassword(String adminEmail,String adminPassword);
	public Admin fetchAdminByEmail(String adminEmail);
}

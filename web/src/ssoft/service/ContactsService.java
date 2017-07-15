package ssoft.service;

public interface ContactsService extends Service {

	
	/**
	 * 获取通信录
	 * @param id
	 * @param password
	 * @return
	 */
	String getContacts(String id, String password);

}

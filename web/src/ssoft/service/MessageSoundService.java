package ssoft.service;

public interface MessageSoundService extends Service {

	/**
	 * 保存群聊或者公众号消息提醒的设置
	 * @param id
	 * @param password
	 * @param type
	 * @param targetId
	 * @param messagesound
	 * @return
	 */
	String saveSet(String id, String password, String type, String targetId,
			String messagesound);

	/**
	 * 获取公众号或者群聊的声音设置
	 * @param id
	 * @param password
	 * @param type
	 * @param targetId
	 * @return
	 */
	String getSet(String id, String password, String type, String targetId);

}

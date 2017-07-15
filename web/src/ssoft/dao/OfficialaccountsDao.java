package ssoft.dao;

import java.util.List;

import ssoft.domain.Officialaccounts;

public interface OfficialaccountsDao extends Dao {

	

	/**
	 * 获取公众号
	 * @param officialaccount_id
	 * @return
	 */
	Officialaccounts getOfficialaccountById(int officialaccount_id);

	/**
	 * 添加一条公众号
	 *
	 * @param name
	 * @param officialaccountName
	 * @param coordinate
	 * @param userid 创建者id
	 * @return
	 */
	Officialaccounts createOfficialaccount(String officialaccountName,
										   String coordinate,String type, String userid);

	/**
	 * 获取所有的公众号
	 * @return
	 */
	List<Officialaccounts> getAllOfficial();

}

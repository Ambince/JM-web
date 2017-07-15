package ssoft.utils;

public class Page {
	//获取总页数
	public static int[] getPageInfo(int page, int totalRows, int perPage){
		perPage = 60;
		int pageInfo[] = new int[4];
		//start
		pageInfo[0] = (page-1)*perPage;
		//perPage
		pageInfo[1] = perPage;
		//pageNum
		if(totalRows%perPage == 0){
			pageInfo[2] = totalRows/perPage;
		}else{
			pageInfo[2] = totalRows/perPage + 1;
		}
		return pageInfo;
	}
}
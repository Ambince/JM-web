package ssoft.domain;

import java.io.Serializable;

public class NativeInfo implements Serializable {
	private String zuXun;
	private String secretIntro;
	private String publicIntro;
	private String zhuanJi;

	public String getZhuanJi() {
		return zhuanJi;
	}
	public void setZhuanJi(String zhuanJi) {
		this.zhuanJi = zhuanJi;
	}
	public String getZuXun() {
		return zuXun;
	}
	public void setZuXun(String zuXun) {
		this.zuXun = zuXun;
	}
	public String getSecretIntro() {
		return secretIntro;
	}

	public void setSecretIntro(String secretIntro) {
		this.secretIntro = secretIntro;
	}
	public String getPublicIntro() {
		return publicIntro;
	}
	public void setPublicIntro(String publicIntro) {
		this.publicIntro = publicIntro;
	}
	@Override
	public String toString() {
		return "NativeInfo [zuXun=" + zuXun + ", secretIntro=" + secretIntro
				+ ", publicIntro=" + publicIntro + ", zhuanJi=" + zhuanJi + "]";
	}

}

package io.rong.models;

public class BroadcastMessage extends Message {
	
	private String context;
	
	public BroadcastMessage(String type, String content){
		this.type = type;
		this.context = content;
	}

	@Override
	public String toString() {
		return context;
	}

}

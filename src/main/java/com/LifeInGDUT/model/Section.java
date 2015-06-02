package com.LifeInGDUT.model;

public enum Section {
	USER(1, "其他人动态"), TEAM(2, "社团"), NEWS(3, "校内新闻");
	private int id;
	private String name;

	private Section(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public static String getName(int id) {
	    for (Section s : Section.values()) {
	    	if (s.id == id) {
	    		return s.name;
	    	}
	    }
	    return null;
	 }
	
	public int getId(){
		return this.id;
	}

}

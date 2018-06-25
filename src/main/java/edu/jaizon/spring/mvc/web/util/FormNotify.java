package edu.jaizon.spring.mvc.web.util;

public class FormNotify {
	
	final static public String ADDBOOK="1";
	final static public String UPDATEBOOK="0";
	
	public FormNotify(String isAddBook){
		if (isAddBook.equalsIgnoreCase(FormNotify.ADDBOOK)) {
			this.setFormAdd(FormNotify.ADDBOOK);
		}else {
			this.setFormUpdate(FormNotify.UPDATEBOOK);
		}
	}
		
	private String formAdd;
	private String formUpdate;

	public String getFormAdd() {
		return formAdd;
	}
	public void setFormAdd(String formAdd) {
		this.formAdd = formAdd;
	}
	public String getFormUpdate() {
		return formUpdate;
	}
	public void setFormUpdate(String formUpdate) {
		this.formUpdate = formUpdate;
	}
	
}

package edu.jaizon.spring.mvc.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "book")
public class Book implements Serializable {	
	
	private	Long id;
	private String categoryName;
	private String isbn;
	private String title;
	private String publisher;
	private float price;		
		
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}	

	@NotEmpty(message="Category cannot be empty.")
	@Size(min=1, max=100, message="Category needs minimum of 1 character.")
	@Column(name = "CATEGORY_NAME")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@NotEmpty(message="ISBN cannot be empty.")
	@Column(name = "ISBN")
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	@NotEmpty(message="Title cannot be empty.")
	@Size(min=1, max=100, message="Title needs minimum of 1 character.")
	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotEmpty(message="Publisher cannot be empty.")
	@Size(min=1, max=100, message="Publisher needs minimum of 1 character.")
	@Column(name = "PUBLISHER")
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@NotNull(message="Price cannot be null.")
	@Column(name = "PRICE_DECIMAL")
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}	
	

	@Override
	public String toString() {
		return "Book [id=" + id + ", categoryName=" + categoryName + ", isbn=" + isbn + ", title=" + title
				+ ", publisher=" + publisher + ", price=" + price + "]";
	}


	private static final long serialVersionUID = 1L;
	
}

package com.revature.model;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.revature.dto.ReimbursementDTO;

@Entity
@Table(name = "ers_reimbursement")
public class Reimbursement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "reimb_amount")
	private int amount;
	
	@Column(name = "reimb_submitted")
	private Date submitted;
	
	@Column(name = "reimb_resolved")
	private Date resolved;
	
	@Column(name = "reimb_description")
	private String description;
	
	@Column(name = "reimb_receipt")
	private Byte[] receipt;

	@ManyToOne
	@JoinColumn(name = "reimb_author")
	private User author;
	
	@ManyToOne
	@JoinColumn(name = "reimb_resolver")
	private User resolver;
	
	@ManyToOne
	@JoinColumn(name = "reimb_status_id")
	private ReimbStatus status;
	
	@ManyToOne
	@JoinColumn(name = "reimb_type_id")
	private ReimbType type;

	public Reimbursement() {
		super();
	}

	public Reimbursement(int amount, Date submitted, Date resolved, String description, Byte[] receipt, User author,
			User resolver, ReimbStatus status, ReimbType type) {
		super();
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}

	public ReimbursementDTO convertToDTO() {
		ReimbursementDTO r = new ReimbursementDTO();
		
		r.setId(id);
		r.setAmount(amount);
		r.setSubmitted(submitted);
		r.setResolved(resolved);
		r.setDescription(description);
		r.setReceipt(receipt);
		r.setAuthor(author == null ? null : author.getUsername());
		r.setResolver(resolver == null ? null : resolver.getUsername());
		r.setStatus(status == null ? null : status.getStatus());
		r.setType(type == null ? null : type.getType());
		
		return r;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}

	public Date getResolved() {
		return resolved;
	}

	public void setResolved(Date resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Byte[] getReceipt() {
		return receipt;
	}

	public void setReceipt(Byte[] receipt) {
		this.receipt = receipt;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public User getResolver() {
		return resolver;
	}

	public void setResolver(User resolver) {
		this.resolver = resolver;
	}

	public ReimbStatus getStatus() {
		return status;
	}

	public void setStatus(ReimbStatus status) {
		this.status = status;
	}

	public ReimbType getType() {
		return type;
	}

	public void setType(ReimbType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + Arrays.hashCode(receipt);
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + ((resolver == null) ? 0 : resolver.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (amount != other.amount)
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (!Arrays.equals(receipt, other.receipt))
			return false;
		if (resolved == null) {
			if (other.resolved != null)
				return false;
		} else if (!resolved.equals(other.resolved))
			return false;
		if (resolver == null) {
			if (other.resolver != null)
				return false;
		} else if (!resolver.equals(other.resolver))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", submitted=" + submitted + ", resolved=" + resolved
				+ ", description=" + description + ", receipt=" + Arrays.toString(receipt) + ", author=" + author
				+ ", resolver=" + resolver + ", status=" + status + ", type=" + type + "]";
	}
}

package victor.bonneau.kata.bankAccount.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import victor.bonneau.kata.bankAccount.model.enums.TransactionType;

public class TransactionDto {

	int id;
	@NotNull
	TransactionType type;
	@NotBlank
	double amount;
	LocalDateTime date;
	@NotBlank
	int accountId;
	double balenceBefor;
	double balenceAfter;
	
	public TransactionDto() {
		
	}
	
	public TransactionDto(int id, TransactionType type, double amount, LocalDateTime date, int accountId, int userId,
			double balenceBefor, double balenceAfter) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.date = date;
		this.accountId = accountId;
		this.balenceBefor = balenceBefor;
		this.balenceAfter = balenceAfter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public double getBalenceBefor() {
		return balenceBefor;
	}

	public void setBalenceBefor(double balenceBefor) {
		this.balenceBefor = balenceBefor;
	}

	public double getBalenceAfter() {
		return balenceAfter;
	}

	public void setBalenceAfter(double balenceAfter) {
		this.balenceAfter = balenceAfter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(balenceAfter);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(balenceBefor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
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
		TransactionDto other = (TransactionDto) obj;
		if (accountId != other.accountId)
			return false;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (Double.doubleToLongBits(balenceAfter) != Double.doubleToLongBits(other.balenceAfter))
			return false;
		if (Double.doubleToLongBits(balenceBefor) != Double.doubleToLongBits(other.balenceBefor))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TransactionDto [id=" + id + ", type=" + type + ", amount=" + amount + ", date=" + date + ", accountId="
				+ accountId + ", balenceBefor=" + balenceBefor + ", balenceAfter=" + balenceAfter + "]";
	}
}

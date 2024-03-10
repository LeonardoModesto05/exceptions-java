package model.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import model.exceptions.DomainException;

public class Reservation {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private Integer roomNumber;
	private Date checkin;
	private Date checkout;
	
	public Reservation ()
	{
		
	}
	
	public Reservation (Integer roomNumber, Date checkin, Date checkout) 
	{
		this.roomNumber = roomNumber;
		Date now = checkout;
		if (now.before(checkin))
		{
			throw new DomainException("Error in reservation: Check-Out must be after check-in date");
		}
		this.checkin = checkin;
		this.checkout = checkout;
		
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Date getCheckin() {
		return checkin;
	}

	public Date getCheckout() {
		return checkout;
	}
	
	public long duration ()
	{
		long diff = checkout.getTime() - checkin.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	public void updateDate (Date checkin, Date checkout) 
	{
		Date now = new Date();
		if (checkin.before(now) || checkout.before(now))
		{
			throw new DomainException ("Reservation Dates for updates must be future dates");
		} else
		if (!checkout.after(checkin))
		{
			throw new DomainException ("Error in reservation: Check-Out must be after check-in date");
		}
		
		this.checkin = checkin;
		this.checkout = checkout;
		
	}
	
	@Override 
	public String toString()
	{
		return "Room " + 
				roomNumber + ", check-in: " +
				sdf.format(checkin) + ", check-out: " + 
				sdf.format(checkout) + ", " + duration() + " nights ";
	}
}

public class Sales
{
	private String code;
	private String name;
	private int purchaseNumber;
	private int price;
	private String date;
	
	Sales()
	{
		
	}
	Sales(String code,String name,int purchaseNumber,int price,String date)
	{
		this.code=code;
		this.name=name;
		this.purchaseNumber=purchaseNumber;
		this.price=price;
		this.date=date;
	}
	
	public String getCode()
	{
		return code;
	}
	public String getName()
	{
		return name;
	}
	public int getPurchaseNumber()
	{
		return purchaseNumber;
	}
	public int getPrice()
	{
		return price;
	}
	public String getDate()
	{
		return date;
	}
}
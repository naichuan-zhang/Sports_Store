public class Stock
{
	private String code;
	private int stockAmount;
	private int price;
	private String name;
	
	Stock()
	{
		
	}
	Stock(String code,int stockAmount,int price,String name)
	{
		this.code=code;
		this.stockAmount=stockAmount;
		this.price=price;
		this.name=name;
	}
	
	public void setCode(String code)
	{
		this.code=code;
	}
	public String getCode()
	{
		return code;
	}
	
	public void setStockAmount(int stockAmount)
	{
		this.stockAmount=stockAmount;
	}
	public int getStockAmount()
	{
		return stockAmount;
	}
	
	public void setPrice(int price)
	{
		this.price=price;
	}
	public int getPrice()
	{
		return price;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return name;
	}
}
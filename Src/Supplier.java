public class Supplier
{
	private String supplierCode;
	private String purchaseCode;
	private String stockName;
	private int supplyAmount;
	private String supplyDate;
	
	Supplier()
	{
		
	}
	Supplier(String supplierCode,String purchaseCode,String stockName,int supplyAmount,String supplyDate)
	{
		this.supplierCode=supplierCode;
		this.purchaseCode=purchaseCode;
		this.stockName=stockName;
		this.supplyAmount=supplyAmount;
		this.supplyDate=supplyDate;
	}
	
	public String getSupplierCode()
	{
		return supplierCode;
	}
	public String getPurchaseCode()
	{
		return purchaseCode;
	}
	public String getStockName()
	{
		return stockName;
	}
	public int getSupplyAmount()
	{
		return supplyAmount;
	}
	public String getSupplyDate()
	{
		return supplyDate;
	}
}
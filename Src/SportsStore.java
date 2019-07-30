		// Objected-Oriented Development Project
			// 16110543008	Zhang Naichuan
import java.util.*;
import java.io.*;
import java.text.*;
import javax.swing.JOptionPane;
public class SportsStore
{
	static Employee anEmployee;
	static Stock aStock;
	static Scanner in=new Scanner(System.in);
	static Scanner in3=new Scanner(System.in);
	static String employeeInput="";
	static String employeeIDEntered="";
	static String fileElements[];
	static ArrayList<Stock> allStocks;
	static ArrayList<Sales> allSales;
	static ArrayList<Supplier> allSuppliers;
	static String employeeFileName;
	static String stockFileName;
	static String salesFileName;
	static String supplierFileName;
	public static void main(String [] args) throws IOException, ParseException
	{
		boolean LOOP=true;
		while(LOOP)
		{
			employeeFileName=args[0]+".csv";
			stockFileName=args[1]+".csv";
			salesFileName=args[2]+".csv";
			supplierFileName=args[3]+".csv";
			Scanner input=new Scanner(System.in);
			System.out.println("\nWelcome to SDUT Sports Store.\n\nLog I)n     Log O)ut     P)urchase     R)eturn");
			employeeInput=input.nextLine();
			if(employeeInput!=null)
			{
				boolean LOOP2=true;
				while(LOOP2)
				{
					if(employeeInput.equalsIgnoreCase("I"))
					{
						System.out.println("Enter Employee ID");
						employeeIDEntered=input.nextLine();
						File employeeFile=new File(employeeFileName);
						in=new Scanner(employeeFile);
						while(in.hasNext())
						{
							fileElements=(in.nextLine()).split(",");
							if(employeeIDEntered.equalsIgnoreCase(fileElements[0]))
							{
								if(fileElements[2].equals("Manager"))
								{
									anEmployee=new Manager(fileElements[0],fileElements[1]);
									System.out.println(anEmployee.toString());
									theManagerMethod(allStocks,fileElements[0],fileElements[1]);
								}
								if(fileElements[2].equals("Assistant"))
								{
									anEmployee=new Assistant(fileElements[0],fileElements[1]);
									System.out.println(anEmployee.toString());
									theAssistantMethod(fileElements[0],fileElements[1]);
								}
							}
						}
						in.close();
						LOOP2=false;
						LOOP=true;
					}
					else if(employeeInput.equalsIgnoreCase("O"))
					{
						System.out.println("You have signed out the program.");
						LOOP2=false;
						LOOP=false;
					}
					else if(employeeInput.equalsIgnoreCase("P"))
					{
						System.out.println("You cannot purchase anything before logging in. Please try again!");
						LOOP2=false;
						LOOP=true;
					}
					else if(employeeInput.equalsIgnoreCase("R"))
					{
						System.out.println("You cannot return anything before logging in. Please try again!");
						LOOP2=false;
						LOOP=true;
					}
					else
					{
						System.out.println("Invalid input. Please re-try!");
						LOOP2=false;
						LOOP=true;
					}
				}
			}
			else
				System.out.println("Please enter a character(I/O/P/R)");
		}
	}
	
	/** log in as an assistant */

	public static void theAssistantMethod(String employeeID,String employeeName) throws IOException, ParseException
	{
		Calendar cal = Calendar.getInstance();
		TimeZone timeZone = cal.getTimeZone();
		System.out.println("\nYour region: "+timeZone.getID());
		System.out.println(timeZone.getDisplayName());
		GregorianCalendar aCalendar=new GregorianCalendar();
		Scanner in2=new Scanner(System.in);
		allStocks=new ArrayList<Stock>();
		aStock=new Stock();
		String purchaseCode="";
		boolean LOOP3=true;
		while(LOOP3)
		{
			System.out.println("\nYour position is an assistant.\n\nLog I)n     Log O)ut     P)urchase     R)eturn");
			employeeInput=in2.nextLine();
			if(employeeInput!=null)
			{
				if(employeeInput.equalsIgnoreCase("I"))
				{
					System.out.println("You have logged in and cannot be logged in again!");
					LOOP3=true;
				}
				else if(employeeInput.equalsIgnoreCase("O"))
				{
					System.out.println("You have logged out now.");
					LOOP3=false;
				}
				else if(employeeInput.equalsIgnoreCase("P"))
				{
					allSales = new ArrayList<Sales>();
					boolean transactionComplete=false;
					boolean validProductCode=false;
					boolean validProductQuantity=false;
					while(!transactionComplete)
					{
						arrayListOfObjectReadMethod(allStocks);
						validProductCode=false;
						validProductQuantity=false;
						while(!validProductCode)
						{
							Scanner in5=new Scanner(System.in);
							System.out.println("Enter Purchase Code:");
							purchaseCode=in5.nextLine();
							Stock aStock=getProductFromStockByID(purchaseCode);
							if(aStock!=null)
							{
								System.out.println("\nCurrent Stock: "+aStock.getStockAmount()+
													"\nPrice: "+aStock.getPrice());
								validProductCode=true;
								while(!validProductQuantity)
								{
									System.out.println("How many would you like to purchase?");
									int numberOfPurchase=in5.nextInt();
									if(aStock.getStockAmount()>=numberOfPurchase)
									{
										validProductQuantity=true;
										aCalendar=new GregorianCalendar();
										String systemDateAsString;
										Date systemDate;
										systemDateAsString=aCalendar.get(Calendar.DATE)+"/"+((aCalendar.get(Calendar.MONTH))+1)
																	+"/"+aCalendar.get(Calendar.YEAR);
										systemDate=convertStringToDate(systemDateAsString);
										Sales aSale = new Sales(purchaseCode,aStock.getName(),numberOfPurchase,aStock.getPrice(),systemDateAsString);
										allSales.add(aSale);
										
									}
									else
									{
										validProductQuantity=false;
										System.out.println("Stocks are not enough, please try it again!");
									}
								}
							}
							else
							{
								validProductCode=false;
								System.out.println("Invalid purchase code, please try it again!");
							}
						}
						Scanner in4=new Scanner(System.in);
						System.out.println("These items have been added\n\nP)urchase     C)omplete");
						employeeInput=in2.nextLine();
						if(employeeInput.equalsIgnoreCase("C"))
							transactionComplete=true;
						else
							transactionComplete=false;
					}
					printTransactionDetails(allSales);
					System.out.println("C)ontinue     S)top");
					employeeInput=in2.nextLine();
					if(employeeInput.equalsIgnoreCase("C"))
					{
						rewriteSalesFile();
						rewriteStockFile();
						System.out.println("Thank you for your purchase. Your receipt has been printed.");
					}
					else
					{
						allSales = new ArrayList<Sales>();
						System.out.println("You have cancelled the purchase.");
					}
					LOOP3=true;
				}
				else if(employeeInput.equalsIgnoreCase("R"))
				{
					String pattern="[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
					Scanner in6=new Scanner(System.in);
					String purchaseCodeWantToReturn="",systemDateAsString="",dateOfPurchaseAsString="";
					Date dateOfPurchase,systemDate;
					boolean loop6=true;
					while(loop6)
					{
						System.out.println("Please enter the purchase code of the product which you want to return:");
						purchaseCodeWantToReturn=in6.nextLine();
						if(checkIfValidSale(purchaseCodeWantToReturn))
							loop6=false;
						else
						{
							System.out.println("Invalid entered purchase code. Please re-try!");
							loop6=true;
						}
					}
					boolean loop3=true;
					while(loop3)
					{
						System.out.println("Please enter the purchase date of the product which you want to return: (dd/mm/yyyy)");
						dateOfPurchaseAsString=in6.nextLine();
						if(!(dateOfPurchaseAsString.matches(pattern)))
							System.out.println("Invalid input. Please re-try!");
						else
							loop3=false;
					}
					File salesFile=new File(salesFileName);
					Scanner in11=new Scanner(salesFile);
					int purchaseNumber=0;
					boolean found1=false;
					while(in11.hasNext())
					{
						fileElements=(in11.nextLine()).split(",");
						if(purchaseCodeWantToReturn.equalsIgnoreCase(fileElements[0])&&dateOfPurchaseAsString.equalsIgnoreCase(fileElements[4]))
						{
							found1=true;
							purchaseNumber=Integer.parseInt(fileElements[2]);
							break;
						}
						else
						{
							found1=false;
						}
					}
					in11.close();
					if(found1==false)
						System.out.println("No eligible record is found!");
					else
					{
						systemDateAsString=aCalendar.get(Calendar.DATE)+"/"+
										((aCalendar.get(Calendar.MONTH))+1)+
										"/"+aCalendar.get(Calendar.YEAR);
						systemDate=convertStringToDate(systemDateAsString);
						dateOfPurchase=convertStringToDate(dateOfPurchaseAsString);
						if(getDiscrepantDays(systemDate,dateOfPurchase) <= 30)
						{
							returnToStockFile(purchaseNumber,purchaseCodeWantToReturn);
							System.out.println("The product has been sent back.");
						}
						else
						{
							System.out.println("You cannot return product for more than 30 days.");
						}
					}
				}
				else
					LOOP3=true;
			}
		}
	}
	
	/** Create an arraylist of object called allStocks */

	public static void arrayListOfObjectReadMethod(ArrayList<Stock> allStocks) throws IOException
	{
		allStocks.clear();
		in3=new Scanner(System.in);
		Stock detailsOfAStock;
		String lineFromFile="";
		String code,name;
		int stockAmount,price;
		File stockFile=new File(stockFileName);
		in3=new Scanner(stockFile);
		while(in3.hasNext())
		{
			fileElements=(in3.nextLine()).split(",");
			code=fileElements[0];
			stockAmount=Integer.parseInt(fileElements[1]);
			price=Integer.parseInt(fileElements[2]);
			name=fileElements[3];
			detailsOfAStock=new Stock(code,stockAmount,price,name);
			allStocks.add(detailsOfAStock);
		}
		in3.close();
	}
	
	/** check whether the entered stock ID is valid */

	public static boolean checkIfValidStock(ArrayList<Stock> allStocks,String purchaseCode)
	{
		boolean found = false;
		for(int i=0;i<allStocks.size() && !found;i++)
		{
			if(purchaseCode.equalsIgnoreCase(allStocks.get(i).getCode()))
				found = true;
	    }
		return found;
	}
	
	/** check whether the entered purchase amount is valid */

	public static boolean checkIfValidQuantity(ArrayList<Stock> allStocks,int numberOfPurchase)
	{
		boolean found = false;
		for(int i=0;i<allStocks.size() && !found;i++)
		{
			if(numberOfPurchase<=allStocks.get(i).getStockAmount())
				found = true;
		}
		return found;
	}
	
	/** Update the stock.csv file */

	public static void rewriteStockFile() throws IOException
	{
		File stockFile=new File(stockFileName);
		PrintWriter update=new PrintWriter(stockFile);
		for(Sales S : allSales)
		{
			String purchaseCode = S.getCode();
			int numberOfPurchase = S.getPurchaseNumber();
			for(int i=0;i<allStocks.size();i++)
			{
				if(purchaseCode.equalsIgnoreCase(allStocks.get(i).getCode())) 
				{
					int num = allStocks.get(i).getStockAmount() - numberOfPurchase;
					allStocks.get(i).setStockAmount(num);
				}
			}
		}
		for(int i=0;i<allStocks.size();i++)
		{
			update.println(allStocks.get(i).getCode()+","+allStocks.get(i).getStockAmount()+","+
									allStocks.get(i).getPrice()+","+allStocks.get(i).getName());
		}
		update.close();
	}
	
	/** Update the sales.csv file */

	public static void rewriteSalesFile() throws IOException
	{
		FileWriter salesFile=new FileWriter(salesFileName,true);
		PrintWriter update=new PrintWriter(salesFile);
		for(int i=0;i<allSales.size();i++)
		{
			update.println(allSales.get(i).getCode()+","+allSales.get(i).getName()
							+","+allSales.get(i).getPurchaseNumber()+","+
							allSales.get(i).getPrice()+","+allSales.get(i).getDate());
		}
		update.close();
	}
	
	/** get the stock from stock file by its ID */

	public static Stock getProductFromStockByID(String ID) throws IOException
	{
		Stock result = null;
		for(Stock S : allStocks)
		{
			if(S.getCode().equalsIgnoreCase(ID))
			{
				result = S;
				break;
			}
		}
		
		return result;
		
	}
	
	/** overwrite stock file when return goods */
	
	public static void returnToStockFile(int purchaseNumber,String purchaseCodeWantToReturn) throws IOException
	{
		arrayListOfObjectReadMethod(allStocks);
		for(Stock K : allStocks)
		{
			if(K.getCode().equalsIgnoreCase(purchaseCodeWantToReturn))
			{
				K.setStockAmount(K.getStockAmount()+purchaseNumber);
			}
		}
		File stockFile=new File(stockFileName);
		PrintWriter update=new PrintWriter(stockFile);
		for(int i=0;i<allStocks.size();i++)
		{
			update.println(allStocks.get(i).getCode()+","+allStocks.get(i).getStockAmount()+","+
							allStocks.get(i).getPrice()+","+allStocks.get(i).getName());
		}
		update.close();
	}
	
	/** get a transaction */

	public static void printTransactionDetails(ArrayList<Sales> allSales) throws IOException
	{
		double subtotal=0,tax=0,total=0;
		int getRegion=selectRegion();
		System.out.println("Transaction Details\n----------------------------------------------");
		for(int i=0;i<allSales.size();i++)
		{
			System.out.println(allSales.get(i).getName()+"\t\t"+allSales.get(i).getPurchaseNumber()+"\t\t"+allSales.get(i).getPrice());
			subtotal += (allSales.get(i).getPrice() * allSales.get(i).getPurchaseNumber());
		}
		if(getRegion==1)
		{
			tax=subtotal/10.0;
			total=subtotal+tax;
			System.out.println("Sub-Total\t\t\t\t"+subtotal+"\nTax(10%)\t\t\t\t"+tax+"\nTotal\t\t\t\t"+total);
			System.out.println("----------------------------------------------");
		}
		else if(getRegion==2)
		{
			tax=subtotal/20.0;
			total=subtotal+tax;
			System.out.println("Sub-Total\t\t\t\t"+subtotal+"\nTax(20%)\t\t\t\t"+tax+"\nTotal\t\t\t\t"+total);
			System.out.println("----------------------------------------------");
		}
		else
		{
			tax=subtotal/20.0;
			total=subtotal+tax;
			System.out.println("Sub-Total\t\t\t\t"+subtotal+"\nTax(20%)\t\t\t\t"+tax+"\nTotal\t\t\t\t"+total);
			System.out.println("----------------------------------------------");
		}
	}
	
	/** get the region by using TimeZone */
	
	public static int selectRegion()
	{
		int getRegion=0;
		Calendar cal = Calendar.getInstance();
		TimeZone timeZone = cal.getTimeZone();
		System.out.println("\nYour region: "+timeZone.getID());
		System.out.println(timeZone.getDisplayName());
		String ID=timeZone.getID();
		switch(ID)
		{
			case "Asia/Shanghai": getRegion=1; break;
			case "Europe/Dublin": getRegion=2; break;
			default: break;
		}
		return getRegion;
	}
	
	/** log in as a manager */

	public static void theManagerMethod(ArrayList<Stock> allStocks,String employeeID,String employeeName) throws IOException, ParseException
	{
		Calendar cal = Calendar.getInstance();
		TimeZone timeZone = cal.getTimeZone();
		System.out.println("\nYour region: "+timeZone.getID());
		System.out.println(timeZone.getDisplayName());
		GregorianCalendar aDate=new GregorianCalendar();
		Scanner in2=new Scanner(System.in);
		aStock=new Stock();
		System.out.println("----------------------------------------------\n"+
						"Your position is a manager. You can retrieve the following information from the system:\n"+
						"1. A summary of all items currently in stock\n2. Details relating to a specific items in stock\n"+
						"3. A summary of all sales over a chosen period of time (days/weeks/months/years)\n"+
						"4. Return products to supplier\n----------------------------------------------");
		System.out.println("Please select one number that you want to retrieve (1/2/3/4):");
		int aNumber=in2.nextInt();
		boolean LOOP4=true;
		while(LOOP4)
		{
			if(aNumber==1)
			{
				System.out.println("\nThe current items in stock are as following: ");
				System.out.println("----------------------------------------------");
				System.out.println("Stock Name\t\tStock Amount");
				File stockFile=new File(stockFileName);
				in2=new Scanner(stockFile);
				while(in2.hasNext())
				{
					fileElements=(in2.nextLine()).split(",");
					System.out.println(fileElements[3]+"\t\t\t"+fileElements[1]);
				}
				in2.close();
				System.out.println("----------------------------------------------");
				LOOP4=false;
			}
			else if(aNumber==2)
			{
				boolean loop5=true;
				String purchaseCodeWantToBeInquired="";
				while(loop5)
				{
					System.out.println("Please enter the purchase code you want to inquire:");
					purchaseCodeWantToBeInquired=in2.next();
					if(!(checkIfValidSale(purchaseCodeWantToBeInquired)))
						System.out.println("No such purchase code. Please re-try!");
					else
						loop5=false;
				}
				System.out.println("\nThe details of the stock: ");
				System.out.println("----------------------------------------------");
				File stockFile=new File(stockFileName);
				in2=new Scanner(stockFile);
				while(in2.hasNext())
				{
					fileElements=(in2.nextLine()).split(",");
					if(purchaseCodeWantToBeInquired.equalsIgnoreCase(fileElements[0]))
					{
						aStock.setStockAmount(Integer.parseInt(fileElements[1]));
						aStock.setPrice(Integer.parseInt(fileElements[2]));
						aStock.setName(fileElements[3]);
						System.out.println("Stock Name: "+aStock.getName()+"\nCurrent Stock: "+aStock.getStockAmount()+"\nPrice: "+aStock.getPrice());
					}
				}
				in2.close();
				System.out.println("----------------------------------------------");
				LOOP4=false;
			}
			else if(aNumber==3)
			{
				Scanner in0=new Scanner(System.in);
				Date startingDate,endingDate,purchaseDate;
				String startingDateAsString="",endingDateAsString="";
				String pattern="[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
				boolean loop1=true,loop2=true;
				while(loop1)
				{
					System.out.println("Please enter a starting date (DD/MM/YYYY):");
					startingDateAsString=in0.nextLine();
					if(!(startingDateAsString.matches(pattern)))
						System.out.println("Invalid entered date. Please re-try!");
					else
						loop1=false;
				}
				while(loop2)
				{
					System.out.println("Please enter an ending date (DD/MM/YYYY):");
					endingDateAsString=in0.nextLine();
					if(!(endingDateAsString.matches(pattern)))
						System.out.println("Invalid entered date. Please re-try!");
					else
						loop2=false;
				}
				System.out.println("The sales' details are as following:\n----------------------------------------------");
				System.out.println("Name\t\tPurchase Number\t\tPrice\t\t");
				startingDate=convertStringToDate(startingDateAsString);
				endingDate=convertStringToDate(endingDateAsString);
				File salesFile=new File(salesFileName);
				in3=new Scanner(salesFile);
				while(in3.hasNext())
				{
					fileElements=(in3.nextLine()).split(",");
					purchaseDate=convertStringToDate(fileElements[4]);
					if(purchaseDate.compareTo(startingDate)>=0&&purchaseDate.compareTo(endingDate)<=0)
						System.out.println(fileElements[1]+"\t\t"+fileElements[2]+"\t\t"+fileElements[3]);
				}
				in3.close();
				System.out.println("----------------------------------------------");
				LOOP4=false;
			}
			else if(aNumber==4)
			{
				Sales returnSales=new Sales();
				GregorianCalendar aCalendar=new GregorianCalendar();
				String pattern="[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
				Scanner in6=new Scanner(System.in);
				String purchaseCodeWantToReturn="",systemDateAsString="",dateOfPurchaseAsString="";
				Date dateOfPurchase,systemDate;
				boolean loop7=true;
				while(loop7)
				{
					System.out.println("Please enter the purchase code of the product which you want to return:");
					purchaseCodeWantToReturn=in6.nextLine();
					if(checkIfValidSale(purchaseCodeWantToReturn))
						loop7=false;
					else
					{
						System.out.println("Invalid entered purchase code. Please re-try!");
						loop7=true;
					}
				}
				boolean loop3=true;
				while(loop3)
				{
					System.out.println("Please enter the purchase date of the product which you want to return: (dd/mm/yyyy)");
					dateOfPurchaseAsString=in6.nextLine();
					if(!(dateOfPurchaseAsString.matches(pattern)))
						System.out.println("Invalid input. Please re-try!");
					else
						loop3=false;
				}
				File salesFile=new File(salesFileName);
				Scanner in12=new Scanner(salesFile);
				boolean found=false;
				while(in12.hasNext())
				{
					fileElements=(in12.nextLine()).split(",");
					if(purchaseCodeWantToReturn.equalsIgnoreCase(fileElements[0])&&dateOfPurchaseAsString.equalsIgnoreCase(fileElements[4]))
					{
						found=true;
						returnSales=new Sales(fileElements[0],fileElements[1],Integer.parseInt(fileElements[2]),Integer.parseInt(fileElements[3]),fileElements[4]);
						break;
					}
					else
					{
						found=false;
					}
				}
				if(found==false)
					System.out.println("No eligible record is found!");
				else
				{	
					systemDateAsString=aCalendar.get(Calendar.DATE)+"/"+
									((aCalendar.get(Calendar.MONTH))+1)+
									"/"+aCalendar.get(Calendar.YEAR);
					systemDate=convertStringToDate(systemDateAsString);					
					dateOfPurchase=convertStringToDate(dateOfPurchaseAsString);
					if(getDiscrepantDays(systemDate,dateOfPurchase) <= 30)
					{
						returnToSupplierFile(returnSales,systemDateAsString);
						System.out.println("The product has been sent back to supplier.");
					}
					else
					{
						System.out.println("You cannot return product for more than 30 days.");
					}
					break;
				}
			}
			else
			{
				System.out.println("Invalid entered number. Please try again!");
				LOOP4=false;
			}
		}
	}
	
	/** check whether the entered sale ID exists */
	
	public static boolean checkIfValidSale(String purchaseCode) throws IOException
	{
		File salesFile=new File(salesFileName);
		Scanner in6=new Scanner(salesFile);
		while(in6.hasNext())
		{
			fileElements=(in6.nextLine()).split(",");
			if(fileElements[0].equalsIgnoreCase(purchaseCode))
				return true;
		}
		in6.close();
		return false;
	}
	
	/** update supplier.csv file when user return goods */
	
	public static void returnToSupplierFile(Sales returnSales,String systemDateAsString) throws IOException
	{
		File supplierFile=new File(supplierFileName);
		Scanner in12=new Scanner(supplierFile);
		String supplierID="";
		while(in12.hasNext())
		{
			fileElements=(in12.nextLine()).split(",");
			if(returnSales.getCode().equalsIgnoreCase(fileElements[1]))
				supplierID=fileElements[0];
		}
		in12.close();
		FileWriter supplierFileWriter=new FileWriter(supplierFileName,true);
		PrintWriter updateSupplier=new PrintWriter(supplierFileWriter);
		updateSupplier.println(supplierID+","+returnSales.getCode()+","+returnSales.getName()+","+returnSales.getPurchaseNumber()+","+systemDateAsString);
		updateSupplier.close();
	}
	
	/** convert string to date */
	
	public static Date convertStringToDate(String aDate) throws ParseException
	{
		DateFormat dateFormatter=new SimpleDateFormat("dd/MM/yyyy");
		Date dateValue=(Date) dateFormatter.parse(aDate);
		return dateValue;
	}
	
	/** get the discrepant of two dates */

	public static int getDiscrepantDays(Date dateStart,Date dateEnd)
	{
		return (int) ((dateEnd.getTime()-dateStart.getTime())/1000/60/60/24);
	}
}
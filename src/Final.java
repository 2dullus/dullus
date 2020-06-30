import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Final {
	
	static Scanner scan;
	static ArrayList<Double> given = new ArrayList<Double>();
	static ArrayList<Double> xm = new ArrayList<Double>();
	static ArrayList<Double> xm2 = new ArrayList<Double>();
	static ArrayList<Double> xi = new ArrayList<Double>();
	static ArrayList<Double> cleanxi = new ArrayList<Double>();
	static ArrayList<Integer> freq = new ArrayList<Integer>();
	static ArrayList<Double> px = new ArrayList<Double>();
	static ArrayList<Double> xim = new ArrayList<Double>();
	static ArrayList<Double> xim2 = new ArrayList<Double>();
	static ArrayList<Double> pxxim2 = new ArrayList<Double>();
	static int r = 0;
	static int n = 0;
	static int ncr = 0;
	static int counter = 0;
	static double mean = 0;
	static double standardDev = 0;
	static double variance = 0;
	static double standardDevI = 0;
	static double varianceI = 0;
	
	//Taking Input givens
	public static void Input() {		
		
		System.out.println("(End your entry by entering a 0 value)");
		System.out.println("Enter Given : ");
		
		for (int i = 0; i < given.size() + 1; i++) {
			scan = new Scanner(System.in);
			double g = scan.nextDouble();
			
			if (g == 0) {
				
				break;
			}
			given.add(g);
			counter++;
		}
		
		System.out.println("Enter n :");
		Scanner n1 = new Scanner(System.in);
		r = n1.nextInt();
		n1.close();
		
		n = given.size();
	}
	
	//Calculate mean
	public static void Mean(ArrayList<Double> data) {
		mean = 0;
		
		for (int i = 0; i < data.size(); i++)
			mean = mean + data.get(i);
		mean = mean / data.size();
	}
	
	//Sums up an ArrayList
	public static double sum(ArrayList<Double> data) {
		double sum = 0;
		for (int i = 0; i < data.size(); i++) {
			sum = sum + data.get(i);
		}
		BigDecimal bd = new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP);
		return sum = bd.doubleValue();
	}
	
	//Factorial of a number
	public static int fact(int n) {
		int fact = 1;
		for ( int i = 1; i <= n; i++) {
			fact = fact*i;
		}
	
		return fact;
	}
	
	static void combinationUtil(ArrayList<Double> given, ArrayList<Double> data, int start, int end, int index, int r ) {
		if (index == r) {
			double c = 0;
			for ( int i = 0; i < r; i++)
				c = c + data.get(i);
			c = c / r;
			BigDecimal bd = new BigDecimal(c).setScale(2, RoundingMode.HALF_UP);
			xi.add(bd.doubleValue());
			return;
		}
		
		for (int x = start; x <= end && end - x + 1 >= r - index; x++) {
			data.set(index, given.get(x));
			
		    combinationUtil(given, data, x + 1, end, index + 1, r);
		}
	}
	
	public static void printComb(ArrayList<Double> given, int n, int r) {
		@SuppressWarnings("unchecked")
		ArrayList<Double> data = (ArrayList<Double>)given.clone();
		combinationUtil(given, data, 0, n - 1, 0, r);
	}
	
	static void combinationPrint(ArrayList<Double> given, ArrayList<Double> data, int start, int end, int index, int r) {
		if (index == r) {
			System.out.print("         | ");
			double xi = 0;
			for (int i = 0; i < r ; i++) {
				System.out.printf("%5s ",data.get(i));
				xi += data.get(i);
			}
			xi = xi / r;
			System.out.printf("  | %5s | %n", xi);
			return;
			
		}
		
		for (int x = start; x <= end && end - x + 1 >= r - index; x++) {
			data.set(index, given.get(x));
			
		    combinationPrint(given, data, x + 1, end, index + 1, r);
		}
	}
	
	public static void combPrint(ArrayList<Double> given, int n, int r) {
		@SuppressWarnings("unchecked")
		ArrayList<Double> data = (ArrayList<Double>)given.clone();
		combinationPrint(given, data, 0, n - 1, 0, r);
	}
	
	public static void Frequency(ArrayList<Double> cleanx) {
		for ( int i = 0; i < cleanx.size(); i++) {
			freq.add(Collections.frequency(xi, cleanx.get(i)));
		}
	}
	
	public static void PofX(ArrayList<Integer> freq) {
		for ( int i = 0; i < freq.size(); i++) {
			px.add((double) (freq.get(i)/ (double) xi.size()));
		}
	}
	
	public static void Calculate() {
		Mean(given);
		
		ncr = fact(n) / (fact(r)*fact(n-r));
		
		//Calculate x - mean
		for (int i = 0; i < given.size();i++ ) {
			 double data = given.get(i) - mean;
			 BigDecimal bd = new BigDecimal(data).setScale(2, RoundingMode.HALF_UP);
			 xm.add(bd.doubleValue());
		}
		
		//Calculate x - mean ^ 2
		for (int i = 0; i < given.size(); i++ ) {
			double data = Math.pow(xm.get(i),2);
			BigDecimal bd = new BigDecimal(data).setScale(2, RoundingMode.HALF_UP);
			xm2.add(bd.doubleValue());
		}
		
		//Calculate Standard Deviation and Variance
		BigDecimal bd = new BigDecimal(sum(xm2) / given.size()).setScale(2, RoundingMode.HALF_UP);
		standardDev = bd.doubleValue();
		BigDecimal bd1 = new BigDecimal(Math.sqrt(standardDev)).setScale(2, RoundingMode.HALF_UP);
		variance = bd1.doubleValue();
		
		//Prints all combinations of the given array 
		printComb(given, n ,r);
		//Sort the value from lowest to highest
		Collections.sort(xi);
		
		//Remove duplicating value in the array
		cleanxi = (ArrayList<Double>) xi.stream().distinct().collect(Collectors.toList());
		//Finds out the frequency of the duplicated values in the original array
		Frequency(cleanxi);
		//Divide the frequency to the size of the main array
		PofX(freq);
		
		//Calculate xi - mean (I call it xi because it's an x with a line above it) 
		for ( int i = 0; i < cleanxi.size(); i++) {
			BigDecimal bd3 = new BigDecimal(cleanxi.get(i) - mean ).setScale(2, RoundingMode.HALF_UP);
			xim.add(bd3.doubleValue());
		}
		
		//Calculate xi - mean ^ 2
		for ( int i = 0; i < xim.size(); i++) {
			BigDecimal bd3 = new BigDecimal(Math.pow(xim.get(i), 2)).setScale(2, RoundingMode.HALF_UP);
			xim2.add(bd3.doubleValue());
		}
		
		//Calculate P(x) * xi - mean ^ 2
		for ( int i = 0; i < xim2.size(); i++) {
			BigDecimal bd3 = new BigDecimal(px.get(i) * xim2.get(i)).setScale(3, RoundingMode.HALF_UP);
			pxxim2.add(bd3.doubleValue());
		}
		
		//Calculate Standard deviation and Variance
		standardDevI = sum(pxxim2);
		BigDecimal bd3 = new BigDecimal(Math.sqrt(standardDevI)).setScale(2, RoundingMode.HALF_UP);
		varianceI = bd3.doubleValue();
	}
	
	public static void Output() throws InterruptedException {
		Thread.sleep(700);
		System.out.println(" +==-----------------------------------------==+");
		System.out.println("  Calculating Answers for the ff given > ");
		System.out.println("  " +  given + " | n = " + r);
		System.out.println(" +==-----------------------------------------==+");
		
		Thread.sleep(3000);
		System.out.println("");
		System.out.println("");
		System.out.println(" +==-----------------------------------------==+");
		System.out.printf("  | %10s | %10s | %10s | %n", "x","x-m","x-m^2");
		for (int i = 0; i < given.size(); i++)
			System.out.printf("  | %10s | %10s | %10s | %n", given.get(i),xm.get(i),xm2.get(i));
		System.out.println(" +---------------------------------------------+");
		System.out.printf("  | %6s /%2s | %6s /%2s |%12s| %n", sum(given), given.size(), sum(xm2), given.size(), " ");
		System.out.printf("  |%3s%8s |%5s%6s |%3s%8s | %n", "m =", mean, "\u03A3".toLowerCase() + "^2 =", standardDev, "\u03A3".toLowerCase() + " =", variance);
		System.out.println(" +==-----------------------------------------==+");
		
		combPrint(given, n, r);
		
		System.out.println(" +==-----------------------------------------==+");
		for (int i = 0; i < cleanxi.size(); i++) 
			System.out.printf( "  | %5s | %2s/%2s | %5s | %5s | %5s |  %n",cleanxi.get(i),freq.get(i), ncr,xim.get(i),xim2.get(i),pxxim2.get(i));
		System.out.println(" +---------------------------------------------+");
		System.out.printf( "  | %22s%9s%6s |  %n" , " ", "\u03A3".toLowerCase() + "^2x =", standardDevI);
		System.out.printf( "  | %3s%7s%11s%9s%6s |  %n" , "mx =", mean," ", "\u03A3".toLowerCase() + "x =", varianceI);
		System.out.println(" +==-----------------------------------------==+");
	}

	public static void main(String[] args) throws InterruptedException {
		Input();
		Calculate();
		Output();
		/*System.out.println(given);
		System.out.println(xm);
		System.out.println(xm2);
		System.out.println(standardDev);
		System.out.println(variance);
		System.out.println(xi);
		System.out.println(cleanxi);
		System.out.println(freq);
		System.out.println(px);
		System.out.println(xim);
		System.out.println(xim2);
		System.out.println(pxxim2);
		System.out.println(standardDevI);
		System.out.println(varianceI);*/
	}
}
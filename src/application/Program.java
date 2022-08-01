package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			List<Sale> list = new ArrayList<>();
			
			String line = br.readLine();
			while (line != null) {
				String[] sales = line.split(",");
				int month = Integer.parseInt(sales[0]);
				int year = Integer.parseInt(sales[1]);
				String seller = sales[2];
				int items = Integer.parseInt(sales[3]);
				double total = Double.parseDouble(sales[4]);
				
				list.add(new Sale(month, year, seller, items, total));
				line = br.readLine();
			}
			
			Comparator<Sale> comp = (d1, d2) -> d1.averagePrice().compareTo(d2.averagePrice());
			List<Sale> higher = list.stream()
					.filter(x -> x.getYear() == 2016)
					.sorted(comp.reversed())
					.limit(5)
					.collect(Collectors.toList());
			
			double totalValue = list.stream()
					.filter(x -> x.getSeller().equals("Logan") && (x.getMonth() == 1 || x.getMonth() == 7))
					.map(x -> x.getTotal())
					.reduce(0.0, (x, y) -> x + y);
			
			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			higher.forEach(System.out::println);
			
			System.out.println();
			System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", totalValue));
		}
		catch (IOException e) {
			System.out.println("Erro: " + path + " (O sistema não pode encontrar o arquivo especificado)");
		}
		
		sc.close();
	}
}

package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDemo {

	public static void main(String[] args) {
		
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		List<StudentRecord> records = convert(lines);
		int zadatak = 1;
		
		//Prvi
		printZadatak(zadatak++);
		long broj = vratiBodovaViseOd25(records);
		System.out.println(broj);
		
		//Drugi
		printZadatak(zadatak++);
		broj = vratiBrojOdlikasa(records);
		System.out.println(broj);
		
		//Teci
		printZadatak(zadatak++);
		List<StudentRecord> list = vratiListuOdlikasa(records);
		for(StudentRecord record : list) {
			System.out.println(record);
		}
		
		//Cetvrti
		printZadatak(zadatak++);
		list = vratiSortiranuListuOdlikasa(records);
		for(StudentRecord record : list) {
			System.out.println(record);
		}
		
		//Peti
		printZadatak(zadatak++);
		List<String> jmbagList = vratiPopisNepolozenih(records);
		for(String jmbag : jmbagList) {
			System.out.println(jmbag);
		}
		
		//Šesti
		printZadatak(zadatak++);
		Map<Integer, List<StudentRecord>> map = razvrstajStudentePoOcjenama(records);
		for(Map.Entry<Integer, List<StudentRecord>> entry : map.entrySet()) {
			System.out.println("Studenti s ocjenom " + entry.getKey() + ":");
			for(StudentRecord record : entry.getValue()) {
				System.out.println(record);
			}
		}
		
		//Sedmi
		printZadatak(zadatak++);
		Map<Integer, Long> map2 = vratiBrojStudenataPoOcjenama(records);
		for(Map.Entry<Integer, Long> entry : map2.entrySet()) {
			System.out.println("Broj studenata s ocjenom " + entry.getKey() + " je " + entry.getValue());
		}
		
		//Osmi
		printZadatak(zadatak++);
		Map<Boolean, List<StudentRecord>> map3 = razvrstajProlazPad(records);
		for(Map.Entry<Boolean, List<StudentRecord>> entry : map3.entrySet()) {
			if(entry.getKey().equals(true)) {
				System.out.println("Studenti koji su prošli:");
			} else {
				System.out.println("Studenti koji su pali:");
			}
			for(StudentRecord record : entry.getValue()) {
				System.out.println(record);
			}
		}
		
	}
	
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.partitioningBy(r -> r.getOcjena() > 1));
	}

	private static Map<Integer, Long> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
//		return records.stream()
//				.collect(Collectors.groupingBy(StudentRecord::getOcjena, Collectors.counting()));

		return records.stream()
				.collect(Collectors.toMap(StudentRecord::getOcjena, r -> (Long.valueOf(1)), (o, n) -> o+1));
	}

	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getOcjena));
	}

	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		Comparator<StudentRecord> PO_JMBAGU = (r1, r2) -> r1.getJmbag().compareTo(r2.getJmbag());
		return records.stream()
				.filter(r -> r.getOcjena() == 1)
				.sorted(PO_JMBAGU)
				.map(r -> r.getJmbag())
				.collect(Collectors.toList());
	}

	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream()
				.filter(r -> ((r.getMeduispit() + r.getZavrsni() + r.getLabosi()) > 25))
				.count();
	}
	
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(r -> r.getOcjena() == 5)
				.count();
	}
	
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(r -> r.getOcjena() == 5)
				.collect(Collectors.toList());
	}
	
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		Comparator<StudentRecord> PO_BODOVIMA = (r1, r2) -> Double.compare(r1.getZavrsni() + r1.getLabosi() + r1.getMeduispit(), 
																			r2.getZavrsni() + r2.getLabosi() + r2.getMeduispit());
		return records.stream()
				.filter(r -> r.getOcjena() == 5)
				.sorted(PO_BODOVIMA.reversed())
				.collect(Collectors.toList());
	}

	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> list= new ArrayList<>();
		
		for(String line : lines) {
			String[] data = line.split("\\s+");
			
			list.add(new StudentRecord(
					data[0], 
					data[1], 
					data[2], 
					Double.parseDouble(data[3]), 
					Double.parseDouble(data[4]), 
					Double.parseDouble(data[5]), 
					Integer.parseInt(data[6])));
		}
		
		return list;
	}
	
	private static void printZadatak(int zad) {
		System.out.println("Zadatak " + zad);
		System.out.println("=========");
	}
	
}

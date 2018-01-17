package PdfVis;

import Evolution.Genotype;
import components.Course;
import components.StudentGroup;
import components.Teacher;
import components.Timetable;
import inOut.LoadData;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;

public class PdfCreator {
	

	private LoadData data;
	String[] start_time = {"8.15","9.15","10.15","11.15","12.15","13.15","14.15","15.15","16.15","17.15","18.15","19.15"};//working hours start time
	String[] days = {"Pon","Wt","Sr","Czw","Pt","S","N"};//working days names
	public PdfCreator(LoadData d)
	{
		data = d;
	}
	
	/**
	 * Writes timetable represented by given genotype in human readable representation in pdf file
	 * @param gen genotype of timetable, which is to be saved in graphical (visual) representation
	 * @param filename output file path (relative to project directory)
	 * @throws FileNotFoundException if file error occurred
	 */
	public void genotypeToFile(Genotype gen, String filename) throws FileNotFoundException 
	{
		
		PdfWriter writer = new PdfWriter(filename); //init pdf writter
		PdfDocument pdf = new PdfDocument(writer); //init pdf document
		pdf.setDefaultPageSize(PageSize.A4.rotate());
		Document document = new Document(pdf); //init document
		
		
		
		HashMap<Integer,ClassTable> map = transformGenotype(gen);
		Table table;
		
		
		for (ClassTable ct:map.values()) //iterate through ClassTables for all student groups
		{
			
			document.add(new Paragraph(ct.k.getName())); //add student group name
			
			table = new Table(Timetable.workingDays+1);
			
			table.addCell("");
			
			for (int i=0; i<Timetable.workingDays;i++) //add day names
				table.addCell(days[i]);
			
			for (int i=0; i<Timetable.workingHours; i++) //add lessons
			{
				table.addCell(start_time[i]);
				
				for (int j=0; j<Timetable.workingDays; j++)
				{
					if (ct.table[j][i]==null) //empty slot
					{
						table.addCell("-");
					}
					else //occupied slot
					{
						int room = ct.table[j][i].room;
						Course z = getZajecie(ct.table[j][i].id);
						Teacher n = z.getTeacher();
						String text = z.getSubject().getName() + "\n" + n.getFirstName().charAt(0) +"." + n.getLastName() + "\nsala " + room;
						table.addCell(text);
					}
				}
			}
			document.add(table);
			document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		}
		
		document.close();
		
	}
	
	/**
	 * Converts timetable from genotype representation to Map, where key is student group id, and value is ClassTable 
	 * @param gen genotype to convert
	 * @see ClassTable
	 * @return Map<Student_group_id,ClassTable>
	 */
	private HashMap<Integer,ClassTable> transformGenotype(Genotype gen)
	{
		int days = Timetable.workingDays;
		int hours = Timetable.workingHours;
		int rooms = Timetable.availableClassrooms;
		ArrayList<StudentGroup> classess = data.getArrayStudentGroup();//list of all classes
		ArrayList<Integer> chrom = gen.getChromosome();
		
		HashMap<Integer,ClassTable> tableMap = new HashMap<Integer,ClassTable>();
		
		for (StudentGroup k:classess)
		{
			Integer id = k.getId();
			tableMap.put(id, new ClassTable(k));
		}
		
		int actDay = 1;
		int actHour = 1;
		int actRoom = 1;

		for (Integer i:chrom)
		{
			int id = i.intValue();
			int groupId;
		
			if (id!=0)//if timeslot is not empty
			{
								
				Course z =getZajecie(id);
				
				if (z==null)
				{
					for (Course zaj:data.getArrayCourses())
					{
						if (zaj.getId()==id)
							z=zaj;
							break;
					}
				}
				
				groupId = z.getStudentGroup().getId();
				tableMap.get(groupId).addClass(id, actDay, actHour, actRoom);//add Lesson to timetable of specific student group
			}
					
			//increment room, hour,day to iterate through entire genotype
			actRoom++;
			if (actRoom>rooms)	
			{
				actRoom=1;
				actHour++;
				if (actHour>hours)
				{
					actHour=1;
					actDay++;
					if (actDay>days)
						break;
				}
			}	
		
		}
			
		return tableMap;	
	}
	
	/**
	 * Get Zajecie object with specified id
	 * @param id
	 * @return
	 */
	private Course getZajecie(int id)
	{
		Course z = data.getArrayCourses().get(id-1);
		
/*		if (z==null || z.getId()!=id)
		{
			for (Classes zaj:data.getArrayZajecia())
			{
				if (zaj.getId()==id)
					z=zaj;
					break;
			}
		}
*/
		return z;
	}
	
}

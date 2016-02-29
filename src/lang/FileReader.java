package lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import lang.exceptions.InvalidFileException;

public class FileReader extends FObj{
	final Scanner source;
	public FileReader(String fname){
		try {
			source=new Scanner(new File(fname));
		} catch (FileNotFoundException e) {
			throw new InvalidFileException(fname);
		}
		set("readln",new Function(a->new FString(source.nextLine())));
		set("readnum",new Function(a->new FNum(source.nextLine())));
		immutableFields.add("readln");
		immutableFields.add("readnum");
	}
}

package exam;

public class RecordOfGrade {
	private int num;
	private String name;
	private int kor;
	private int eng;
	
	public RecordOfGrade(int num, String name, int kor, int eng) {
		this.num = num;
		this.name = name;
		this.kor = kor;
		this.eng = eng;
	}

	public int getNum() {
		return num;
	}

	public String getName() {
		return name;
	}

	public int getKor() {
		return kor;
	}

	public int getEng() {
		return eng;
	}
	
}

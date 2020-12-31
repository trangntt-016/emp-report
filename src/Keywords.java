import java.io.Serializable;

public class Keywords  implements Serializable{
	private String _fName;
	private String _lName;
	private String _department;
	private int _minSal;
	private int _maxSal;
	
	public Keywords() {};
	public Keywords(String firstName, String lastName, int min, int max, String department) {
		this._fName = firstName;
		this._lName = lastName;
		this._minSal = min;
		this._maxSal = max;
		this._department = department;

	};
	
	public String getFNameKW() {
		return _fName;
	}
	
	public String getLNameKW() {
		return _lName;
	}
	
	public String getDeptKW() {
		return _department;
	}
	
	public int getMinSalKW() {
		return _minSal;
	}
	
	public int getMaxSalKW() {
		return _maxSal;
	}
}

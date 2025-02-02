
import java.time.*;
import java.time.format.*;

public class TimeHandler {

	// method to obtain and set the time with the desired pattern
	public static String setTime() {
		return	LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
	}
	
	// method to obtain and set the date with the desired pattern
	public static String setDate() {
		return	LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	}
}

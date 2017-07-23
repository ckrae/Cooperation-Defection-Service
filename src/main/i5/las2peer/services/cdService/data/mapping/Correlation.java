package i5.las2peer.services.cdService.data.mapping;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.services.cdService.data.util.TableRow;

@Embeddable
public class Correlation {

		///////// Entity Fields ///////////

		@Basic
		private double pearson;

		@Basic
		private double spearman;		

		/////////// Constructor ///////////

		public Correlation() {

		}

		public Correlation(double[] val1, double[] val2) {
			
			this.pearson = calculatePearsons(val1, val2);
			this.spearman = calculateSpearmans(val1, val2);			
		}

		public Correlation(List<Double> valList1, List<Double> valList2) {
			
			int val1Size = valList1.size();
			int val2Size = valList2.size();
			double[] val1 = new double[val1Size];
			double[] val2 = new double[val2Size];
			
			for (int i = 0; i < val1Size; i++) {
				val1[i] = valList1.get(i);
				val2[i] = valList2.get(i);
			}

			this.pearson = calculatePearsons(val1, val2);
			this.spearman = calculateSpearmans(val1, val2);
		}

		/////////// Calculations ///////////

		private double calculatePearsons(double[] val1, double[] val2) {
			
			PearsonsCorrelation correlation = new PearsonsCorrelation();
			return correlation.correlation(val1, val2);
		}

		private double calculateSpearmans(double[] val1, double[] val2) {

			SpearmansCorrelation correlation = new SpearmansCorrelation();
			return correlation.correlation(val1, val2);
		}

		//////////// Getter /////////////

		@JsonProperty
		public double getPearsons() {
			return this.pearson;
		}

		@JsonProperty
		public double getSpearmans() {
			return this.spearman;
		}

		/////////// Print ///////////

		public TableRow toTableLine() {		

			TableRow line = new TableRow();		
			line.add(getPearsons()).add(getSpearmans());		
			return line;
		}

		public TableRow toHeadLine() {

			TableRow line = new TableRow();
			line.add("pearsons").add("spearmans");
			return line;

		}
	
}

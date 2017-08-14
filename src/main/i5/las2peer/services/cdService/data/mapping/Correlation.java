package i5.las2peer.services.cdService.data.mapping;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.apache.commons.math3.stat.inference.MannWhitneyUTest;
import org.apache.commons.math3.stat.inference.WilcoxonSignedRankTest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import i5.las2peer.services.cdService.data.util.table.TableRow;

@Embeddable
public class Correlation {

		///////// Entity Fields ///////////
		
		@Basic
		private double covariance;	
		
		@Basic
		private double pearson;

		@Basic
		private double spearman;
		
		@Basic
		private double kendall;
		
		@Basic
		private double wilcoxon;
		
		@Basic
		private double mannWhitney;
	

		/////////// Constructor ///////////

		public Correlation() {

		}

		public Correlation(double[] val1, double[] val2) {
			
			this.covariance = calculateCovariance(val1, val2);
			this.pearson = calculatePearsons(val1, val2);
			this.spearman = calculateSpearmans(val1, val2);	
			this.kendall = calculateKendalls(val1, val2);
			
			this.wilcoxon = calculateWilcoxons(val1, val2);
			this.mannWhitney = calculateMannWhitneyUs(val1, val2);
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
			this.wilcoxon = calculateWilcoxons(val1, val2);
		}

		/////////// Calculations ///////////
		
		protected double calculateCovariance(double[] val1, double[] val2) {
			
			Covariance correlation = new Covariance();
			return correlation.covariance(val1, val2);
		}
		
		protected double calculatePearsons(double[] val1, double[] val2) {
			
			PearsonsCorrelation correlation = new PearsonsCorrelation();
			return correlation.correlation(val1, val2);
		}

		protected double calculateSpearmans(double[] val1, double[] val2) {

			SpearmansCorrelation correlation = new SpearmansCorrelation();
			return correlation.correlation(val1, val2);
		}
		
		protected double calculateKendalls(double[] val1, double[] val2) {

			KendallsCorrelation correlation = new KendallsCorrelation();
			return correlation.correlation(val1, val2);
		}
		
		protected double calculateWilcoxons(double[] val1, double[] val2) {
			
			WilcoxonSignedRankTest correlation = new WilcoxonSignedRankTest();
			return correlation.wilcoxonSignedRank(val1, val2);
		}
		
		protected double calculateMannWhitneyUs(double[] val1, double[] val2) {
			
			MannWhitneyUTest correlation = new MannWhitneyUTest();
			return correlation.mannWhitneyU(val1, val2);
		}


		//////////// Getter /////////////
		
		@JsonProperty
		public double getCovariance() {
			return this.covariance;
		}
		
		@JsonProperty
		public double getPearson() {
			return this.pearson;
		}

		@JsonProperty
		public double getSpearman() {
			return this.spearman;
		}
		
		@JsonProperty
		public double getKendall() {
			return this.kendall;
		}
		
		@JsonProperty
		public double getWilcoxon() {
			return this.wilcoxon;
		}
		
		@JsonProperty
		public double getMannWhitneyU() {
			return this.mannWhitney;
		}
		
		///// Setter /////
		
		@JsonSetter
		public void setCovariance(double covariance) {
			this.covariance = covariance;
		}
		
		@JsonSetter
		public void setPearson(double pearson) {
			this.pearson = pearson;
		}
		
		@JsonSetter
		public void setSpearman(double spearman) {
			this.spearman = spearman;
		}
		
		@JsonSetter
		public void setKendall(double kendall) {
			this.kendall = kendall;
		}
		
		@JsonSetter
		public void setWilcoxon(double wilcoxon) {
			this.wilcoxon = wilcoxon;
		}
		
		@JsonSetter
		public void setMannWhitneyU(double whitney) {
			this.mannWhitney = whitney;
		}
		
		/////////// Print ///////////

		public TableRow toTableLine() {		

			TableRow line = new TableRow();		
			line.add(getCovariance()).add(getPearson()).add(getSpearman()).add(getKendall()).add(getWilcoxon()).add(getMannWhitneyU());		
			return line;
		}

		public TableRow toHeadLine() {

			TableRow line = new TableRow();
			line.add(covariance).add("pearsons").add("spearmans").add("kendall").add("wilcoxon").add("whitneyU");
			return line;

		}
	
}

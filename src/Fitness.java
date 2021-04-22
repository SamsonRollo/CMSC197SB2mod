import java.math.BigDecimal;
class Fitness{
	Fitness(){}
	protected BigDecimal calculateFitness(long penaltyValue){
		return (new BigDecimal(1.0)).divide((new BigDecimal(1.0)).add(new BigDecimal(penaltyValue)), new java.math.MathContext(500));
		}
	}
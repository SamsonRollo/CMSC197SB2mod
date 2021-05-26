class Fitness{
	Fitness(){}
	protected double calculateFitness(long penaltyValue){
		return 1/(1+Double.parseDouble(penaltyValue+""));
		}
	}
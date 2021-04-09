class GreedySelection{
	private int penaltyType = 0;
	GreedySelection(){}
	protected Bee greedySearch(Bee x, Bee v){
		double newFitness=new Fitness().calculateFitness(v.getPenaltyValue(penaltyType));
		if(x.getFitness()<=newFitness){
			x.copyProblem(v.getCopy());
			x.setFitness(newFitness);
			}
		return x;
		}
	protected void setPenaltyType(int penaltyType){
		this.penaltyType = penaltyType;
	}
	}
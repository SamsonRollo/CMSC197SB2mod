import java.math.BigDecimal;

class GreedySelection{
	private int penaltyType = 0;
	GreedySelection(){}
	protected Bee greedySearch(Bee x, Bee v){
		BigDecimal newFitness=new Fitness().calculateFitness(v.getPenaltyValue(penaltyType));
		//System.out.println(x.getFitness()+" <= "+newFitness+" : "+(x.getFitness().compareTo(newFitness)<=0));
		if(x.getFitness().compareTo(newFitness)<=0){
			x.copyProblem(v.getCopy());
			x.setFitness(newFitness);
			}
		return x;
		}
	protected void setPenaltyType(int penaltyType){
		this.penaltyType = penaltyType;
	}
	}
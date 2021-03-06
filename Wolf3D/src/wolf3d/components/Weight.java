package wolf3d.components;

import engine.components.Component;

/**
 * This class is responsible for giving an item a weight
 * @author Sameer Magan 300223776
 *
 */
public class Weight extends Component {
	private int weight = 10;	//default weight amount
	
	public Weight(int weight){
		this.weight = weight;
	}
	
	public Weight(){}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}

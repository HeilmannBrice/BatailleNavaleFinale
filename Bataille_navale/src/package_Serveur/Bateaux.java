package package_Serveur;



public class Bateaux {

	private int taille;
	private int life;
	
	
	public Bateaux(int taille, int life) {
		super();
		this.taille = taille;
		this.life = life;
	}

	//retourne la taille du bateau
	public int getTaille() {
		return taille;
	}

	//modifie la taille du bateau
	public void setTaille(int taille) {
		this.taille = taille;
	}

	//retourne la vie du bateau
	public int getLife() {
		return life;
	}

	//modifie la vie du bateau
	public void setLife(int life) {
		this.life = life;
	}
	//gère les dégats sur le bateau quand il est touché 
	public void impact() {
		setLife(this.life-1);
	}
	//retourne un booléen suivant si le bateau est coulé ou non
	public boolean est_coule() {
		if(this.life==0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	

	
	
	
	
}

package com.ica.ocs.Core;

public class TerrainEffect {
	private String description;
	private String density;
	private double attackerArmor;
	private double attackerMech;
	private double attackerOther;
	private double defenderArmor;
	private double defenderMech;
	private double defenderOther;
	
	public TerrainEffect(String description, String density, 
							double attackerarmor, double attackermech, double attackerother, 
							double defenderarmor, double defendermech, double defenderother) {
		this.description = description;
		this.density = density;
		this.attackerArmor = attackerarmor;
		this.attackerMech = attackermech;
		this.attackerOther = attackerother;
		this.defenderArmor = defenderarmor;
		this.defenderMech = defendermech;
		this.defenderOther = defenderother;
	}
	public TerrainEffect() {
		this.description = "Unknown";
		this.density = "Other";
		this.attackerArmor = 1;
		this.attackerMech = 1;
		this.attackerOther = 1;
		this.defenderArmor = 1;
		this.defenderMech = 1;
		this.defenderOther = 1;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public double getAttackerArmor() {
        return attackerArmor;
    }

    public void setAttackerArmor(double attackerArmor) {
        this.attackerArmor = attackerArmor;
    }

    public double getAttackerMech() {
        return attackerMech;
    }

    public void setAttackerMech(double attackerMech) {
        this.attackerMech = attackerMech;
    }

    public double getAttackerOther() {
        return attackerOther;
    }

    public void setAttackerOther(double attackerOther) {
        this.attackerOther = attackerOther;
    }

    public double getDefenderArmor() {
        return defenderArmor;
    }

    public void setDefenderArmor(double defenderArmor) {
        this.defenderArmor = defenderArmor;
    }

    public double getDefenderMech() {
        return defenderMech;
    }

    public void setDefenderMech(double defenderMech) {
        this.defenderMech = defenderMech;
    }

    public double getDefenderOther() {
        return defenderOther;
    }

    public void setDefenderOther(double defenderOther) {
        this.defenderOther = defenderOther;
    }

    public boolean getIsIntervening() {
        return this.density == "Other";
	}
}

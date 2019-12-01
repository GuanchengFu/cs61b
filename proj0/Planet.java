/** The class simulates a real Planet in the world.*/
public class Planet{

	public static final double G = 6.67e-11;

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xP, double yP, double xV,
              double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p){
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}

	/**Calculate the distance between this planet this planet p. */
	public double calcDistance(Planet p){
		double dx = this.xxPos - p.xxPos;
		double dy = this.yyPos - p.yyPos;
		return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}

	/**Calculate the Force from p on this planet. */
	public double calcForceExertedBy(Planet p){
		return (Planet.G*this.mass*p.mass)/Math.pow(calcDistance(p), 2);
	}

	/**Calculate the force on direct X exerted by the Planet p*/
	public double calcForceExertedByX(Planet p){
		double dx = p.xxPos - this.xxPos;
		//double dy = p.yyPos - this.yyPos;
		double force = this.calcForceExertedBy(p);
		return (force*dx)/calcDistance(p);
	}

	/**Calculate the force on direct y exerted by the Planet p*/
	public double calcForceExertedByY(Planet p){
		double dy = p.yyPos - this.yyPos;
		double force = this.calcForceExertedBy(p);
		return (force*dy)/calcDistance(p);
	}

	public double calcNetForceExertedByX(Planet[] planets){
		double netForceX = 0;
		for (int i = 0; i < planets.length; i ++){
			if (this.equals(planets[i])) {
				continue;
			}
			netForceX += this.calcForceExertedByX(planets[i]);
		}
		return netForceX;
	}

	public double calcNetForceExertedByY(Planet[] planets){
		double netForceY = 0;
		for (int i = 0; i < planets.length; i ++){
			if (this.equals(planets[i])) {
				continue;
			}
			netForceY += this.calcForceExertedByY(planets[i]);
		}
		return netForceY;
	}

	public void update(double dt, double fX, double fY){
		double aNetX = fX / this.mass;
		double aNetY = fY / this.mass;
		this.xxVel = this.xxVel + dt*aNetX;
		this.yyVel = this.yyVel + dt*aNetY;
		this.xxPos = this.xxPos + dt*this.xxVel;
		this.yyPos = this.yyPos + dt*this.yyVel;
	}

	public void draw(){
		String path = "images/" + this.imgFileName;
		StdDraw.picture(this.xxPos, this.yyPos, path);
	}
}
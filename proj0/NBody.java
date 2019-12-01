public class NBody{
	/**Read the radius of the universe from the file and return it.*/
	public static double readRadius(String path){
		In in = new In(path);
		in.readInt();
		return in.readDouble();
	}


	public static Planet[] readPlanets(String path){
		In in = new In(path);
		int numberOfPlanets = in.readInt();
		in.readDouble();
		Planet[] planets = new Planet[numberOfPlanets];
		for (int i = 0; i < numberOfPlanets; i++){
			double xcods = in.readDouble();
			double ycods = in.readDouble();
			double xvel = in.readDouble();
			double yvel = in.readDouble();
			double mass = in.readDouble();
			String tempPath = in.readString();
			planets[i] = new Planet(xcods, ycods, xvel, yvel, mass, tempPath);
		}
		return planets;
	}

	public static void main(String[] args) {
		if (args.length == 0){
		    System.out.println("Please supply a country as a command line argument.");
            System.out.println("First argument: T second argument: dt Third argument filename");
		}else{
			double T = Double.parseDouble(args[0]);
			double dt = Double.parseDouble(args[1]);
			String filename = args[2];

			Double radius = readRadius(filename);
			Planet[] planets = readPlanets(filename);

			StdDraw.setScale(-radius, radius);
			StdDraw.clear();

			StdDraw.picture(0, 0, "images/starfield.jpg");

			for (int i =0; i < planets.length; i ++){
				planets[i].draw();
			}

			StdDraw.enableDoubleBuffering();

			double currentTime = 0;
			while (currentTime < T){
				double[] xForces = new double[planets.length];
				double[] yForces = new double[planets.length];
				for (int i = 0; i<planets.length; i ++){
					xForces[i] = planets[i].calcNetForceExertedByX(planets);
					yForces[i] = planets[i].calcNetForceExertedByY(planets);
				}

				for (int i = 0; i<planets.length; i ++){
					planets[i].update(dt, xForces[i], yForces[i]);
				}
				StdDraw.clear();
				StdDraw.picture(0, 0, "images/starfield.jpg");

				for (int i =0; i < planets.length; i ++){
					planets[i].draw();
				}

				StdDraw.show();

				StdDraw.pause(10);
				currentTime += dt;
			}
			StdOut.printf("%d\n", planets.length);
			StdOut.printf("%.2e\n", radius);
			for (int i = 0; i < planets.length; i++) {
    			  StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
}
    }
}
}
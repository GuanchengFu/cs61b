public class Triangle{
	/**Draw a triangle like this :
*
**
***
****
*****                        */
	public static void main(String[] args) {
		int row = 1;
		int col = 0;
		while (row<=5){
			col = 0;
			while (col < row){
				System.out.print("*");
				col += 1;
			}
			System.out.println();
			row += 1;
		}
	}
}
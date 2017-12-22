package vue;
import com.pi4j.util.Console;

public class Position {

		public static final int POSITION_X_DEFAUT = 0;
		public static final int POSITION_Y_DEFAUT = 0;
		public static final int POSITION_Z_DEFAUT = 0;
		public static final int POSITION_U_DEFAUT = 0;
		public static final int POSITION_V_DEFAUT = 0;
		public static final int POSITION_W_DEFAUT = 0;

		private int x,y,z,u,v,w;
	
		public Position(int x, int y, int z, int u, int v, int w) {
			this.x=x;
			this.y=y;
			this.z=z;
			this.u=u;
			this.v=v;
			this.w=w;
		}
		
		public Position(int x, int y, int z, int u, int v) {
			this(x,y,z,u,v,POSITION_W_DEFAUT);
		}
		
		public Position(int x, int y, int z) {
			this(x,y,z,POSITION_U_DEFAUT,POSITION_V_DEFAUT,POSITION_W_DEFAUT);
		}
		
		public Position(int x, int y) {
			this(x,y,POSITION_Z_DEFAUT,POSITION_U_DEFAUT,POSITION_V_DEFAUT,POSITION_W_DEFAUT);
		}
		
		public Position() {
			this(POSITION_X_DEFAUT,
					POSITION_Y_DEFAUT,
					POSITION_Z_DEFAUT,
					POSITION_U_DEFAUT,
					POSITION_V_DEFAUT,
					POSITION_W_DEFAUT);
		}
		
		public int getX() {
			return this.x;
		}
		public int getY() {
			return this.y;
		}
		public int getZ() {
			return this.z;
		}
		public int getU() {
			return this.u;
		}
		public int getV() {
			return this.v;
		}
		public int getW() {
			return this.w;
		}
		
		public void setX(int X) {
			this.x=X;
		}
		public void setY(int Y) {
			this.y=Y;
		}
		public void setZ(int Z) {
			this.z=Z;
		}
		public void setU(int U) {
			this.u=U;
		}
		public void setV(int V) {
			this.v=V;
		}
		public void setW(int W) {
			this.w=W;
		}
		
		public String toString() {
			String info = "X=" + this.getX() + "  Y=" + this.getY() + "  Z=" + this.getZ() + "  U=" + this.getU() 
			+ "  V=" + this.getV() + "  W=" + this.getW() ;
			return info;
		}
		public void toPrompt() {
			final Console console = new Console();
			console.promptForExit();
			console.title("----- Position buse extrudeur -----");
			console.println("X : " + this.getX() + " Y : " + this.getY() + " Z : " + this.getZ());
			console.println("U : " + this.getU() + " V : " + this.getU() + " W : " + this.getW());		
		}
}

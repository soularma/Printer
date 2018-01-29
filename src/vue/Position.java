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
		private Position origin = new Position();
		private static boolean isMillimetre = true;
		
		//Les variables u,v et w ne peuvent être comprisent qu'entre -180 et 180. (Calculé en degré) 
		public Position(int x, int y, int z, int u, int v, int w) {
			this.x=x;
			this.y=y;
			this.z=z;
			
			this.u = orientationMax(u);
			this.v = orientationMax(v);
			this.w = orientationMax(w);
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
		
		public Position(Position position) {
			this.x = position.getX();
			this.y = position.getY();
			this.z = position.getZ();
			this.u = position.getU();
			this.v = position.getV();
			this.w = position.getW();
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
		public void setOrigine() {
			origin.setPosition(new Position());
		}
		public void setOrigine(int x, int y, int z, int u, int v, int w){
			this.origin.setX(x);
			this.origin.setY(y);
			this.origin.setZ(z);
			this.origin.setU(u);
			this.origin.setV(v);
			this.origin.setW(w);	
		}
		public void setOrigineX(int x ){
			this.origin.setX(x);
		}
		public void setOrigineY(int y ){
			this.origin.setY(y);
		}
		public void setOrigineZ(int z ){
			this.origin.setZ(z);
		}
		public void setOrigineU(int u ){
			this.origin.setU(u);
		}
		public void setOrigineV(int v ){
			this.origin.setV(v);
		}
		public void setOrigineW(int w ){
			this.origin.setW(w);
		}
		public int getOriginX() {
			return origin.getX();
		}
		public int getOriginY() {
			return origin.getY();
		}
		public int getOriginZ() {
			return origin.getZ();
		}
		public int getOriginU() {
			return origin.getU();
		}
		public int getOriginV() {
			return origin.getV();
		}
		public int getOriginW() {
			return origin.getW();
		}
		
		public void setPosition(Position position) {
			this.setX(position.getX());
			this.setY(position.getY());
			this.setZ(position.getZ());
			this.setU(position.getU());
			this.setV(position.getV());
			this.setW(position.getW());
		}
		
		public String affichePos() {
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
		
		public int orientationMax(int axe) {
			if (axe > 180 || axe < -180) {
				if(axe > 180) 
				 axe = 180;
				else
				 axe = -180;
			return axe;
			}else 
				return axe;
		}
		/* _______________________
		 *| Unite  		| Boolean
		 * -----------------------
		 *| Millimetre	| True
		 * -----------------------
		 *| Pouce		| False
		 * -----------------------
		 */
		public static void setUnity(boolean a){
			isMillimetre = a;
		}
		
		
}

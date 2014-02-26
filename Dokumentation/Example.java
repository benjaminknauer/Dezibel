/**
 * ...
 * @author Richard
 * @inv
 */
public class Example {
	public static final int psf1 = 0;
	public static int ps1 = 0;
	public int p1 = 0;
	
	private static final int psf2 = 0;
	private static int ps2 = 0;
	private int p2 = 0;
	
	/**
	 * ...
	 */
	public Example() {
		
	}
	
	/**
	 * ...
	 * @return
	 * @pre
	 * @post
	 */
	public int method1() {
		return 0;
	}
	
	/**
	 * ...
	 * @return
	 * @pre
	 * @post
	 */
	private int method2() {
		return 0;
	}
	
	public static int getPs1() {
		return ps1;
	}

	public static void setPs1(int ps1) {
		Example.ps1 = ps1;
	}

	public int getP1() {
		return p1;
	}

	public void setP1(int p1) {
		this.p1 = p1;
	}

	public static int getPs2() {
		return ps2;
	}

	public static void setPs2(int ps2) {
		Example.ps2 = ps2;
	}

	public int getP2() {
		return p2;
	}

	public void setP2(int p2) {
		this.p2 = p2;
	}

	public static int getPsf1() {
		return psf1;
	}

	public static int getPsf2() {
		return psf2;
	}

}

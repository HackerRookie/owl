/**
 * 
 */
package org.owl.common;

import java.io.Serializable;

/**
 * @author cy
 * Coords store x and y values, since these are hexes
 * 
 *      -y
 *       0
 *      _____
 *  5 /     \ 1
  -x /       \ +x 
 *   \       / 
 *  4 \_____/ 2 
 *       3
 *      +y
 */
public class Coords implements Serializable {

	private static final long serialVersionUID = 2468619395328742269L;
	
	public static final double HEXSIDE = Math.PI / 3.0;
	
	/*Allow at most 30 boards (510 hex)  in the y directory */
	private static final int SHIFT = 9;
	private static final int MASK = ( 1 << SHIFT) - 1; 
	
	public static final int MAX_BOARD_HEIGHT = Integer.MAX_VALUE & MASK; //511
	public static final int MAX_BOARD_WIDTH = (Integer.MAX_VALUE - Coords.MAX_BOARD_HEIGHT) >> (Coords.SHIFT + 2); //1048575
	
	
	private final int x;
	private final int y;
	
	public Coords(final int x,final int y){
		this.x = x;
		this.y = y;
	}
	
	public Coords(){
		this(0,0);
	}
	
	public Coords(Coords coords){
		this(coords.getX(),coords.getY());
	}
	
	
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
		System.out.println(Coords.HEXSIDE);
		
		System.out.println(Coords.MAX_BOARD_HEIGHT);
		
		System.out.println(Coords.MAX_BOARD_WIDTH);
	}
	
}
